import java.util.Scanner;

public class LoginChecker {
    public static void main(String[] args) {

        String username = "Admin";
        String password = "Secret123";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please Enter Username:");
        String inputUsername = scanner.nextLine();

        System.out.println("Please Enter Password:");
        String inputPassword = scanner.nextLine();

        boolean isUsernameValid = username.equalsIgnoreCase(inputUsername);

        boolean isPasswordVaild = password.equals(inputPassword);

        if (isUsernameValid && isPasswordVaild) {
            System.out.println("Welcome");
        } else {
            System.out.println("Access Denied");
        }

        scanner.close();
    }
}
