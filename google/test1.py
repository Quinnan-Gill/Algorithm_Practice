def solution(A):
    # write your code in Python 3.6
    def odd_jump(A, j):
        print(A[j], end=" ")
        minimal = float('inf')
        min_index = -1
        for k in range(j+1, len(A)):
            if A[j] < A[k]:
                if A[k]-A[j] < minimal:
                    minimal = A[k]-A[j]
                    min_index = k
        print(minimal, end=" ")
        print(A[min_index], end= " ")
        return min_index

    def even_jump(A, j):
        minimal = float('inf')
        min_index = -1
        for k in range(j+1, len(A)):
            if A[j] > A[k]:
                if A[j]-A[k] < minimal:
                    minimal = A[j] - A[k]
                    min_index = k
        return min_index

    possible = 0
    for i in range(len(A)):
        count = 1
        index = i
        while index != -1 and index != len(A) -1:
            if count%2 == 1:
                index = odd_jump(A, index)
            else:
                index = even_jump(A, index)
            count += 1
        print(index)
        if index == len(A) -1:
            possible += 1
    return possible
print(solution([10, 13, 12, 14, 15]))
