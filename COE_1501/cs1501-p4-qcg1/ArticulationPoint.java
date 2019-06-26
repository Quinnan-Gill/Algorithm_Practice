public class ArticulationPoint {
    private Node[] marked;
    private int count;
    private Node head;
    private int[] parents;
    private int artPoints;

    public ArticulationPoint(NetworkGraph G) {
        int s = 0;
        marked = new Node[G.V()];
        parents = new int[G.V()];
        for(int i=0; i < G.V(); i++)
            parents[i] = -1;
        count = 0;
        artPoints = 0;
        head = new Node(s);
        getArticulationPoint(G, head);
        checkArt(head);
    }

    private void getArticulationPoint(NetworkGraph G, Node parent) {
        int v = parent.val;
        parent.num = count++;
        parent.low = parent.num;
        marked[v] = parent;
        int dfsChild = 0;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            Node ni = new Node(w);
            ni.num = count;
            ni.low = count;
            if (marked[w] == null) {
                parents[w] = v;
                parent.child.add(ni);
                getArticulationPoint(G, ni);
                ++dfsChild;
                parent.low = Math.min(parent.low, ni.low);
            } else if (w != parents[v]) {
                ni = marked[w];
                // System.out.println("parent.low1: " + parent.low);
                parent.low = Math.min(parent.low, ni.num);
                // System.out.println("parent.low2: " + parent.low);
            }
        }
    }

    private void checkArt(Node curr) {
        for(Node ni: curr.child) {
            if (curr.num < ni.low) {
                System.out.println("Not Biconnected");
                artPoints++;
            }
            checkArt(ni);
        }
    }

    public int numOfArticulationPoints() {
        return artPoints;
    }
}
