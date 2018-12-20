def twoStrings(a, b):
    freq = {}
    for c1 in a:
        freq[c1] = True
    for c2 in b:
        if c2 in freq:
            return "YES"
    return "NO"

    
