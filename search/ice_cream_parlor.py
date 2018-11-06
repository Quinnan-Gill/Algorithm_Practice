def whatFlavors(cost, money):
    cost_map = {}
    for i, c in enumerate(cost):
        print(cost_map)
        sunny = c
        john = money - c
        if john in cost_map.keys():
            print("Result:", cost_map[john]+1, i+1)
        else:
            cost_map[sunny] = i

if __name__ == "__main__":
    whatFlavors([1, 4, 5, 3, 2], 4)
    whatFlavors([2, 2, 4, 3], 4)
