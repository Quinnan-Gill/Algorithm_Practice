import math

class MaxHeap:
    def __init__(self):
        self.heap = []

    def __str__(self):
        return ' '.join([str(i) for i in self.heap])

    def  parent(self, index):
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

        print "Index:", self.heap[index]
        print "Left:", self.heap[left]
        print "Right:", self.heap[right]

        largest = index

        if left < len(self.heap) and self.heap[left] > self.heap[index]:
            largest = index

        if right < len(self.heap) and self.heap[right] > self.heap[largest]:
            largest = right

        if (largest != index):
            self.swap(index, largest)
            print self.heap
            self.heapify_down(largest)

    def heapify_up(self, index):
        print self.heap
        if index > 0 and self.heap[self.parent(index)] < self.heap[index]:
            self.swap(index, self.parent(index))

            print "Heapify:", self.heap

            self.heapify_up(self.parent(index))

    def size(self):
        return len(self.heap)

    def isEmpty(self):
        return len(self.heap) == 0

    def push(self, value):
        self.heap.append(value)


        index = self.size() - 1
        self.heapify_up(index)

    def pop(self):
        try:
            if len(self.heap) == 0:
                raise IndexError()

            value = self.heap[0]

            self.heap[0] = self.heap[self.size() - 1]
            del self.heap[-1]

            self.heapify_down(0)
        except:
            print("Index Error: Popping an empty string loser")

arr = [6, 7, 12, 10, 15, 17, 5]
max_heap = MaxHeap()

for a in arr:
    max_heap.push(a)


print max_heap.heap

max_heap.pop()
print max_heap.heap


# for i in range(0, 3):
#     max_heap.pop()
#     print max_heap.heap
