import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 15, 06, 2022
 * @Description: Given a path of a directory, count all the files in it. Note that hidden files also count, while
 *      directories do not count.
 * @Note:   1. BFS/DFS
 *          2. Only put directory into queue/stack.
 */
public class Code02_CountFile {

    public static int countFile(String path) {
        File root = new File(path);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Queue<File> queue = new LinkedList<>();
        queue.add(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            File folder = queue.poll();
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    ans++;
                } else if (file.isDirectory()) {
                    queue.add(file);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String path = "D:\\IDEA_workspace\\my_algorithm\\src";
        System.out.println(countFile(path));
    }

}
