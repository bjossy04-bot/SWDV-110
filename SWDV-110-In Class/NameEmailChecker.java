import java.util.Scanner;

public class NameEmailChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter your full name (First, Middle, and Last):");
        String fullName = scanner.nextLine();

        fullName = fullName.trim();

        int index1 = fullName.indexOf("");
        int index2 = fullName.lastIndexOf("");

        if (index1 == -1) {
            System.out.println("Please enter valid full name with first, middle, and last name. With Spaces between.");
        } else {
            String firstName = fullName.substring(0, index1);
            String lastName = fullName.substring(index2 + 1);
            String firstInitial = firstName.substring(0, 1);
            String lastInitial = lastName.substring(0, 1);

            System.out.println("First Name:" + firstName);
            System.out.println("Last Name:" + lastName);
            System.out.println("Initials:" + firstInitial + lastInitial);
        }
    }

}
