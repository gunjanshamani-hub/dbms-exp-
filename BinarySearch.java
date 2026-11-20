import java.util.Scanner;

public class BinarySearch {

    static int binarySearch(int[] arr, int low, int high, int key) {

        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        if (arr[mid] == key) {
            return mid;
        }

        if (key < arr[mid]) {
            return binarySearch(arr, low, mid - 1, key);
        }

        return binarySearch(arr, mid + 1, high, key);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter sorted elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.print("Enter element to search: ");
        int key = sc.nextInt();

        long startTime = System.nanoTime();

        int result = binarySearch(arr, 0, n - 1, key);

        long endTime = System.nanoTime();

        long timeTaken = endTime - startTime;

        if (result != -1) {
            System.out.println("Element " + key +
                    " found at position " + (result + 1));
        } else {
            System.out.println("Element not found");
        }

        System.out.println("Time consumed: " + timeTaken + " nanoseconds");

        System.out.println("\nTime Complexity:");
        System.out.println("Best Case    : O(1)");
        System.out.println("Average Case : O(log n)");
        System.out.println("Worst Case   : O(log n)");

        System.out.println("Space Complexity: O(log n)");

        sc.close();
    }
}


