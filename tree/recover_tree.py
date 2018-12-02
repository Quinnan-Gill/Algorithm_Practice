class TreeNode(object):
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution(object):
    def recurInorder(self, root, inorder):
        if root.left:
            self.recurInorder(root.left, inorder)
        inorder.append(root.val)
        if root.right:
            self.recurInorder(root.right, inorder)

    def inorderTraversal(self, root):
        inorder = []
        if root:
            self.recurInorder(root, inorder)
        return inorder
    def recoverTree(self, root):
        inorder = self.inorderTraversal(root)
        wrong = []
        inorder_sort = sorted(inorder)
        for i in range(len(inorder)):
            if inorder[i] != inorder_sort[i]:
                wrong.append(inorder[i])
        result = []
        stack = [(root, False)]

        while stack:
            cur, visited = stack.pop()
            if cur:
                if visited:
                    if cur.val == wrong[0]:
                        cur.val = wrong[1]
                    elif cur.val == wrong[1]:
                        cur.val = wrong[0]
                else:
                    stack.append((cur.right, False))
                    stack.append((cur, True))
                    stack.append((cur.left, False))
