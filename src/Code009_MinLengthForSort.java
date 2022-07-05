/**
 * @Author: duccio
 * @Date: 05, 07, 2022
 * @Description: Given an integer array, you need to find one continuous subarray that if you only sort this subarray
 *      in ascending order, then the whole array will be sorted in ascending order. Return the shortest such subarray
 *      and output its length.
 *      https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
 * @Note:   1. A forward pass to get the right boundary in O(n).
 *          2. A backward pass to ght the lest boundary in O(n).
 *          3. return max{0, right_boundary - left_boundary + 1}.
 */
public class Code009_MinLengthForSort {

    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int N = nums.length;
        // forward pass to get the right boundary
        int right = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            if (nums[i] < max) {
                right = i;
            }
            max = Math.max(max, nums[i]);
        }
        // backward pass to ght the lest boundary
        int left = N;
        int min = Integer.MAX_VALUE;
        for (int i = N - 1; i >= 0; i--) {
            if (nums[i] > min) {
                left = i;
            }
            min = Math.min(min, nums[i]);
        }
        return Math.max(0, right - left + 1);
    }

}
