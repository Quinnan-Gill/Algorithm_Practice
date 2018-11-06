class TreeNode(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

#
# Complete the swapNodes function below.
#
def swapNodes(indexes, queries):
    #
    # Write your code here.
    #
    tree_map = {}
    height = 1

    root = TreeNode(1)
    tree_map[height] = [root]

    createTree(root, 0, indexes, tree_map, height)

    inorder_display = []
    inorder(root, inorder_display)

    print(inorder_display)
    print(" ")

    for t in tree_map.keys():
        print(t, ":",end=" ")
        for j in tree_map[t]:
            print(j.value,end=" ")
        print()
    result = []
    for k in queries:
        count = k
        while count <= len(tree_map):
            for branch in tree_map[count]:
                temp = branch.right
                branch.right = branch.left
                branch.left = temp
            count += k
        inorder_display = []
        inorder(root, inorder_display)

        print(inorder_display)


def createTree(root, pos, indexes, tree_map, height):
    if pos < len(indexes):

        children = indexes[pos]
        print(children, end=" ")
        print("pos:", pos, "len(indexes):", len(indexes))
        root.left = TreeNode(children[0])
        root.right = TreeNode(children[1])

        try:
            tree_map[height+1].append(root.left)
            tree_map[height+1].append(root.right)
        except:
            tree_map[height+1] = [root.left]
            tree_map[height+1].append(root.right)

        createTree(root.left, (pos*2)+1, indexes, tree_map, height+1)
        createTree(root.right, (pos*2)+2, indexes, tree_map, height+1)

def inorder(root, display):
    if root == None or root.value == -1:
        return
    inorder(root.left, display)
    display.append(root.value)
    inorder(root.right, display)

# print(swapNodes([[2, 3],[-1, -1],[-1, -1]], [1, 1]))


# order = swapNodes([[2 ,3], [4 ,5], [6 ,-1], [-1, 7], [8 ,9], [10, 11], [12, 13], [-1, 14], [-1, -1], [15, -1], [16, 17], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1]], [2,2])
order = swapNodes([[2 ,3],[4 ,-1],[5 ,-1],[6 ,-1],[7 ,8],[-1, 9],[-1, -1],[10, 11],[-1, -1],[-1, -1],[-1, -1]], [2,2])
order = swapNodes([[2 ,3], [4 ,-1], [5 ,-1], [6 ,-1], [7 ,8], [-1, 9], [-1, -1], [10, 11], [-1, -1], [-1, -1], [-1, -1]], [2,2])
