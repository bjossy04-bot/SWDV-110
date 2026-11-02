import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@FunctionalInterface
interface MathOperation {
    double apply(double a, double b);
}

public class LambdaCalculator {
    public static void main(String[] args) {
        Map<String, MathOperation> operations = new HashMap<>();

        operations.put("+", (a, b) -> a + b);
        operations.put("-", (a, b) -> a - b);
        operations.put("*", (a, b) -> a * b);
        operations.put("/", (a, b) -> a / b);
        operations.put("%", (a, b) -> a % b);

    try (Scanner scanner = new Scanner(System.in)) {

    System.out.print("Enter calculation (example: 12 * 4): ");
    String input = scanner.nextLine();

    String[] parts = input.trim().split("\\s+");

    if (parts.length == 3) {
        double num1 = Double.parseDouble(parts[0]);
        String operator = parts[1];
        double num2 = Double.parseDouble(parts[2]);

        MathOperation operation = operations.get(operator);

        if (operation != null) {
            double result = operation.apply(num1, num2);
            System.out.println("Result:" + result);
        } else {
            System.out.println("Unknown operator");
        }

        } else {
            System.out.println("Invalid input format");
        }
            
        }
    }
}
