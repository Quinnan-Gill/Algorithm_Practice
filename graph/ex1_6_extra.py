# Complete the countMatches function below.
def countMatches(grid1, grid2):

    mut_grid_1 = []
    mut_grid_2 = []

    # for a, b in zip(grid1, grid2):
    #     row_grid_1 = []
    #     row_grid_2 = []
    #     for c, d in zip(a, b):
    #         row_grid_1.append(int(c))
    #         row_grid_2.append(int(d))
    #     mut_grid_1.append(row_grid_1)
    #     mut_grid_2.append(row_grid_2)
    for row in grid1:
        row_grid_1 = []
        for val in row:
            row_grid_1.append(int(val))
        mut_grid_1.append(row_grid_1)
    for row in grid2:
        row_grid_2 = []
        for val in row:
            row_grid_2.append(int(val))
        mut_grid_2.append(row_grid_2)

    label1 = connect_comp_label(mut_grid_1, len(mut_grid_1))
    label2 = connect_comp_label(mut_grid_2, len(mut_grid_2))

    return find_diff(label1,label2)


def and_vals(a, b):
    if a == '1' and b == '1':
        return 1
    else:
        return 0

def find_diff(label1, label2):
    ll = LinkedLabels()

    for x_1, y_1 in label1.keys():
        ll.add1((x_1, y_1), label1[(x_1, y_1)])
    for x_2, y_2 in label2.keys():
        ll.add2((x_2, y_2), label2[(x_2, y_2)])

    return ll.union_prune()

def connect_comp_label(mut_grid, size):
    # Use connected component labels algorithm
    labels = {}
    min_label = 0

    uf = UnionFindArray()
    # First pass to find all connected components
    for row in range(0, size):
        for col in range(0, size):
            if mut_grid[row][col] == 1:
                # want northwest, north, northeast, and west boxes

                # north
                if row > 0 and mut_grid[row-1][col] == 1:
                    n = labels[(row-1, col)]
                    labels[(row, col)] = n

                    # check west
                    if col > 0 and mut_grid[row][col-1] == 1:
                        w = labels[(row, col-1)]
                        uf.union(n , w)
                # west
                elif col > 0 and mut_grid[row][col-1] == 1:
                    labels[(row, col)] = labels[(row, col-1)]
                # no new neighbors
                else:
                    labels[(row, col)] = uf.add(min_label)
                    min_label += 1

    # Second pass to make all connected components the
    # res_grid = [ ['a' for i in range(size)] for j in range(size)]
    for row, col in labels.keys():
        comps = uf.find(labels[(row, col)])
        labels[(row, col)] = comps
        # res_grid[row][col] = comps

    return labels

class UnionFindArray:
    def __init__(self):
        self.map = {}
        self.parent = []
        self.index = 0
        self.size = 0
    def add(self, x):
        self.map[x] = self.index
        temp = self.index
        self.parent.append(self.index)
        self.index += 1
        return temp
    def find(self, val):
        try:
            i = self.map[val]
            while self.parent[i] < i:
                temp = self.parent[i]
                self.parent[i] = self.parent[temp]
                i = temp
            return i
        except:
            return -1
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
    def union_help(self, i, root):
        try:
            val = self.map[i]
            while self.parent[val] < val:
                temp = self.parent[val]
                self.parent[val] = root
                val = temp
            self.parent[val] = root
        except:
            pass
    def get_parents(self):
        return self.parent

class LinkedLabels:
    def __init__(self):
        self.lst = {}
        self.uf = UnionFindArray()
    def add1(self, coor,val1):
        if coor not in self.lst:
            self.lst[coor] = [val1, None]
        else:
            self.lst[coor][0] = val1

    def add2(self, coor, val2):
        try:
            self.lst[coor][1] = val2
        except:
            self.lst[coor] = [None, val2]

    def union_prune(self):
        added1 = []
        added2 = []
        for value in self.lst.values():
            try:
                found_1 = added1.index(value[0])
            except:
                found_1 = None
            try:
                found_2 = added2.index(value[1])
            except:
                found_2 = None
            value = tuple(value)
            if found_1 == None and found_2 == None:
                self.uf.add(value)
            elif found_1 == None:
                self.uf.add(value)
                parent = (added1[found_2], added2[found_2])
                self.uf.union(parent, value)
            elif found_2 == None:
                self.uf.add(value)
                parent = (added1[found_1], added2[found_1])
                self.uf.union(parent, value)

            added1.append(value[0])
            added2.append(value[1])

        parent =  self.uf.get_parents()
        for a, b in zip(added1, added2):
            if a == None or b == None:
                removal = self.uf.find((a,b))
                parent = list(filter(lambda x: x != removal, parent))
        return len(set(parent))


if __name__ == '__main__':
    # data = [["001","111","101"],["001","100","101"]]
    # data = [["0100","1001","0011","0011"],["0101","1001","0011","0011"]]
    # data = [["0010","0111","0100","1111"],["0010","0111","0110","1111"]]

    # data = [["111","101","100"],["111","100","101"]]
    # data = [["0100","1011","0011","0011"],["010","101","000"]]
    # data = [["11111","10001","10101","10001","11111"],["11111","10001","10001","10001","11111"]]

    # data = [["10001","01010","00100","01010","10001"],["10101","01010","10101","01010","10101"]]

    data = [["00000","00000","00000","00000","00000"],["00000","00000","00000","00000","00000"]]

    data = [["10101","01010","10101","01010","10101"],["01010","10101","01010","10101","01010"]]

    data = [["11111","11111","11111","11111","10111"],["11111","11111","11111","11111","11111"]]
    # data = [["0000000000"]]
5
10000
10000
10000
10000
10001
3
100
100
100
    for row in data[0]:
        print row
    print ""

    for row in data[1]:
        print row
    print ""
    print countMatches(data[0],data[1])
