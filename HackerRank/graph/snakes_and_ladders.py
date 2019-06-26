import sys
from queue import Queue

# Complete the quickestWayUp function below.
def quickestWayUp(ladders, snakes):
    board = list(range(0, 101))

    for edge in (ladders + snakes):
        board[edge[0]] = edge[1]

    visited = {}
    queue = Queue()

    queue.put((1, 0))
    visited[1] = []

    while queue:
        val, roll = queue.get()

        print(str(val)+": "+str([board[i] for i in range(val+7, val, -1) if i <= 100]))
        for adj in range(val+6, val, -1):
            if adj > 100:
                continue
            if board[adj] == 100:
                return roll+1
            elif board[adj] not in visited:
                queue.put((board[adj], roll+1))
                visited[adj] = True

if __name__ == "__main__":
    # ladders = [[32, 62],
    #            [42, 68],
    #            [12, 98]]
    #
    # snakes = [[95, 13],
    #           [97, 25],
    #           [93, 37],
    #           [79, 27],
    #           [75, 19],
    #           [49, 47],
    #           [67, 17]]
    #
    # print(quickestWayUp(ladders, snakes))

    ladders = [[8 ,52],
               [6 ,80],
               [26, 42],
               [2 ,72]]

    snakes = [[51, 19],
              [39, 11],
              [37, 29],
              [81, 3],
              [59, 5],
              [79, 23],
              [53, 7],
              [43, 33],
              [77, 21]]
    print(quickestWayUp(ladders, snakes))
