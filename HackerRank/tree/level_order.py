from collections import deque

class Node:
    def __init__(self, info):
        self.info = info
        self.left = None
        self.right = None
        self.level = None

    def __str__(self):
        return str(self.info)

class BinarySearchTree:
    def __init__(self):
        self.root = None

    def create(self, val):
        if self.root == None:
            self.root = Node(val)
        else:
            current = self.root

            while True:
                if val < current.info:
                    if current.left:
                        current = current.left
                    else:
                        current.left = Node(val)
                        break
                elif val > current.info:
                    if current.right:
                        current = current.right
                    else:
                        current.right = Node(val)
                        break
                else:
                    break

class QueueNode:
    def __init__(self, val):
        self.val = val
        self.next = None

class Queue:
    def __init__(self):
        self.head = None
        self.tail = None

    def put(self, node):
        if self.head == None:
            self.head = QueueNode(node)
        if self.tail == None:
            self.tail = self.head
        else:
            self.tail.next = QueueNode(node)
            self.tail = self.tail.next
    def get(self):
        val = self.head
        self.head = self.head.next
        return val.val

    def print_queue(self):
        print("fsdafs")

    def isEmpty(self):
        return self.head == None



# def levelOrder(root):
#     queue = Queue()
#     visited = set()
#
#     queue.put(root)
#
#     while not queue.isEmpty():
#         node = queue.get()
#
#         print(node.info, end=" ")
#
#         if node.left:
#             queue.put(node.left)
#         if node.right:
#             queue.put(node.right)

def levelOrder(root):
    queue = deque()
    visited = set()

    queue.append(root)

    while queue:
        node = queue.popleft()

        print(node.info, end=" ")

        if node.left:
            queue.append(node.left)
        if node.right:
            queue.append(node.right)


if __name__ == "__main__":
    tree = BinarySearchTree()

    nodes = [1, 2, 5, 3, 6, 4]

    for node in nodes:
        tree.create(node)

    levelOrder(tree.root)
