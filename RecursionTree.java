import java.util.Scanner;

public class RecursionTree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n (power of 2):- ");
        int n = sc.nextInt();

        System.out.print("Enter base cost T(1): ");
        int base = sc.nextInt();

        // Simple bug/validation check (no complex bitwise operator)
        if (n <= 0 || base < 0) {
            System.out.println("Enter a positive power of 2 and non-negative base value.");
            sc.close();
            return;
        }

        int levels = 0;
        int temp = n;

        // 1. Total levels count karna (n ko 2 se divide karte hue)
        while (temp > 1) {
            temp = temp / 2;
            levels++;
        }

        int totalWork = 0;
        int subproblems = 1;

        System.out.println("\n--- Recursion Tree Analysis ---");

        // 2. Har level ka work calculate karna
        for (int i = 0; i < levels; i++) {
            System.out.println("Level " + i + ": " + subproblems + " subproblems, Work = " + n);
            totalWork = totalWork + n;
            subproblems = subproblems * 2;
        }

        // 3. Last (Leaf) level ka work add karna
        int leafWork = n * base;
        totalWork = totalWork + leafWork;

        System.out.println("Leaf Level (" + levels + "): " + subproblems + " subproblems, Work = " + leafWork);
        System.out.println("\nTotal Work = " + totalWork);
        System.out.println("Time Complexity: O(n log n)");
        System.out.println("Space Complexity: O(1)");

        sc.close();
    }
}
