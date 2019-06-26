/*
 * The Node used to act as a intermediate between the DLB and the MaxPQ
 * It has a String dbl_key to pull from the DLB and a comparable value T that
 * is used to interact with the MaxPQ
 */
public class ValueNode<T extends Comparable<T>> implements Comparable<ValueNode<T>>{
    public String dbl_key;
    public T pq_key;

    public ValueNode(String prefix, T val) {
        dbl_key = prefix;
        pq_key = val;
    }

    @Override
    public int compareTo(ValueNode<T> other) {
        return pq_key.compareTo(other.pq_key);
    }
}
