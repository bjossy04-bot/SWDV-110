import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class LibraryDueDates {
    public static void main(String[] args) {
        
        LocalDate checkoutDate = LocalDate.now();
        Scanner scanner = new Scanner(System.in);
        LocalDate dueDate = checkoutDate.plusDays(21);

        if (dueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            dueDate = dueDate.plusDays(2);
        } else if (dueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            dueDate = dueDate.plusDays(1);
        }

        long daysUntilDue = ChronoUnit.DAYS.between(checkoutDate, dueDate);

        System.out.println("Checkout Date:" + checkoutDate);
        System.out.println("Due Date:" + dueDate);
        System.out.println("Days Unitl Due:" + daysUntilDue);

        System.out.println("Enter return date (YYYY-MM-DD):");
        String returnInput = scanner.nextLine();

        LocalDate returnDate = LocalDate.parse(returnInput);

        System.out.println("Return Date:" + returnDate);

        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

        if (daysLate < 0) {
            daysLate = 0;
        }

        double lateFee = daysLate * 0.25;
        NumberFormat currency = NumberFormat.getCurrencyInstance();

        System.out.println("Days Late:" + daysLate);
        System.out.println("Late Fee:" + currency.format(lateFee));
    }
}
