def hourglassSum(arr):
    max_sum = 0
    index_r = 0
    index_c = 0
    for r in range(0, 4):
        for c in range(0, 4):
            temp_sum = sum(arr[r][c:c+3]) + arr[r+1][c+1] + sum(arr[r+2][c:c+3])
            if temp_sum > max_sum:
                max_sum = temp_sum
                index_r = r
                index_c = c
                print(max_sum)
    return max_sum

if __name__ == "__main__":
    # arr = [[1, 1, 1, 0, 0, 0],
    #        [0, 1, 0, 0, 0, 0],
    #        [1, 1, 1, 0, 0, 0],
    #        [0, 0, 2, 4, 4, 0],
    #        [0, 0, 0, 2, 0, 0],
    #        [0, 0, 1, 2, 4, 0]]
    arr = [[-1, -1, 0, -9, -2, -2],
           [-2, -1, -6, -8, -2, -5],
           [-1, -1, -1, -2, -3, -4],
           [-1, -9, -2, -4, -4, -5],
           [-7, -3, -3, -2, -9, -9],
           [-1, -3, -1, -2, -4, -5]]
    print(hourglassSum(arr))
