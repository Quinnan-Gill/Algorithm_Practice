class UnionFindArray(object):
    def __init__(self, n):
        self.parent = list(range(0,n))

    def find(self, val):
        while self.parent[val] < val:
            temp = self.parent[val]
            self.parent[val] = self.parent[temp]
            val = temp
        return val

    def union(self, x, y):
        if x != y:
            root_x = self.find(x)
            root_y = self.find(y)
            if root_x > root_y:
                self.union_help(x, root_y)
                self.union_help(y, root_y)
            else:
                self.union_help(x, root_x)
                self.union_help(y, root_x)

    def union_help(self, val, root):
        while self.parent[val] < val:
            temp = self.parent[val]
            self.parent[val] = root
            val = temp
        self.parent[val] = root

uf = UnionFindArray(5)
print(uf.parent)

uf.union(0, 1)
print(uf.parent)

uf.union(2, 3)
print(uf.parent)

uf.union(0, 4)
print(uf.parent)
