def gridlandMetro(n, m, k, track):
    result = 0
    iter_track = 0;
    if len(track) == 0:
        return n * m
    for i in range(1, n+1):
        if track[iter_track][0] != i:
            result += m
        else:
            split = track[iter_track][2] - track[iter_track][1] + 1
            result += m - split
            iter_track += 1
    return result

gridlandMetro()

# This can be a input (had to buy)
'''
n m k
1 5 3
1 1 2 <- t1
1 2 4 <- t2
1 3 5 <- t3
'''
