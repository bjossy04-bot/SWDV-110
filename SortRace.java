import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class SortRace {
    public static void main(String[] args) {

        // Create 50,000 random numbers
        int[] numbers = new int[100000];

        Random random = new Random();

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt();
        }

        System.out.println("Created 100,000 random numbers!");

        // Make three copies of the original array
        int[] selectionArray = Arrays.copyOf(numbers, numbers.length);
        int[] mergeArray = Arrays.copyOf(numbers, numbers.length);
        int[] builtInArray = Arrays.copyOf(numbers, numbers.length);

        System.out.println("Created three copies of the array!");

        // Time Selection Sort
        Instant start = Instant.now();
        selectionSort(selectionArray);
        Instant end = Instant.now();

        Duration selectionTime = Duration.between(start, end);
        System.out.println("Selection Sort: " + selectionTime.toMillis() + " ms");

        // Time Merge Sort
        start = Instant.now();
        mergeSort(mergeArray);
        end = Instant.now();

        Duration mergeTime = Duration.between(start, end);
        System.out.println("Merge Sort: " + mergeTime.toMillis() + " ms");

        // Time Arrays.sort()
        start = Instant.now();
        Arrays.sort(builtInArray);
        end = Instant.now();

        Duration builtInTime = Duration.between(start, end);
        System.out.println("Arrays.sort: " + builtInTime.toMillis() + " ms");

        // Verify all three arrays are sorted
        System.out.println("Selection Sort sorted: " + isSorted(selectionArray));
        System.out.println("Merge Sort sorted: " + isSorted(mergeArray));
        System.out.println("Arrays.sort sorted: " + isSorted(builtInArray));
    }

    // Selection Sort method
    public static void selectionSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // Merge Sort method
    public static void mergeSort(int[] arr) {
        if (arr.length < 2) {
            return;
        }

        int mid = arr.length / 2;

        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    // Merge two sorted arrays
    public static void merge(int[] arr, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

     // Check if an array is sorted
    public static boolean isSorted(int[] arr) {

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }

        return true;
    }
}
