def solution(S, K):
    # write your code in Python 3.6
    if S == None or K <= 0:
        return

    result = ""
    count = 0
    for i, c in enumerate(reversed(S)):
        if c != "-":
            result = c + result
            count += 1
        if count == K and i != len(S)-1:
            result = "-" + result
            count = 0

    return result

print(solution("2-4A0r7-4k",3))
