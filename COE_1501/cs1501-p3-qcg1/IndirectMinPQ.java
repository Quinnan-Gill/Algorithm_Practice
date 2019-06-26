import java.util.Iterator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class IndirectMinPQ<Val extends Comparable<Val>> implements Iterable {
    private Car[] pq;
    private int n;
    private ExtendedComparator<Car, Val> comparator;
    private HashMap<String, Integer> map;

    public IndirectMinPQ(int initCapacity) {
        pq = new Car[initCapacity];
        n = 0;
        map = new HashMap<String, Integer>();
    }

    public IndirectMinPQ() {
        this(2);
    }

    public IndirectMinPQ(int initCapacity, ExtendedComparator<Car, Val> comparator) {
        this.comparator = comparator;
        pq = new Car[initCapacity];
        n = 0;
    }

    public IndirectMinPQ(ExtendedComparator<Car, Val> comparator) {
        this(2);
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Car min() {
        if (isEmpty()) return null;
        return pq[0];
    }

    public Car getCar(String vin) {
        if (!map.containsKey(vin)) throw new NoSuchElementException();
        int index = map.get(vin);
        return pq[index];
    }

    private void resize(int capacity) {
        assert capacity > n;
        Car[] temp = new Car[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the key to add to this priority queue
     */
    public void insert(Car x) {
        // double size of array if necessary
        if (n == pq.length) resize(2 * pq.length);

        // add x, and percolate it up to maintain heap invariant
        pq[n] = x;
        n++;
        map.put(x.vin, n-1);
        swim(n-1);
        assert isMinHeap();
    }

    private void swim(int k) {
        while (k > 0 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    public Car delMin() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Car min = pq[0];
        exch(0, n-1);
        n--;
        pq[n] = null;     // to avoid loiterig and help with garbage collection
        map.remove(min.vin);
        sink(0);
        if ((n > 0) && (n == (pq.length) / 4)) resize(pq.length / 2);
        assert isMinHeap();
        return min;
    }


    private void sink(int k) {
        while (2*k+1 < n) {
            int j = 2*k+1;
            if (j+1 < n && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Car>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            Car a = pq[i];
            Car b = pq[j];
            return comparator.compare(a, b) > 0;
        }
    }

    private void exch(int i, int j) {
        map.put(pq[i].vin, j);
        map.put(pq[j].vin, i);

        Car swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..N] a min heap?
    private boolean isMinHeap() {
        return isMinHeap(0);
    }

    // is subtree of pq[1..n] rooted at k a min heap?
    private boolean isMinHeap(int k) {
        if (k >= n) return true;
        int left = 2*k+1;
        int right = 2*k + 2;
        if (left  < n && greater(k, left))  return false;
        if (right < n && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }

    public Car remove(String id) {
        if (!map.containsKey(id)) throw new NoSuchElementException();
        if (isEmpty()) return null;
        int index = map.get(id);
        Car decom = pq[index];
        exch(index, n-1);
        n--;
        pq[n] = null;
        map.remove(id);
        sink(index);
        if (( n > 0) && (n == (pq.length) / 4)) resize(pq.length / 2);
        assert isMinHeap();
        return decom;
    }

    public void update(String id, Val b) {
        if (!map.containsKey(id)) throw new NoSuchElementException();
        int index = map.get(id);

        Val old_val = comparator.updateVal(pq[index], b);

        if (old_val.compareTo(b) < 0) {
            sink(index);
        } else if (old_val.compareTo(b) == 0) {
            // do nothing
        } else {
            swim(index);
        }
    }

    public void printPQ() {
        for(int i=0; i < n; i++) {
            System.out.println(i + ": "+ comparator.getId(pq[i]) + " " + comparator.getPrimaryValue(pq[i]));
        }
    }

    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in ascending order.
     * <p>
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator<Car> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Car> {
        // create a new pq
        private IndirectMinPQ<Val> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new IndirectMinPQ<Val>(size());
            else                    copy = new IndirectMinPQ<Val>(size(), comparator);
            for (int i = 0; i < n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Car next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}
