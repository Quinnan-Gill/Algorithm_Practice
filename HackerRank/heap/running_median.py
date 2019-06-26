from heapq import *

#
# Complete the runningMedian function below.
#
def runningMedian(a):
    #
    # Write your code here.
    #
    min_heap = []
    max_heap = []

    result = []

    median = 0.0

    for i, elem in enumerate(a):
        if elem <= median:
            heappush_max(max_heap, elem)
        else:
            heappush(min_heap, elem)

        # print "min heap 1:", min_heap
        # print "max_heap 1:", -1 * max_heap

        if len(min_heap) > len(max_heap) + 1:
            heappush_max(max_heap, heappop(min_heap))
        elif len(max_heap) > len(min_heap) + 1:
            heappush(min_heap, heappop_max(max_heap))

        if len(min_heap) == len(max_heap) + 1:
            median = float(min_heap[0])
        elif len(min_heap) == len(max_heap):
            median = ((min_heap[0] + (-1 * max_heap[0])) / 2.0)
        else:
            median = float(-1 *  max_heap[0])

        result.append(median)

    return result


def heappush_max(max_heap, value):
    heappush(max_heap, -1*value)

def heappop_max(min_heap):
    return -1 * heappop(min_heap)

a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

print runningMedian(a)
