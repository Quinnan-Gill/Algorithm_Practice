import java.util.LinkedList;
// import java.util.Iterable;

public class NetworkGraph {
    private final int V;
    private int E;
    private LinkedList<Edge>[] adj;
    private int[] indegree;

    public NetworkGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be positive");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = (LinkedList<Edge>[]) new LinkedList[V];
        for (int v=0; v < V; v++) {
            adj[v] = new LinkedList<Edge>();
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + V);
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public void addEdge(String unparsed) {
        Edge e = new Edge(unparsed);
        addEdge(e);
    }

    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public Iterable<Edge> edges() {
        LinkedList<Edge> list = new LinkedList<Edge>();
        for (int v=0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e: adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }

                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    public void printGraph() {
        for(int v = 0; v < V; v++) {
            for (Edge e: adj(v)) {
                e.printEdge();
            }
        }
    }

    public int V(){
        return V;
    }

    public boolean invalidVertex(int v) {
        return v < 0 || v >= V;
    }
}
