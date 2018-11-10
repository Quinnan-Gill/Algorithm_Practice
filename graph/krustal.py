class Graph:
    def __init__(self, vertices):
        self.V = vertices
        self.graph = []

    def addEdge(self, u, v, w):
        self.graph.append([u,v,w])

    def find(self, parent, i):
        # print(i)
        if parent[i] == i:
            return i
        return self.find(parent, parent[i])

    def union(self, parent, rank, x, y):
        xroot = self.find(parent, x)
        yroot = self.find(parent, y)

        if rank[xroot] < rank[yroot]:
            parent[xroot] = yroot
        elif rank[xroot] > rank[yroot]:
            parent[yroot] = xroot
        else:
            parent[yroot] = xroot
            rank[xroot] += 1

        print(parent)

    def KrustalsMST(self):
        result = 0

        i = 0
        e = 0

        self.graph = sorted(self.graph, key=lambda item: item[2])

        parent = []
        rank = []

        for node in range(self.V):
            parent.append(node)
            rank.append(0)
        print(self.graph)
        print(parent)

        while e < self.V - 2:
            u, v, w = self.graph[i]
            i += 1
            x = self.find(parent, u)
            y = self.find(parent, v)

            if x != y:
                e += 1
                result += w
                self.union(parent, rank, x, y)

        return result

def kruskals(g_nodes, g_from, g_to, g_weight):

    graph = Graph(g_nodes+1)
    for u, v, w in zip(g_from, g_to, g_weight):
        graph.addEdge(u, v, w)

    return graph.KrustalsMST()


if __name__ == "__main__":

    nodes = [[1, 2, 5],
             [1, 3, 3],
             [4, 1, 6],
             [2, 4, 7],
             [3, 2, 4],
             [3, 4, 5]]

    g_from = []
    g_to = []
    g_weight = []

    for a, b, c in nodes:
        g_from.append(a)
        g_to.append(b)
        g_weight.append(c)

    print(kruskals(4, g_from, g_to, g_weight))
