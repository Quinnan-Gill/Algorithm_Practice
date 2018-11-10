#!/bin/python3

import os
import sys

'''
1. Determine the bottom nodes of the tree (with no children)
2. Assign "weight" 1 to each of the bottom nodes
3. Build your way up the tree calculating the "weight" of each node (for example
    a node with 2 children has "weight" 3)
4. Count the number of nodes with even "weight" excluding the root.
    That's your answer...
'''

if __name__ == '__main__':
    tree_nodes, tree_edges = map(int, input().split())

    tree_from = [0] * tree_edges
    tree_to = [0] * tree_edges

    for tree_itr in range(tree_edges):
        tree_from[tree_itr], tree_to[tree_itr] = map(int, input().split())

    tree_weight = {}
    for tree_itr in range(tree_edges-1,-1,-1):
        if tree_from[tree_itr] in tree_weight:
            tree_weight[tree_from[tree_itr]] += 1
        else:
            tree_weight[tree_from[tree_itr]] = 1
        if tree_to[tree_itr] in tree_weight:
            tree_weight[tree_to[tree_itr]] += tree_weight[tree_from[tree_itr]]
        else:
            tree_weight[tree_to[tree_itr]] = tree_weight[tree_from[tree_itr]]
    # print(tree_weight)
    print(len([1 for key in tree_weight if tree_weight[key] % 2 == 0]))
