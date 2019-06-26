class SinglyLinkedListNode:
    def __init__(self, node_data):
        self.data = node_data
        self.next = None

class SinglyLinkedList:
    def __init__(self):
        self.head = None

def print_singly_linked_list(node):
    while node:
        print(node.data, )

        node = node.next

def insertNodeAtTail(head, data):
    if head == None:
        head = SinglyLinkedListNode(data)
    elif head.next == None:
        head.next = SinglyLinkedListNode(data)
    else:
        insertNodeAtTail(head.next, data)
    return head

if __name__ == '__main__':
    ll = [141, 302, 164, 530, 474]

    llist = SinglyLinkedList()

    for i in ll:
        llist_head = insertNodeAtTail(llist.head, i)
        llist.head = llist_head

    print_singly_linked_list(llist.head)
