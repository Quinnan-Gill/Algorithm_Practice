package graph;

import java.util.*;

public class Dijkstra {
    static int[] shortestReach(int n, int[][] edges, int s) {
        HashMap<Integer, int[]> graph = new HashMap<>();

        for(int i=0; i < edges.length; i++) {
            graph.put(edges[i][0], {edges[i][2], edges[i][1]});
            graph.put(edges[i][1], {edges[i][2], edges[i][1]});
        }

        int[] result = new int[n];
        for(int i=1; i <= n; i++) {
            if (i != s) {
                result[i-1] = dijkstra(graph, s, end);
            }
        }
    }

    static int dijkstra(HashMap<Integer, int[]> graph, int start, int end) {
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>();
        HashMap<String, Boolean> visited = new HashMap<>();
        HashMap<Integer, Integer> mins = new HashMap<>();

        pq.add()
        while (!pq.isEmpty()) {
            int[] = pq.poll()
        }
    }
}
