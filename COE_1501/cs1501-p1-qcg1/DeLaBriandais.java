import java.util.*;

public class DeLaBriandais<E extends Comparable<E>> implements TrieSTInterface<E> {
    private static final char terminator = '^'; // The terminator for the key in the DLB

    private Node root; // the starting node
    private int size; // Number of keys in the DLB

    /*
     * Internal Node for the DLB
     */
    private static class Node {
        private Object value; // The value in the ST
        private char key; // The key or being entered into the DLB
        private Node lateral; // The keys of different possible keys with same prefix
        private Node depth; // The extends the prefix of a key

        private Node(char key) {
            this.key = key;
            value = null;
            lateral = null;
            depth = null;
        }
    }

    public DeLaBriandais() {
        root = null;
        size = 0;
    }

    /*
     * Checks if key exists in the DLB. True if does exist and false if not
     * @param String key The key to search if in DLB
     * return boolean if the was found
     */
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /*
     * Gets the string from DBL and return the value of the key or return null
     * if key does not exist.
     * @param String key the string to be searched for
     * return E the value of the key
     */
    public E get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        Node x = get(root, key, 0); // get the level the possible could be on
        // traverse through the nodes
        while (x != null && x.key != terminator){
            if (x == null) {
                return null;
            } else {
                x = x.lateral;
            }
        }
        // check if not null
        if (x == null) {
            return null;
        }
        // return value of the node
        return (E) x.value;
    }

    /*
     * A private method to get the level of the possible get key
     * Performs recursively the search
     * @param Node x. The node currently being examined
     * @param String key. The string to be searched
     * @param int d the character in the key being compared
     * return Node the current Node being examined
     */
    private Node get(Node x, String key, int d) {
        if (x == null) return null; // If it is null return null b/c not DLB
        // If at max length return the current node
        if (d == key.length()) {
            return x;
        } else if (x.key == key.charAt(d)) {
            // depth traversal of the node
            return get(x.depth, key, d+1);
        } else {
            // make lateral movement to exam next node for possible character
            return get(x.lateral, key, d);
        }
    }

    /*
     * Takes a key and value and puts it into the DLB
     * @param String key the value entering into the DLB
     * @param E val the value for the terminator node
     */
    public void put(String key, E val) {
        if (key == null) {
            throw new IllegalArgumentException("argument to put() is null");
        }

        root = put(root, key + terminator, val, 0);
    }

    /*
     * The private method to put the key and value into the node
     * @pram Node x: The node being examined
     * @param String key: Key to enter into DLB
     * @param E: value to be paired with the key
     * @param d: the current character being examined
     */
    private Node put(Node x, String key, E val, int d) {
        // Creates new node to be placed into DBL if does not exist
        if (x == null) x = new Node(key.charAt(d));
        // End of the string add the value terminator node
        if (d == key.length()-1) {
            // If the key is not a terminator and it is at the end add
            // a new lateral node
            if (x.key != terminator){
                x.lateral = put(x.lateral, key, val, d);
            }else {
                // Terminator have it increment size and update the value
                if (x.value == null) size++;
                x.value = val;
                return x;
            }
        }
        if (key.charAt(d) == x.key) {
            // Set the depth node as the next character
            x.depth = put(x.depth, key, val, d+1);
        } else {
            // Need to move to next possibel node
            x.lateral = put(x.lateral, key, val, d);
        }
        // returns the updated node
        return x;
    }

    /*
     * return the size as int
     */
    public int size() {
        return size;
    }

    /*
     * return isEmpty as boolean
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /*
     * returns all the possibel keys in the DLB
     * return Iterable<ValueNode<E>> all keys in the DLB
     */
    public Iterable<ValueNode<E>> keys() {
        return keysWithPrefix("");
    }

    /*
     * The return all the keys starting with the same string prefix
     * @param prefix The starting string
     * return Iterable<ValueNode<E>> a queue to return all the keys found
     */
    public Iterable<ValueNode<E>> keysWithPrefix(String prefix) {
        MaxPQ<ValueNode<E>> results = new MaxPQ<ValueNode<E>>();
        // Start at root and get the starting node at end of prefix
        Node x = get(root, prefix, 0);
        collect(x, prefix, results);
        return results;
    }

    /*
     * Load theh results Iterable MaxPQ with the proper strings
     * @param Node x The start node for the traversal
     * @param String prefix to be added to traversed nodes
     * @param The MaxPQ to be loaded. The MaxPQ implements Interable
     */
    private void collect(Node x, String prefix, MaxPQ<ValueNode<E>> results) {
        if (x == null) return;
        if (x.key == terminator) {
            // If terminator is encounterd take the key and get its value and
            // insert into the MaxPQ
            results.insert(new ValueNode<E>(prefix, (E) x.value));
        }
        // Lateral traversal
        collect(x.lateral, prefix, results);
        // Depth traversal
        collect(x.depth, prefix + "" + x.key, results);
    }

    // Everything below here is not implemented
    public Iterable<String> keysThatMatch(String pattern) {
        throw new UnsupportedOperationException("keysThatMatch() is not supported");
    }

    public String longestPrefixOf(String query) {
        throw new UnsupportedOperationException("longestPrefixOf() is not supported");
    }

    public void delete(String key) {
        throw new UnsupportedOperationException("delete() is not supported");
    }
}
