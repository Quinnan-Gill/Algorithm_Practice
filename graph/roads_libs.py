#!/bin/python3

import math
import os
import random
import re
import sys

def roadsAndLibraries(n, c_lib, c_road, cities):
    print(cities)
    if c_lib < c_road or cities == []:
        return n * c_lib

    # generate graph
    graph_map = {}
    for city in cities:
        try:
            graph_map[city[0]].update([city[1]])
        except:
            graph_map[city[0]] = set([city[1]])
        try:
            graph_map[city[1]].update([city[0]])
        except:
            graph_map[city[1]] = set([city[0]])

    start = cities[0][0]

    visited = {}

    components = []

    for node in graph_map.keys():
        if node not in visited.keys():
            visited[node] = True
            components.append(dfs(graph_map, node, visited, 1))

    result = 0
    for comp in components:
        result += (comp-1) * c_road + c_lib

    result += (n - len(visited)) * c_lib

    return result


def dfs(graph, node, visited, result):
    for adj in graph[node]:
        if not(adj in visited.keys()):
            visited[adj] = True
            result = dfs(graph, adj, visited, result+1)
    return result

if __name__ == '__main__':

    q = int(input())

    for q_itr in range(q):
        nmC_libC_road = input().split()

        n = int(nmC_libC_road[0])

        m = int(nmC_libC_road[1])

        c_lib = int(nmC_libC_road[2])

        c_road = int(nmC_libC_road[3])

        cities = []

        for _ in range(m):
            cities.append(list(map(int, input().rstrip().split())))

        result = roadsAndLibraries(n, c_lib, c_road, cities)

        print(str(result) + '\n')
