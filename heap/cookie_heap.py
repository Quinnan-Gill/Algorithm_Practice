import os
import sys

class MinHeap(object):
    def __init__(self):
        self.heap = []

    def size(self):
        return len(self.heap)

    def isEmpty(self):
        return len(self.heap) == 0

    def parent(self, index):
        return (index - 1) / 2

    def left(self, index):
        return (index * 2) + 1

    def right(self, index):
        return (index * 2) + 2

    def swap(self, a, b):
        self.heap[a], self.heap[b] = self.heap[b], self.heap[a]

    def heapify_down(self, index):
        left = self.left(index)
        right = self.right(index)

        largest = index

        if left < self.size() and self.heap[left] < self.heap[index]:
            largest = left

        if right < self.size() and self.heap[right] < self.heap[largest]:
            largest = right

        if largest != index:
            self.swap(index, largest)
            self.heapify_down(largest)

    def heapify_up(self, index):
        parent = self.parent(index)

        if index > 0 and self.heap[parent] > self.heap[index]:
            self.swap(parent, index)

            self.heapify_up(parent)

    def build_heap(self, arr):
        self.heap = arr
        start = self.size() / 2

        while start >= 0:
            self.heapify_down(start)

            start -= 1

    def push(self, value):
        self.heap.append(value)

        index = self.size() - 1

        self.heapify_up(index)

    def pop(self):
        if self.isEmpty():
            return None

        value = self.heap[0]

        self.heap[0] = self.heap[self.size()-1]
        del self.heap[self.size()-1]

        self.heapify_down(0)

        return value

    def peek(self):
        return self.heap[0]


#
# Complete the cookies function below.
#
def cookies(k, A):
    #
    # Write your code here.
    #

#     min_heap = MinHeap()

#     min_heap.build_heap(A) # build the heap

#     steps = 0

#     while min_heap.size() >= 2:

#         val1 = min_heap.pop()
#         val2 = min_heap.pop()

#         if val1 == None or val2 == None:
#             return -1

#         if val1 >= k:
#             return steps
#         else:
#             steps += 1

#             result = val1 + 2*val2

#             min_heap.push(result)

#     if min_heap.peek() >= k:
#         return steps
#     else:
#         return -1

    heapify(A)

    steps = 0

    while len(A) >= 2:
        val1 = heappop(A)

        if val1 >= k:
            return steps
        else:
            val2 = heappop(A)

            steps += 1

            result = val1 + 2*val2

            heappush(A, result)

    if A[0] >= k:
        return steps

    else:
        return -1
