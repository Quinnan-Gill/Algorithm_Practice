class IndexMinPQ(object):
    def __init__(self):
        self.n = 0
        self.pq = []

    def exch(self, a, b):
        pq[a], pq[b] = pq[b], pq[a]

    def swim(self, k):
        while k > 1 and (pq[k//2]) > pq[k]:
            exch(k, k//2)
            k = k//2

    def sink(self, k):
        while 2*k <= self.n:
            j = 2*k
