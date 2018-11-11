from collections import defaultdict
from heapq import *

# Complete the shortestReach function below.
def shortestReach(n, edges, s):
    graph = defaultdict(list)

    for f, t, w in edges:
        graph[f].append([w, t])
        graph[t].append([w, f])

    result = []
    for i in range(1, n+1):
        if i != s:
            result.append(djis(graph, s, i))
    return result


def dijkstra(graph, start, end):
    pq = [(0, start,[])]
    seen = set()
    mins = {start: 0}

    while pq:
        print(pq)
        print(mins)
        (cost, v1, path) = heappop(pq)
        if v1 not in seen:
            seen.add(v1)
            path = (v1, path)
            if v1 == end:
                return cost

            for c, v2 in graph.get(v1, []):
                if v2 in seen: continue
                prev = mins.get(v2, None)
                next = cost + c
                if prev is None or next < prev:
                    mins[v2] = next
                    heappush(pq, (next, v2, path))
    return -1

if __name__ == "__main__":

    edges = [[1, 2, 24],
             [1, 4, 20],
             [3, 1, 3],
             [4, 3, 12]]

    print(shortestReach(4, edges, 1))
