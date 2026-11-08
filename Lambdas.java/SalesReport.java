import java.util.*;
import java.util.stream.Collectors;

public class SalesReport {

    record Sale(String product, String region, int qty, double price) {}

    public static void main(String[] args) {

        List<Sale> sales = List.of(
            new Sale("Laptop", "West", 2, 800.00),
            new Sale("Mouse", "East", 5, 25.00),
            new Sale("Keyboard", "North", 3, 50.00),
            new Sale("Monitor", "South", 2, 200.00),
            new Sale("Laptop", "East", 1, 800.00),
            new Sale("Mouse", "West", 4, 25.00),
            new Sale("Headphones", "North", 3, 60.00),
            new Sale("Keyboard", "South", 2, 50.00),
            new Sale("Monitor", "West", 1, 200.00),
            new Sale("Headphones", "East", 2, 60.00)
        );

        double totalRevenue = sales.stream().mapToDouble(s -> s.qty() * s.price()).sum();
        System.out.println("Toatl Revenue: $" + totalRevenue);

        System.out.println("\nProducts with revenue over $100:");
        sales.stream().filter(s -> s.qty() * s.price() > 100).map(Sale::product).distinct().sorted().forEach(System.out::println);

        sales.stream().max(Comparator.comparingDouble(s -> s.qty() * s.price())).ifPresent(s -> System.out.println("\nBiggest Sale: " + s.product() + " - $" + (s.qty() * s.price())));

        Map<String, Double> revenueByRegion = sales.stream().collect(Collectors.groupingBy(Sale::region, Collectors.summingDouble(s -> s.qty() * s.price())));
        System.out.println("\nRevenue by Region:");
        revenueByRegion.forEach((region, revenue) -> System.out.println(region + ": $" + revenue));

    }
}

