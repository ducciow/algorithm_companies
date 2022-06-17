/**
 * @Author: duccio
 * @Date: 17, 06, 2022
 * @Description: Given a matrix of integers, start at any position of it, and go any one of four directions at each
 *      step. Return the length of the longest increasing path.
 *      https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
 * @Note:   DP with memory cache:
 *          - The main function calls process function for each starting position, and returns the max one. The process
 *            func tries all four directions at current position, and returns the longest length.
 */
public class Code05_LongestIncreasingPath {

    public static int longestIncPath(int[][] m) {
        if (m == null || m.length == 0) {
            return 0;
        }
        int[][] lookup = new int[m.length][m[0].length];
        // try every staring position
        int ans = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                ans = Math.max(ans, process(m, i, j, lookup));
            }
        }
        return ans;
    }

    // varying arguments: i and j representing the current position
    // returns the longest length from now on
    public static int process(int[][] m, int i, int j, int[][] lookup) {
        if (lookup[i][j] != 0) {
            return lookup[i][j];
        }
        int up = i > 0 && m[i][j] < m[i - 1][j] ? process(m, i - 1, j, lookup) : 0;
        int down = i < m.length - 1 && m[i][j] < m[i + 1][j] ? process(m, i + 1, j, lookup) : 0;
        int left = j > 0 && m[i][j] < m[i][j - 1] ? process(m, i, j - 1, lookup) : 0;
        int right = j < m[0].length - 1 && m[i][j] < m[i][j + 1] ? process(m, i, j + 1, lookup) : 0;
        int ans = Math.max(up, Math.max(down, Math.max(left, right))) + 1;
        lookup[i][j] = ans;
        return ans;
    }

}
