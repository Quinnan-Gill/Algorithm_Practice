def roadsAndLibraries(n, c_lib, c_road, cities):
    if c_lib < c_road or cities == [[]]:
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

if __name__ == "__main__":

    cities = [[8, 2],
              [2, 9]]

    print(roadsAndLibraries(9, 91, 84, cities))

    cities = [[2, 1],
              [5, 3],
              [5, 1],
              [3, 4],
              [3, 1],
              [5, 4],
              [4, 1],
              [5, 2],
              [4, 2]]

    print(roadsAndLibraries(5, 92, 23, cities))

    cities = [[6, 4],
              [3, 2],
              [7, 1]]

    print(roadsAndLibraries(8, 10, 55, cities))

    print(roadsAndLibraries(1, 5, 3, [[]]))

    print(roadsAndLibraries(2, 102, 1, [[]]))
