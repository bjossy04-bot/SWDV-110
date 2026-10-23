package edu.cwi.swdv110.contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class App {

    private App() {}

    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();

        try {
            List<String> lines =
                    Files.readAllLines(Path.of("contacts.txt"));

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    contacts.add(ContactParser.parseLine(line));
                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Skipped line " + (i + 1)
                            + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Could not read contacts.txt: " + e.getMessage());
            return;
        }

        // Display the completed report
        System.out.println(ContactReport.buildReport(contacts));

        // Let the user search for contacts
        try (Scanner scanner = new Scanner(System.in)) {

    while (true) {
        System.out.print("Search (or 'quit'): ");
        String query = scanner.nextLine().trim();

        // Exit when the user types quit
        if (query.equalsIgnoreCase("quit")) {
            System.out.println("Bye!");
            break;
        }

        int matches = 0;

        // Search contacts by name
        for (Contact contact : contacts) {
            if (contact.name().toLowerCase()
                    .contains(query.toLowerCase())) {

                System.out.println(
                        contact.name() + " - "
                        + ContactParser.maskEmail(contact.email()));

                matches++;
            }
        }

        System.out.println(matches + " match(es)");
    }
}
    }
}

