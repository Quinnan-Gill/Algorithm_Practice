class SinglyLinkedListNode:
    def __init__(self, node_data):
        self.data = node_data
        self.next = None

class SinglyLinkedList:
    def __init__(self):
        self.head = None
        self.tail = None

    def insert_node(self, node_data):
        node = SinglyLinkedListNode(node_data)

        if not self.head:
            self.head = node
        else:
            self.tail.next = node


        self.tail = node

def print_singly_linked_list(node, sep, fptr):
    while node:
        fptr.write(str(node.data))

        node = node.next

        if node:
            fptr.write(sep)

def reverse(head):
    sllist = SinglyLinkedList()
    reverse_recur(head, sllist)

    return sllist.head

def reverse_recur(head, sllist):
    if head == None or head.next == None:
        return head

    remaining = reverse_recur(head.next, sllist)

    sllist.insert_node(remaining.data)

    return head

def create_and_rev(lst):
    sllist = SinglyLinkedList()
    for l in lst:
        sllist.insert_node(l)
