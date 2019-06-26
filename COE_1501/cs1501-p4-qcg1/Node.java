import java.util.LinkedList;

public class Node {
    public int num;
    public int low;
    public int val;
    public LinkedList<Node> child;

    public Node() {
        num = -1;
        low = -1;
        val = -1;
        child = new LinkedList<Node>();
    }

    public Node(int num, int val)  {
        this();
        this.num = num;
        this.val = val;
    }

    public Node(int val) {
        this();
        this.val = val;
    }

    public String toString() {
        return "num: " + num + "\nlow: " + low + "\nval: " + val;
    }
}
