/*
Given a string, find the length of the longest substring without repeating characters.
Examples:
Given "abcabcbb", the answer is "abc", which the length is 3.
Given "bbbbb", the answer is "b", with the length of 1.
Given "pwwkew", the answer is "wke", with the length of 3.
Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */

/**
 * Approach 1: Sliding Window
 * Using Sliding Window Template
 * Detail explanation about the template is here:
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        int ans = 0;
        int counter = 0;
        for (int left = 0, right = 0; right < s.length(); right++) {
            // count > 0 means repeating character
            char cRight = s.charAt(right);
            map.put(cRight, map.getOrDefault(cRight, 0) + 1);
            if (map.get(cRight) > 1) {
                counter++;
            }

            while (counter > 0) {
                char cLeft = s.charAt(left);
                map.put(cLeft, map.get(cLeft) - 1);
                // map.get(cLeft) == 1 means the cLeft is the duplicated character, 
                // and we have remove it, so after left++, it will be distinct.
                if (map.get(cLeft) == 1) {
                    counter--;
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}

/**
 * Approach 2: Sliding Window (Optimized)
 * The above solution requires at most 2n steps.
 * In fact, it could be optimized to require only n steps.
 * We could define a mapping of the characters to its index.
 * Then we can skip the characters immediately when we found a repeated character.
 *
 * The reason is that if s[j] have a duplicate in the range [i, j)with index j',
 * we don't need to increase i little by little. We can skip all the elements in the range [i, j']
 * and let i to be j' + 1 directly.
 *
 * Complexity Analysis
 * Time complexity : O(n). Index j will iterate n times.
 * Space complexity : O(min(m, n)). Same as the previous approach.
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        // current index of character
        Map<Character, Integer> map = new HashMap<>();
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}