public interface TrieSTInterface<E extends Comparable<E>> {
    public boolean contains(String key);

    public E get(String key);

    public void put(String key, E val);

    public int size();

    public boolean isEmpty();

    public Iterable<ValueNode<E>> keys();

    public Iterable<ValueNode<E>> keysWithPrefix(String prefix);

    public Iterable<String> keysThatMatch(String pattern);

    public String longestPrefixOf(String query);

    public void delete(String key);
}
