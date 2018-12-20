def countTriplets(arr, r):
    count = 0
    dict = {}
    dictPairs = {}
    for i in reversed(arr):
        if i*r in dictPairs:
            count += dictPairs[i*r]
        if i*r in dict:
            dictPairs[i] = dictPairs.get(i, 0) + dict[i*r]
        dict[i] = dict.get(i, 0) + 1
        print("Dict", dict)
        print("dictPairs:", dictPairs)
        print("")
    return count
if __name__ == "__main__":
    print(countTriplets([1,2,2,4], 2))
