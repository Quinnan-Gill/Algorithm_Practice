import java.util.LinkedList;
import java.util.Queue;

public class Djikstra {
    private double[] distTo;
    private Edge[] edgeTo;
    private LinkedList<Integer>[] pathTo;
    private IndexMinPQ<Double> pq;

    public Djikstra(NetworkGraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];

        validateVertex(s);

        for (int v=0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from a
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e: G.adj(v))
                relax(e, v);
        }
        // System.out.println("Done");
    }

    private void relax(Edge e, int v) {
        int w = e.other(v);

        // System.out.println("In relax v: " + v);
        // System.out.println("In relax w: " + w);

        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else {
                pq.insert(w, distTo[w]);
            }
        }
    }

    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        MyStack<Edge> path = new MyStack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[v]) {
            path.push(e);
            v = e.other(v);
        }
        return path;
    }

    public void printPath(int v) {
        int min_band = Integer.MAX_VALUE;
        String str = "";
        validateVertex(v);
        for(Edge e: pathTo(v)) {
            int to = e.to;
            int from = e.from;
            System.out.println(str + from + "--" + e.weight() + "-->"+ to);
            if (min_band > e.bandwidth) {
                min_band = e.bandwidth;
            }
            str += "  ";
        }

        System.out.println("bandwidth: " + min_band);
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
