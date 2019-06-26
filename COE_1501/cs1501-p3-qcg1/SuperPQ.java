import java.util.Iterator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class SuperPQ<Val extends Comparable<Val>>{
    private IndirectMinPQ<Val>[] pq;
    private int n;
    private ExtendedComparator<Car, Val> comparator;
    private HashMap<String, Integer> model_map;
    private HashMap<String, String> vin_map;

    public SuperPQ(int initCapacity) {
        pq = new IndirectMinPQ[initCapacity];
        n = 0;
        model_map = new HashMap<String, Integer>();
        vin_map = new HashMap<String, String>();
    }

    public SuperPQ() {
        this(2);
    }

    public SuperPQ(int initCapacity, ExtendedComparator<Car, Val> comparator) {
        this.comparator = comparator;
        pq = new IndirectMinPQ[initCapacity];
        n = 0;
    }

    public SuperPQ(ExtendedComparator<Car, Val> comparator) {
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

        return pq[0].min();
    }

    public Car min(String make, String model) {
        if (isEmpty()) throw new NoSuchElementException();
        if (!model_map.containsKey(make + model)) return null;
        int index = model_map.get(make + model);
        return pq[index].min();
    }

    public Car getCar(String vin) {
        if (!vin_map.containsKey(vin)) return null;
        int index = model_map.get(vin_map.get(vin));
        return pq[index].getCar(vin);
    }

    public void insert(Car x) {
        String id = x.make + x.model;
        vin_map.put(x.vin, id);
        if (!model_map.containsKey(id)) {
            regular_insert(x, id);
        } else {
            insert_update(x, id);
        }
    }

    private void regular_insert(Car x, String id) {
        if (n == pq.length) resize(2 * pq.length);

        pq[n] = new IndirectMinPQ<Val>(comparator);
        pq[n].insert(x);
        n++;
        model_map.put(id, n-1);
        swim(n-1);
        assert isMinHeap();
    }

    private void insert_update(Car x, String id) {
        int index = model_map.get(id);
        Val old_value = comparator.getPrimaryValue(pq[index].min());

        pq[index].insert(x);

        Val new_value = comparator.getPrimaryValue(pq[index].min());

        if (old_value.compareTo(new_value) < 0) {
            sink(index);
        } else if (old_value.compareTo(new_value) == 0) {
            // do nothing
        } else {
            swim(index);
        }
    }

    public void update(String vin, Val b) {
        if (!vin_map.containsKey(vin)) return;
        int index = model_map.get(vin_map.get(vin));

        Val old_min = comparator.getPrimaryValue(pq[index].min());

        pq[index].update(vin, b);

        Val new_min = comparator.getPrimaryValue(pq[index].min());

        // System.out.println("old_min: " + old_min + " new_min: " + new_min);

        if (old_min.compareTo(new_min) < 0) {
            sink(index);
        } else if (old_min.compareTo(new_min) == 0) {
            // do nothing
        } else {
            swim(index);
        }
    }

    public void delMin() {
        throw new UnsupportedOperationException("unable to delete min");
    }

    public Car remove(String vin) {
        if (!vin_map.containsKey(vin)) throw new NoSuchElementException();
        if (isEmpty()) return null;
        int index = model_map.get(vin_map.get(vin));

        Val old_min = comparator.getPrimaryValue(pq[index].min());

        if (pq[index].size() == 1) {
            return regular_remove(vin, index);
        }

        Car decom = pq[index].remove(vin);

        Val new_min = comparator.getPrimaryValue(pq[index].min());

        if (old_min.compareTo(new_min) < 0) {
            sink(index);
        } else {
            swim(index);
        }
        return decom;
    }

    private Car regular_remove(String vin, int index) {
        Car decom = pq[index].min();
        exch(index, n-1);
        n--;
        pq[n] = null;
        model_map.remove(vin_map.get(vin));
        vin_map.remove(vin);
        sink(index);
        if (( n > 0) && (n == (pq.length) / 4)) resize(pq.length / 2);
        assert isMinHeap();
        return decom;
    }

    private void resize(int capacity) {
        assert capacity > n;
        IndirectMinPQ<Val>[] temp = new IndirectMinPQ[capacity];
        for (int i=0; i < n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    private void swim(int k) {
        while (k > 0 && greater(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
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

    private void exch(int i, int j) {
        Car node_i = pq[i].min();
        Car node_j = pq[j].min();
        model_map.put(node_i.make + "" + node_i.model, j);
        model_map.put(node_j.make + "" + node_j.model, i);

        IndirectMinPQ<Val> swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private boolean greater(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Car>) pq[i].min()).compareTo(pq[j].min()) > 0;
        }
        else {
            return comparator.compare(pq[i].min(), pq[j].min()) > 0;
        }
    }

    private boolean isMinHeap() {
        return isMinHeap(0);
    }

    private boolean isMinHeap(int k) {
        if (k >= n) return true;
        int left = 2*k+1;
        int right = 2*k+2;
        if (left  < n && greater(k, left)) return false;
        if (right < n && greater(k, right)) return false;
        return isMinHeap(left) && isMinHeap(right);
    }

    public void printPQ() {
        for(int i=0; i < n; i++) {
            Car p = pq[i].min();
            System.out.println("\n" + i + ": " + p.make + " " + p.model);
            pq[i].printPQ();
        }
    }
}
