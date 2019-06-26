from collections import defaultdict

def contacts(queries):
    trie_map = defaultdict(int)
    result = []
    for q in queries:
        if q[0] == "add":
            for j in range(1, len(q[1])+1):
                sub = q[1][0:j]
                trie_map[sub] += 1
        else:
            if q[1] not in trie_map:
                result.append(0)
            else:
                result.append(trie_map[q[1]])
    print(trie_map)
    return result

if __name__ == "__main__":

    queries = [["add", "hack"],
               ["add", "hackerrank"],
               ["find", "hac"],
               ["find", "hak"]]

    print(contacts(queries))
