import java.time.LocalDate;
import java.util.Scanner;

public class BirthdayProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your birthday (yyyy-MM-dd):");
        String date = scanner.nextLine();

        LocalDate birthday = LocalDate.parse(date);
        LocalDate today = LocalDate.now();
        LocalDate thisYearsBirthday = birthday.withYear(today.getYear());
        boolean hasPassed = thisYearsBirthday.isBefore(today);

        System.out.println("Your Birthday is:" + birthday);
        System.out.println("You were born on a:" + birthday.getDayOfWeek());
        System.out.println("Day of the year:" + birthday.getDayOfYear());
        System.out.println("Was it a leap year?:" + birthday.isLeapYear());
        System.out.println("Has your birthday passed?:" + hasPassed);

        scanner.close();
    }
}
