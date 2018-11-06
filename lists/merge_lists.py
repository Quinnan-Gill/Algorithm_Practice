import os
import sys

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

def mergeLists(head1, head2):
    if head1 == None and head2 == None:
        return None
    if head1 != None and head2 == None:
        return head1
    if head2 != None and head1 == None:
        return head2
    if head1.data <= head2.data:
        head1.next = mergeLists(head1.next, head2)
    elif head2.data < head1.data:
        t = head2
        head2 = head1
        head1 = t
        head1.next = mergeLists(head1.next, head2)
    return head1
'''
if((headA==NULL)&&(headB==NULL)
    return NULL;
if((headA!=NULL)&&(headB==NULL))
    return headA;
if((headA == NULL)&&(headB!=NULL))
    return headB;
if(headA->data < headB->data)
    headA->next = MergeLists(headA->next, headB);
else if(headA->data > headB->data)
{
    Node* temp = headB;
    headB = headB->next;
    temp->next = headA;
    headA = temp;
    headA->next = MergeLists(headA->next, headB);
}
return headA;
'''
