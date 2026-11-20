import java.util.Scanner;

public class KnapSack {

    static int knapsack(int W, int wt[], int profit[], int n) {

        int dp[][] = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {

                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                }

                else if (wt[i - 1] <= w) {

                    dp[i][w] = Math.max(
                        profit[i - 1] + dp[i - 1][w - wt[i - 1]],
                        dp[i - 1][w]
                    );
                }

                else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][W];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        int profit[] = new int[n];
        int wt[] = new int[n];

        System.out.println("Enter profits of items:");

        for (int i = 0; i < n; i++) {
            profit[i] = sc.nextInt();
        }

        System.out.println("Enter weights of items:");

        for (int i = 0; i < n; i++) {
            wt[i] = sc.nextInt();
        }

        System.out.print("Enter knapsack capacity: ");
        int W = sc.nextInt();
        long startTime = System.nanoTime();
        int maxProfit = knapsack(W, wt, profit, n);
        long endTime = System.nanoTime();

        System.out.println("Maximum Profit: " + maxProfit);
        System.out.println("Time Complexity: O(n * W)");
        System.out.println("Space Complexity: O(n * W)");
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");

        sc.close();
    }
}
