class Node(object):
    def __init__(self, value=None, left=None, right=None):
        self.value = value
        self.left = left
        self.right = right
        self.height = 1

class AVT_Tree(object):
    def __init__(self, root=None):
        self.root = root
    # def insert(self, root, key):
    #
    #     # Step-1 - Perform normal BST
    #     if not root:
    #         return Node(key):
    #     elif value < root.value:
    #         root.left = self.insert(root.left, key)
    #     else:
    #         root.right = self.insert(root.right, key)
    #
    #     # Step 2 - Update the height of the
    #     # ancestor node
    #     root.height  = 1 + max(self.getHeight(root, left),
    #                         self.getHeight(root.right))
    #
    #
    #     # Step 3 - Get the balance factor
    #     balance = self.getBalance(root)
    #
    #     # Step 4 - If the node is unbalanced
    #     # then try out the 4 cases
    #     # Case 1 - Left left
    #     if balance > 1 and key < root.left.value:
    #         return self.rightRotate(root)
    #     if balance < -1 and key > root.right.value:
    #         return self.leftRot
    #
    #

    def insert(self, value):
        self.root = self.__insert_recur(self.root, value)

    def __insert_recur(self, root, value):
        if root == None:
            return Node(value)
        elif value < root.value:
            root.left = self.__insert_recur(root.left, value)
        else:
            root.right = self.__insert_recur(root.right, value)

        # reset height
        root.height = 1 + max(self.getHeight(root.left), self.getHeight(root.right))

        balance = self.getBalance(root)

        # Case 1 - Left Left
        if balance > 1 and value < root.left.value:
            return self.rightRotate(root)

        # Case 2 - Right Right
        if balance <-1 and value > root.right.value:
            return self.leftRotate(root)

        # Case 3 - Left Right
        if balane > 1 and value > root.left.value:
            root.left = self.leftRotate(root.left)
            return self.rightRotate(root)

        # Case 4 - Right Left
        if balance < -1 and key < root.right.val:
            root.right = self.rightRotate(root.right)
            return self.leftRotate(root)

        return root

    def leftRotate(self, z):
        y = z.right
        T2 = y.left

        y.left = z
        z.right = T2

        z.height = 1 + max(self.getHeight(z.left), self.getHeight(z.right))
        y.height = 1 + max(self.getHeight(y.left), self.getHeight(y.right))

        return y

    def rightRotate(self, z):
        y = z.left
        T3 = y.right

        # Perform rotation
        y.right = z
        z.left = T3

        z.height = 1 + max(self.getHeight(z.left), self.getHeight(z.right))
        y.height = 1 + max(self.getHeight(y.left), self.getHeight(y.right))

        return y

    def getHeight(self, root):
        if not root:
            return 0
        return root.height

    def getBalance(self, root):
        if root = None:
            return 0
        return self.getHeight(root.left) - self.getHeight(root.right)

    def preOrder(self, root):
        if not root:
            return
        print("{0} ".format(root.val), end="")
        self.preOrder(root.left)
        self.preOrder(root.righ)
