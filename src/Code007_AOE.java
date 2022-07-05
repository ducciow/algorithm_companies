/**
 * @Author: duccio
 * @Date: 17, 06, 2022
 * @Description: There are two non-negative integer arrays arr and hp, where arr is sorted and arr[i] represents the
 *      position of monster_i, and hp[i] is the life value of monster_i. Given an integer k, representing the range of
 *      an AOE attack, ie., [x-k, x+k] where x is in arr, and each attack deducts the hp by 1 of monsters in the attack
 *      range, return the minimum number of attacks needed to kill all monsters.
 * @Note:   Greedy + Segment Tree:
 *          1. Iterate through the array, treat arr[i] as the left boundary of an AOE coverage, and see what the attack
 *             affects rightmost.
 *          2. Every time updating the hp of monsters affected is an operation to a segment, so a segment tree is used.
 */
public class Code007_AOE {

    public static int min(int[] arr, int[] hp, int k) {
        int N = arr.length;
        int[] cover = new int[N];  // cover[i] is the rightmost idx in arr under the attack range with i as the leftmost
        int r = 0;
        for (int i = 0; i < N; i++) {
            while (r < N && arr[r] - arr[i] <= k) {
                r++;
            }
            cover[i] = r - 1;
        }
        SegmentTree st = new SegmentTree(hp);  // remember elem starts from idx 1 in a segment tree
        st.build(1, N, 1);
        int ans = 0;
        for (int i = 1; i <= N; i++) {
            int restHP = st.query(i, i, 1, N, 1);  // get the rest hp of the leftmost alive monster
            if (restHP > 0) {
                ans += restHP;  // use this times of the AOE skill
                st.add(i, cover[i - 1] + 1, -restHP, 1, N, 1);  // deduct hp of all monsters under the coverage
            }
        }
        return ans;
    }


    public static class SegmentTree {
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN];
            for (int i = 0; i < MAXN - 1; i++) {
                arr[i + 1] = origin[i];
            }
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        private void pushDown(int rt, int ln, int rn) {
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && R >= r) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && R >= r) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

    }


}
