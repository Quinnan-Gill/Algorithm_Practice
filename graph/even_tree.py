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

# Complete the evenForest function below.
def evenForest(t_nodes, t_edges, t_from, t_to):
    tree_map = {}
    for edge in range(t_edges-1, -1, -1):
        if t_from[edge] in tree_map:
            tree_map[t_from[edge]] += 1
        else:
            tree_map[t_from[edge]] = 1
        if t_to[edge] in tree_map:
            tree_map[t_to[edge]] += tree_map[t_from[edge]]
        else:
            tree_map[t_to[edge]] = tree_map[t_from[edge]]
    return len([1 for key in tree_map.keys() if tree_map[key]%2 == 0])
