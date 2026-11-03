import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Calculator_Project {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    
    try {
    System.out.print("Enter First Whole Number:");
        double firstNumber = sc.nextInt();

    System.out.print("Enter Second Whole Number:");
        double secondNumber = sc.nextInt();

        double add = firstNumber + secondNumber;
        double subtract = firstNumber - secondNumber;
        double multiply = firstNumber * secondNumber;
        
        if (secondNumber == 0) {
            System.out.println("Can not divide by zero.");
            return;
        }
        double divide = firstNumber / secondNumber;

    ArrayList<Double> numbers = new ArrayList<>();
        numbers.add(add);
        numbers.add(subtract);
        numbers.add(multiply);
        numbers.add(divide);

        for (double result : numbers) {
            System.out.println(result);
        }

    try {
        FileWriter writer = new FileWriter("transaction.txt");

        writer.write(firstNumber + "+" + secondNumber + "=" + add + "\n");
        writer.write(firstNumber + "-" + secondNumber + "=" + subtract + "\n");
        writer.write(firstNumber + "*" + secondNumber + "=" + multiply + "\n"); 
        writer.write(firstNumber + "/" + secondNumber + "=" + divide + "\n");   
        
        writer.close();

        System.out.println("Transactions Saved Succsefully.");
    } catch (IOException e) {
        System.out.println("Error Saving Transaction:" + e.getMessage());
    }
    
    } catch (java.util.InputMismatchException e) {
        System.out.println("Error: Please enter valid numbers.");
    }

        sc.close();
    }
}
