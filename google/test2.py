from heapq import *

def solution(S, E):
    # write your code in Python 3.6

    pq = []

    count = 0

    guests = sorted(list(zip(S, E)))

    for guest in guests:
        if pq and guest[0] >= pq[0]:
            heappop(pq)
        heappush(pq, guest[1])

        if len(pq) > count:
            count = len(pq)
    return count
print(solution([1, 2, 6, 5, 3], [5, 5, 7, 6, 8]))
