#!/bin/python3

import math
import os
import random
import re
import sys
from queue import Queue

# Complete the bfs function below.
def bfs(n, m, edges, s):
    graph = {}
    visited = {}
    for node in range(1, n+1):
        graph[node] = set()
        visited[node] = -1
    for edge in edges:
        graph[edge[0]].update([edge[1]])
        graph[edge[1]].update([edge[0]])

    print(graph)

    queue = Queue()
    queue.put((s, 0))

    depth = 6


    while not queue.empty():
        val = queue.get()

        depth = val[1] + 6

        for adj in graph[val[0]]:
            if visited[adj] == -1:
                queue.put((adj, depth))
                visited[adj] = depth


    result = []

    print(list(visited.keys()))

    for visit in list(visited.keys()):
        if visit != s:
            result.append(visited[visit])

    return result


if __name__ == "__main__":
    edges = [[1, 2],
             [1, 3],
             [3, 4]]

    print(bfs(5, 3, edges, 1))
