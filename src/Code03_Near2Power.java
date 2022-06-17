/**
 * @Author: duccio
 * @Date: 15, 06, 2022
 * @Description: Given a non-negative integer, return the nearest power of 2 that is no smaller than it. Requiring not
 *      using iterations.
 * @Note:   Bit operation:
 *          - Set all lower digits to be 1, and return the carry 1.
 */
public class Code03_Near2Power {

    public static int tableSizeFor(int n) {
        if (n == 0) {
            return 1;
        }
        n--;  // in case of n is already a power of 2
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }

    public static void main(String[] args) {
        int n = 120;
        System.out.println(tableSizeFor(n));
    }

}
