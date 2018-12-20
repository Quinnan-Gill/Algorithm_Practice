
memo = []

def abbreviation(a,b):
    global memo
    memo = []
    if abbreviationHelper(a, b):
        return "YES"
    else:
        return "NO"

def abbreviationHelper(a, b):
    # get all the lowercase letter positions
    if a == b:
        return True

    global memo

    print("Memo", memo)
    lwrc = [i for i, a in enumerate(a) if a.islower()]

    del_res = False
    up_res = False

    for lower in lwrc:
        delete = a[:lower] + a[lower+1:]
        upper = a[:lower] + a[lower].upper() + a[lower+1:]

        if delete not in memo:
            memo.append(delete)
            del_res = abbreviationHelper(delete, b)
        if upper not in memo:
            memo.append(upper)
            up_res = abbreviationHelper(upper, b)
        return del_res or up_res
    return False

if __name__ == "__main__":
    print(abbreviation("AbCdE", "AFE"))
    print(abbreviation("beFgH", "EFG"))
    print(abbreviation("beFgH","EFH"))
