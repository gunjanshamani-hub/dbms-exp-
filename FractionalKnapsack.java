import java.util.*;
class Item {
    int itemNo;
    double profit;
    double weight;
    double ratio;
    Item(int itemNo, double profit, double weight) {
        this.itemNo = itemNo;
        this.profit = profit;
        this.weight = weight;
        this.ratio = profit / weight;
    }
}

public class FractionalKnapsack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long startTime = System.nanoTime();
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details for Item " + (i + 1));

            System.out.print("Enter Profit: ");
            double profit = sc.nextDouble();

            System.out.print("Enter Weight: ");
            double weight = sc.nextDouble();

            items[i] = new Item(i + 1, profit, weight);
        }

        System.out.print("\nEnter Knapsack Capacity: ");
        double capacity = sc.nextDouble();

        Arrays.sort(items, (a, b) ->
                Double.compare(b.ratio, a.ratio));

        double remainingCapacity = capacity;
        double maximumProfit = 0;
        double[] fractionTaken = new double[n];

        for (int i = 0; i < n; i++) {

            if (remainingCapacity == 0)
                break;

            if (items[i].weight <= remainingCapacity) {

                fractionTaken[i] = 1.0;

                remainingCapacity -= items[i].weight;
                maximumProfit += items[i].profit;

            } else {
                fractionTaken[i] = remainingCapacity / items[i].weight;
                maximumProfit += items[i].profit * fractionTaken[i];
                remainingCapacity = 0;
            }
        }

        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;

        System.out.println("\n========== RESULT ==========");

        System.out.printf("%-8s %-10s %-10s %-12s %-15s%n",
                "Item", "Profit", "Weight", "Ratio", "Selection");

        for (int i = 0; i < n; i++) {

            double percentage = fractionTaken[i] * 100;

            System.out.printf("%-8d %-10.2f %-10.2f %-12.2f %.2f%%%n",
                    items[i].itemNo,
                    items[i].profit,
                    items[i].weight,
                    items[i].ratio,
                    percentage);
        }

        System.out.println("---------------------------------------------");
        System.out.printf("Maximum Profit = %.2f%n", maximumProfit);
        System.out.printf("Remaining Capacity = %.2f%n", remainingCapacity);

        System.out.println("\n========== COMPLEXITY ==========");
        System.out.println("Time Complexity = O(n log n)");
        System.out.println("Space Complexity = O(n)");

        System.out.println("Time Taken = " + timeTaken + " nanoseconds");
        System.out.println("Time Taken = " + (timeTaken / 1_000_000.0) + " milliseconds");

        sc.close();
    }
}
