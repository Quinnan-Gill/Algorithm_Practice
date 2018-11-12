memoizing_dict = {}

def getWays(n, c):
    global memoizing_dict
    if n < 0:
        return 0
    elif n == 0:
        return 1
    elif (n, len(c)) in memoizing_dict.keys():
        # print((n, len(c)))
        return memoizing_dict[(n, len(c))]
    else:
        c_sum = 0
        for c_index in range(len(c)):
            c_item = c[c_index]
            if c_item > n:
                continue
            else:
                result = getWays(n-c_item, c[c_index:])
                if result > 0:
                    c_sum += result
        memoizing_dict[(n, len(c))] = c_sum
        return c_sum

if __name__ == "__main__":
    nm = input().split()

    n = int(nm[0])

    m = int(nm[1])

    c = list(map(int, input().rstrip().split()))

    # Print the number of ways of making change for 'n' units using coins having the values given by 'c'

    ways = getWays(n, c)

    print(ways)
