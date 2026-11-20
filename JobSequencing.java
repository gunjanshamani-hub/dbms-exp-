import java.util.Scanner;
import java.util.Arrays;

public class JobSequencing {

    static class Job {
        int id, deadline, profit;

        Job(int id, int deadline, int profit) {
            this.id = id;
            this.deadline = deadline;
            this.profit = profit;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of jobs: ");
        int n = sc.nextInt();

        Job jobs[] = new Job[n];

        for (int i = 0; i < n; i++) {

            System.out.println("Enter deadline and profit for Job " + (i + 1) + ":");

            int deadline = sc.nextInt();
            int profit = sc.nextInt();

            jobs[i] = new Job(i + 1, deadline, profit);
        }
        
        long startTime = System.nanoTime();
        Arrays.sort(jobs, (a, b) -> b.profit - a.profit);
        int maxDeadline = 0;
        for (int i = 0; i < n; i++) {
            maxDeadline = Math.max(maxDeadline, jobs[i].deadline);
        }

        int slots[] = new int[maxDeadline + 1];
        Arrays.fill(slots, -1);

        int totalProfit = 0;
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = jobs[i].deadline; j > 0; j--) {
                if (slots[j] == -1) {
                    slots[j] = jobs[i].id;
                    totalProfit += jobs[i].profit;
                    count++;
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        System.out.println("\nSelected Jobs:");
        for (int i = 1; i <= maxDeadline; i++) {
            if (slots[i] != -1) {
                System.out.println("Slot " + i + ": Job " + slots[i]);
            }
        }

        System.out.println("Total Jobs Scheduled: " + count);
        System.out.println("Maximum Profit: " + totalProfit);
        System.out.println("Time Complexity: O(n log n + n * d)");
        System.out.println("Space Complexity: O(n + d)");
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");

        sc.close();
    }
}
