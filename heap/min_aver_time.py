'''
[[0, 3], [1, 9], [2, 5], [3, 6], [4, 7]]
'''
from heapq import *
def min_aver(tasks):
    tasks.sort(reverse=True)

    pq = []
    time_waiting = 0
    current_time = 0

    print tasks
    print pq

    while tasks or pq:
        while tasks and tasks[-1][0] <= current_time:
            heappush(pq, tasks.pop()[::-1])
        print tasks
        print pq
        if pq:
            current_task = heappop(pq)
            current_time += current_task[0]
            time_waiting += current_time - current_task[1]
        else:
            heappush(pq, tasks.pop()[::-1])
            current_time = pq[0][1]

min_aver([[0, 3], [1, 9], [2, 5], [3, 6], [4, 7]])
