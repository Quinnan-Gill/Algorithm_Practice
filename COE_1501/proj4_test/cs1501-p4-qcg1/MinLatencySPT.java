import java.util.Queue;
import java.util.LinkedList;

public class MinLatencySPT {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private boolean[] visited;
    private IndexMinPQ<Double> pq;
    private int V;

    public MinLatencySPT(NetworkGraph G) {
        V = G.V();
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    private void prim(NetworkGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    private void scan(NetworkGraph G, int v) {
        marked[v] = true;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new LinkedList<Edge>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null) {
                mst.add(e);
            }
        }
        return mst;
    }

    public void printTree() {
        NetworkGraph mst = new NetworkGraph(V);
        for (Edge e: edges()) {
            mst.addEdge(e);
        }

        visited = new boolean[V];
        dfsTraversal(mst, 0, "");
    }

    private void dfsTraversal(NetworkGraph G, int v, String str) {
        visited[v] = true;
        for (Edge e: G.adj(v)) {
            int w = e.other(v);
            if (!visited[w]) {
                System.out.println(str + v + "--" + e.weight() + "-->"+ w);
                str += "  ";
                dfsTraversal(G, w, str);
            }
        }
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e: edges())
            weight += e.weight();
        return weight;
    }
}
