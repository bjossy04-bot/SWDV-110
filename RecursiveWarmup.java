public class RecursiveWarmup {

    // 1. Add all digits together
    public static int sumDigits(int n) {
        if (n < 10) {
            return n;
        }
        return n % 10 + sumDigits(n / 10);
    }

    // 2. Calculate powers without Math.pow()
    public static int power(int base, int exp) {
        if (exp == 0) {
            return 1;
        }
        return base * power(base, exp - 1);
    }

    // 3. Count occurrences of a character
    public static int countChar(String str, char c) {
        if (str.isEmpty()) {
            return 0;
        }

        int count = (str.charAt(0) == c) ? 1 : 0;
        return count + countChar(str.substring(1), c);
    }

    // 4. Print a number in binary
    public static void printBinary(int n) {
        if (n < 2) {
            System.out.print(n);
            return;
        }

        printBinary(n / 2);
        System.out.print(n % 2);
    }

    public static void main(String[] args) {
        System.out.println(sumDigits(4096));
        System.out.println(power(2, 10));
        System.out.println(countChar("banana", 'a'));

        printBinary(10);
        System.out.println();
    }
}
