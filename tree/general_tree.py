class Node:
    def __init__(self, value):
        self.value = value
        self.parent = None
        self.right = None
        self.left = None

class Tree:
    def __init__(self):
        self.root = None

    def set_root(self, value):
        self.root = Node(value)
        return self.root

    def set_right(self, node, value):
        node.right = Node(value)
        node.right.parent = node
        return node.right

    def set_left(self, node, value):
        node.left = Node(value)
        node.left.parent = node
        return node.left

dic = {}
def col_order(root, horzdist, mydic):
    if not root:
        return None
    if horzdist in mydic:
        mydic[horzdist].append(root.val)
    else:
        col_order(root.left, horzdist-1, mydiv)
        col_order(root.right, horzdist+1, mydiv)
def 
