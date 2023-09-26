package solutions;

import java.util.PriorityQueue;
import java.util.Queue;

public class CookiesProblem {
    public Integer solve(int k, int[] cookies) {
        Queue<Integer> queue = new PriorityQueue<>();

        for (int cookie : cookies) {
            queue.offer(cookie);
        }
        int currentMinSweetness = queue.peek();
        int steps = 0;
        while (currentMinSweetness < k && queue.size() > 1) {
            int leastSweet = queue.poll();
            int secondLeastSweet = queue.poll();

            int combinedSweetness = leastSweet + 2 * secondLeastSweet;

            queue.offer(combinedSweetness);

            currentMinSweetness = queue.peek();
            steps++;
        }
        return currentMinSweetness > k ? steps : -1;
    }
}
