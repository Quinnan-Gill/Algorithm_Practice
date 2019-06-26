class Node(object):
    def __init__(self, key, val):
        self.key = key
        self.val = val
    def __str__(self):
        return str(self.key)
    def compare(self, other):
        return self.val > other.val
class DeltaMin(object):
    def __init__(self):
        self.pq = []
        self.map = dict()
        self.n = 0

    def size(self):
        return self.n

    def isEmpty(self):
        return self.n == 0

    def insert(self, x):
        self.pq.append(None)
        self.pq[self.n] = x
        self.n += 1
        self.map[x.key] = self.n-1
        self.swim(self.n-1)

    def swim(self, k):
        while k > 0 and self.greater(k/2, k):
            self.exch(k, k/2)
            k = k/2

    def delMin(self):
        if (self.isEmpty()):
            return
        min = self.pq[0]
        self.exch(0, self.n-1)
        self.pq.pop()
        self.map.pop(min, None)
        self.n -= 1
        self.sink(0)

        return min

    def sink(self, k):
        while 2*k+1 < self.n:
            j = 2*k+1
            if j+1 < self.n and self.greater(j, j+1):
                j += 1
            if not self.greater(k, j):
                break
            self.exch(k, j)
            k = j
        return k

    def update(self, search, val):
        if not search in self.map:
            return

        pos = self.map[search]
        old_val = self.pq[pos].val
        self.pq[pos].val = val

        if val < old_val:
            self.swim(pos)
        else:
            self.sink(pos)


    def exch(self, a, b):
        a = int(a)
        b = int(b)

        self.map[self.pq[b].key] = a
        self.map[self.pq[a].key] = b

        t = self.pq[a]
        self.pq[a] = self.pq[b]
        self.pq[b] = t

    def greater(self, a, b):
        return self.pq[int(a)].compare(self.pq[int(b)])
        # return self.pq[int(a)] > self.pq[int(b)]
    def printPQ(self):
        # print([p.key for p in self.pq])
        print({p.key: p.val for p in self.pq})

d_pq = DeltaMin()
d_pq.insert(Node("a", 4))
d_pq.insert(Node("b", 7))
d_pq.insert(Node("c", 2))
d_pq.insert(Node("d", 16))
d_pq.insert(Node("e", 17))
d_pq.insert(Node("f", 18))
d_pq.insert(Node("g", 19))
d_pq.insert(Node("h", 1))


d_pq.printPQ()

d_pq.update("h", 20)
d_pq.printPQ()
d_pq.update("g", 1)
d_pq.printPQ()
# print(d_pq.delMin())
# print(d_pq.delMin())
# print(d_pq.delMin())
