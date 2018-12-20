
def abbreviation(a, b):
    if a and b:
        if a[-1] == b[-1]:
            a.pop()
            b.pop()
        elif a[-1].upper() != b[-1]:
            return False
        elif a[-1].islower():
            abbreviation(a.pop(), b)
            abbreviation(a[:-1] + a[-1].upper(), b)
    elif a == "" and b != "":
        return False
    elif a == "" and b == "":
        return True
    elif a != "" and b == "":
        for val in a:
            if a.isUpper():
                return False
        return True
