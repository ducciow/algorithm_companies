/**
 * @Author: duccio
 * @Date: 17, 06, 2022
 * @Description: Given a string only containing 'G's and 'B's, swap all 'G's to the left and 'B's to the right, or the
 *      other way around. Requiring the swap only takes place for adjacent letters, return the minimum number of swaps.
 * @Note:   O(N) - Greedy:
 *          - Latter 'G'/'B' does not need to proceed the former 'G'/'B' after swaps. So use a pointer indicate the
 *            boundary of the former part already meeting the requirement.
 */
public class Code004_MinSwapOfTwoLetters {

    public static int minSwap(String s) {
        if (s == null || s.length() < 3) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int N = chars.length;
        int step1 = 0;
        int gi = 0;
        for (int i = 0; i < N; i++) {
            if (chars[i] == 'G') {
                step1 += i - gi;
                gi++;
            }
        }
        int step2 = 0;
        int bi = 0;
        for (int i = 0; i < N; i++) {
            if (chars[i] == 'B') {
                step2 += i - bi;
                bi++;
            }
        }
        return Math.min(step1, step2);
    }

}
