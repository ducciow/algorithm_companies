/**
 * @Author: duccio
 * @Date: 15, 06, 2022
 * @Description: Given a sorted integer array representing points on a coordinate, and a positive integer representing
 *      the length of a cord, return the maximum number of points the cord can cover.
 * @Note:   Ver1. O(N) - sliding window.
 *          Ver2. O(N*logN) - since the array is sorted, for each end index, use binary search for the appropriate left
 *                index.
 */
public class Code01_CordCoverMaxPoint {

    public static int maxPoint1(int[] arr, int k) {
        int ans = 0;
        int L = 0;
        int R = 0;
        while (L < arr.length) {
            while (R < arr.length && arr[R] - arr[L] <= k) {
                R++;
            }
            ans = Math.max(ans, R - L);
            L++;
        }
        return ans;
    }

    public static int maxPoint2(int[] arr, int k) {
        int ans = 0;
        for (int R = 0; R < arr.length; R++) {
            int neatestIdx = nearestIndex(arr, R, arr[R] - k);
            ans = Math.max(ans, R - neatestIdx + 1);
        }
        return ans;
    }

    private static int nearestIndex(int[] arr, int R, int v) {
        int L = 0;
        int ans = R;
        while (L <= R) {
            int mid = L + (R - L) / 2;
            if (arr[mid] >= v) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return ans;
    }

}
