def countSwaps(a):
    counter = 0
    for i in range(0, len(a)):
        for j in range(0, len(a)-1):
            print("A[j]", a[j], "and A[j+1]", a[j+1])
            if a[j] > a[j+1]:
                print("Swapping {0} with {1}".format(a[j], a[j+1]))
                counter += 1
                swap(a, j, j+1)

    print(a)
    print("Array is sorted in {0} swaps.".format(counter))
    print("First Element: {0}".format(a[0]))
    print("Last Element: {0}".format(a[len(a)-1]))

def swap(lst, c, d):
    lst[c], lst[d] = lst[d], lst[c]

countSwaps([3,2,1])
