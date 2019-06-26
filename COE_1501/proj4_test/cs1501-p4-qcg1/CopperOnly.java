public class CopperOnly {
    private boolean[] marked;
    private int count;
    private int V;

    public CopperOnly(NetworkGraph G) {
        marked = new boolean[G.V()];
        count = 0;
        V = G.V();
        dfs(G, 0);
    }

    private void dfs(NetworkGraph G, int v) {
        count++;
        marked[v] = true;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (!marked[w] && e.copper) {
                dfs(G, w);
            }
        }
    }

    public int count() {
        return count;
    }

    public boolean copperConnected() {
        return count == V;
    }
}
