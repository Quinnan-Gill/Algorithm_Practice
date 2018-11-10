class UnionFindArray(object):
    def __init__(self, n):
        self.parent = list(range(0,n))

    def setRoot(self, val, root):
        while self.parent[val] < val:
            temp = self.parent[val]
            self.parent[val] = root
            val = temp
        self.parent[val] = root

    def findRoot(self, val):
        while self.parent[val] < val:
            val = self.parent[val]
        return val

    def find(self, val):
        root = self.findRoot(val)
        self.setRoot(val, root)
        return root

    def union(self, x, y):
        print("Union:", x, "and", y)
        if x != y:
            root = self.findRoot(x)
            print("\troot_x:", root)
            root_j = self.findRoot(y)
            print("\troot_y:", root_j)
            if root > root_j:
                root = root_j
            self.setRoot(x, root)
            self.setRoot(y, root)

    def union_help(self, val, root):
        while self.parent[val] < val:
            temp = self.parent[val]
            self.parent[val] = root
            val = temp
        self.parent[val] = root
    def run_through(self):
        for i, p in enumerate(self.parent):
            self.find(i)

# Complete the journeyToMoon function below.
def journeyToMoon(n, astronaut):
    uf = UnionFindArray(n)

    for cosmo in astronaut:
        uf.union(cosmo[0], cosmo[1])
    uf.run_through()

    arr = uf.parent

    print("arr:", arr)

    count_map = {}
    for a in arr:
        try:
            count_map[a] += 1
        except:
            count_map[a] = 1

    set_sizes = list(count_map.values())

    print(set_sizes)

    result = 0
    summa = set_sizes.pop()

    while set_sizes:
        new_val = set_sizes.pop()
        result += (summa * new_val)
        summa += new_val
    return result

if __name__ == "__main__":
    cosmos = [[0, 2],
              [1, 8],
              [1, 4],
              [2, 8],
              [2, 6],
              [3, 5],
              [6, 9]]

    print(journeyToMoon(10, cosmos))
