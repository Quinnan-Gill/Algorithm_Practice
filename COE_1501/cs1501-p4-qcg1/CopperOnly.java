/**
 *  The {@code DepthFirstSearch} class represents a data type for
 *  determining the vertices connected to a given source vertex <em>s</em>
 *  in an undirected graph. For versions that find the paths, see
 *  {@link DepthFirstPaths} and {@link BreadthFirstPaths}.
 *  <p>
 *  This implementation uses depth-first search.
 *  See {@link NonrecursiveDFS} for a non-recursive version.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the graph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Inspired by:
 *  Robert Sedgewick
 *  Kevin Wayne
 */
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

    // Depth first search
    private void dfs(NetworkGraph G, int v) {
        count++;
        marked[v] = true;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            // forget all copper
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
