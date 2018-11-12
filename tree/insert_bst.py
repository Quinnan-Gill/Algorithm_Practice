class Node:
    def __init__(self, info):
        self.info = info
        self.left = None
        self.right = None
        self.level = None

    def __str__(self):
        return str(self.info)

def preOrder(root):
    if root == None:
        return
    print (root.info, end=" ")
    preOrder(root.left)
    preOrder(root.right)

class BinarySearchTree:
    def __init__(self):
        self.root = None

    def insert(self, val):
        #Enter you code here.
        if self.root == None:
            self.root = Node(val)
            return

        root = self.root

        while root:
            if root.info > val:
                if root.left:
                    root = root.left
                else:
                    root.left = Node(val)
                    return
            else:
                if root.right:
                    root = root.right
                else:
                    root.right = Node(val)
                    return
if __name__ == "__main__":
    nodes = [4, 2, 3, 1, 7, 6]

    tree = BinarySearchTree()

    for node in nodes:
        tree.insert(node)
        print(tree.root)

    preOrder(tree.root)
