public class BinarySearch {
    static int checks = 0;
    
    public static int binarySearch(int[] a, int target, int low, int high) {
        // Base case: Number not found
        if (low > high) {
            return -1;
        }

        checks++;

        int mid = low + (high - low) / 2;

        // Base case: Number found
        if (a[mid] == target) {
            return mid;
        }

        // Search left half
        if (target < a[mid]) {
            return binarySearch(a, target, low, mid - 1);
        }

        // Search right half
        return binarySearch(a, target, mid + 1, high);
    }

    public static void main(String[] args) {

        int[] numbers = new int[1000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        int[] targets = {1, 1000, 500, 1500};

        for (int target : targets) {
            checks = 0;

            int result = binarySearch(numbers, target, 0, numbers.length - 1);

            System.out.println("Target: " + target);
            System.out.println("Index: " + result);
            System.out.println("Binary checks: " + checks);
            linearSearch(numbers, target);
            System.out.println();
        }
    }

    public static int linearSearch(int[] a, int target) {
        int linearChecks = 0;

        for (int i = 0; i < a.length; i++) {
            linearChecks++;

            if (a[i] == target) {
                System.out.println("Linear checks: " + linearChecks);
                return i;
            }
        }

        System.out.println("Linear checks: " + linearChecks);
        return -1;
    }
}
