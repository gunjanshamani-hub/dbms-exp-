
import java.util.Scanner;

public class MasterMethod {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter value of a: ");
        int a = sc.nextInt();

        System.out.print("Enter value of b: ");
        int b = sc.nextInt();

        System.out.print("Enter power k of f(n) = n^k: ");
        int k = sc.nextInt();

        if (a < 1 || b <= 1 || k < 0) {
            System.out.println("Enter valid values.");
            sc.close();
            return;
        }

        double logValue = Math.log(a) / Math.log(b);

        System.out.println("\nRecurrence Relation:");
        System.out.println("T(n) = " + a + "T(n/" + b + ") + n^" + k);

        System.out.printf("log_b(a) = %.4f%n", logValue);

        System.out.println("\nMaster Method Analysis:");

        if (k < logValue) {

            System.out.println("Case 1 applies.");
            System.out.println("Time Complexity = O(n^" +
                    String.format("%.4f", logValue) + ")");

        } else if (k == logValue) {

            System.out.println("Case 2 applies.");
            System.out.println("Time Complexity = O(n^" + k +
                    " log n)");

        } else {

            System.out.println("Case 3 applies.");
            System.out.println("Time Complexity = O(n^" + k + ")");
        }

        System.out.println("Space Complexity = O(log n) recursion stack");

        sc.close();
    }
}
