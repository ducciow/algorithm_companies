import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 17, 06, 2022
 * @Description: Given a non-negative inter array and an integer target, add signs to each element in the array to form
 *      an expression that is evaluated to the target. Return the number of such expressions.
 *      https://leetcode.com/problems/target-sum/
 * @Note:   Ver1. DP with memory cache:
 *                - For each element, collect the afterwards answer of its two sign options.
 *                - Use a outer-inner hash map for lookup.
 *          Ver2. DP with optimization:
 *                - Let set P = {+ signed elem}, N = {- signed elem}
 *                - Then sum_P - sum_N = target, and sum_P + sum_N = all_Sum, which gives sum_P = (target + all_sum) / 2.
 *                - The dp progress turns to be a backpacking problem.
 *                - Space compression for the dp table can be utilized.
 *
 */
public class Code06_TargetSum {

    // space O(N*2sum)
    public static int findTargetSumWays1(int[] nums, int target) {
        HashMap<Integer, HashMap<Integer, Integer>> lookup = new HashMap<>();
        return process1(nums, 0, target, lookup);
    }

    public static int process1(int[] nums, int idx, int target, HashMap<Integer, HashMap<Integer, Integer>> lookup) {
        if (lookup.containsKey(idx) && lookup.get(idx).containsKey(target)) {
            return lookup.get(idx).get(target);
        }
        int ans = 0;
        if (idx == nums.length) {
            ans = target == 0 ? 1 : 0;
        } else {
            ans = process1(nums, idx + 1, target + nums[idx], lookup)
                    + process1(nums, idx + 1, target - nums[idx], lookup);
        }
        if (!lookup.containsKey(idx)) {
            lookup.put(idx, new HashMap<>());
        }
        lookup.get(idx).put(target, ans);
        return ans;
    }

    // optimize the space to O(N*sum)
    public static int findTargetSumWays2(int[] nums, int target) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (target > sum) {  // target cannot exceed the sum
            return 0;
        }
        if (((target & 1) ^ (sum & 1)) != 0) {  // target must have the same parity with sum
            return 0;
        }
        return subset2(nums, (target + sum) >> 1);
    }

    // dp, which is basically a backpacking
    // considering nums[0...i], to get the target j, how many ways of packing is there
    public static int subset1(int[] nums, int target) {
        if (target < 0) {
            return 0;
        }
        int N = nums.length;
        int[][] dp = new int[N][target + 1];
        dp[0][0] = 1;  // meaning drop nums[0] to get the target 0
        if (target >= nums[0]) {
            dp[0][nums[0]] += 1;  // for the 0-th row, only column equaling to nums[0] can be satisfied
        }
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];  // drop nums[i]
                if (j - nums[i] >= 0) {
                    dp[i][j] += dp[i - 1][j - nums[i]];  // pack nums[i]
                }
            }
        }
        return dp[N - 1][target];
    }

    // use space compression
    public static int subset2(int[] nums, int target) {
        if (target < 0) {
            return 0;
        }
        int N = nums.length;
        int[] dp = new int[target + 1];
        dp[0] = 1;  // meaning drop nums[0] to get the target 0
        if (target >= nums[0]) {
            dp[nums[0]] += 1;  // for the 0-th row, only column equaling to nums[0] can be satisfied
        }
        for (int i = 1; i < N; i++) {
            for (int j = target; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }

}
