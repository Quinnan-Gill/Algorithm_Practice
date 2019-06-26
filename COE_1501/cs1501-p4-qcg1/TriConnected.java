public class TriConnected {
    private Node[] marked;
    private int count;
    private int toxic;
    private Node head;
    private int[] parents;
    int artPoints;

    public TriConnected(NetworkGraph G) {
        artPoints = 0;

        int s=1;
        toxic=0;
        marked = new Node[G.V()];
        parents = new int[G.V()];
        for(int i=0; i < G.V(); i++)
            parents[i] = -1;
        count = 0;

        head = new Node(s);
        getArticulationPoint(G, head);
        checkArt(head);
        if (artPoints > 0) return;

        s = 0;
        for(toxic=1; toxic < G.V(); toxic++) {
            marked = new Node[G.V()];
            parents = new int[G.V()];
            for(int i=0; i < G.V(); i++)
                parents[i] = -1;
            count = 0;

            head = new Node(s);
            getArticulationPoint(G, head);
            checkArt(head);
            if (artPoints > 0) return;
        }
    }

    private void getArticulationPoint(NetworkGraph G, Node parent) {
        int v = parent.val;
        parent.num = count++;
        parent.low = parent.num;
        marked[v] = parent;
        int dfsChild = 0;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (w == toxic) {
                continue;
            }
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
                artPoints++;
            }

            checkArt(ni);
        }
    }

    public boolean hasArticulationPoints() {
        return artPoints > 0;
    }
}
