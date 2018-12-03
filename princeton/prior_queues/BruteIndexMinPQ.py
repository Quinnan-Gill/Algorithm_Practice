class Solution:
    def canCross(self, stones):
        """
        :type stones: List[int]
        :rtype: bool
        """

        target = stones[-1]

        def binSearch(aList, target):
            binSearchHelp(aList, target, 0, len(aList))

        def binSearchHelp(aList, target, start, end):
            if end-start+1 <= 0:
                return -1
            else:
                midpoint = start + (end - start) // 2
                if aList[midpoint] == target:
                    return midpoint
                else:
                    if target < aList[midpoint]:
                        return binSearchHelp(aList, target, start, midpoint-1)
                    else:
                        return binSearchHelp(aList, target, midpoint+1, end)

        stack = [[stones[0], 0, stones[1:]]]


        while stack:
            print(stack)
            val, k, stones_left = stack.pop()

            if val == target:
                return True

            k_min = binSearch(stones_left, val + k-1)
            k_cur = binSearch(stones_left, val + k)
            k_pul = binSearch(stones_left, val + k+1)

            if k_min != -1:
                print(k_min)
                stack.append([stones_left[k_min], k-1, stones_left[k_min+1:]])

            if k_cur != -1:
                stack.append([stones_left[k_cur], k, stones_left[k_cur+1:]])

            if k_pul != -1:
                stack.append([stones_left[k_pul], k+1, stones_left[k_cur+1:]])

        return False

a = [0,1,3,4,5,7,9,10,12]
b = [0,1,2,3,4,8,9,11]

sol = Solution()

print(sol.canCross(a))
