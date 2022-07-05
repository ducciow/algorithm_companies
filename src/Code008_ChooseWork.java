import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * @Author: duccio
 * @Date: 05, 07, 2022
 * @Description: Given two integer arrays representing the hardness and payment of a list of jobs, and given another
 *      integer array representing workers' abilities, for each worker choose a job so that his or her ability is no
 *      less than the job's hardness. Each job can be taken repeatedly, return the array of jobs chosen with the max
 *      total payment for all workers.
 * @Note:   Greedy + TreeMap + self-defined comparator:
 *          1. Sort all jobs preferring easier and then more-paid jobs.
 *          2. Remove less-paid jobs with equal or higher hardness, which can be easier done by TreeMap.
 *          3. For each worker, find the floor hardness for him or her, and then assign the job in O(1).
 */
public class Code008_ChooseWork {

    public static class Job {
        public int hard;
        public int pay;

        public Job(int h, int p) {
            hard = h;
            pay = p;
        }
    }

    public static class JobComparator implements Comparator<Job> {

        @Override
        // prioritize easier and then more-paid jobs
        public int compare(Job o1, Job o2) {
            return o1.hard != o2.hard ? o1.hard - o2.hard : o2.pay - o1.pay;
        }
    }

    public static int[] choose(Job[] jobs, int[] abilities) {
        // sort all jobs
        Arrays.sort(jobs, new JobComparator());
        // remove certain jobs
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(jobs[0].hard, jobs[0].pay);
        Job pre = jobs[0];
        for (int i = 1; i < jobs.length; i++) {
            // only keep such jobs:
            if (jobs[i].hard > pre.hard && jobs[i].pay > pre.pay) {
                map.put(jobs[i].hard, jobs[i].pay);
                pre = jobs[i];
            }
        }
        int[] ans = new int[abilities.length];
        for (int i = 0; i < abilities.length; i++) {
            Integer floor = map.floorKey(abilities[i]);
            ans[i] = floor != null ? map.get(floor) : 0;
        }
        return ans;
    }

}
