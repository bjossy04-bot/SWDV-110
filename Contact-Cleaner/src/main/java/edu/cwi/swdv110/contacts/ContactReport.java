package edu.cwi.swdv110.contacts;

import java.util.List;

/**
 * Builds the printable report for cleaned contacts.
 */
public final class ContactReport {

    private static final String ROW = "%-22s %-30s %-15s %-9s%n";

    private ContactReport(){
    }

    public static String buildReport(List<Contact> contacts) {
        // TODO Part 5: Build the contact report.
        StringBuilder report = new StringBuilder();

        int invalidEmails = 0;
        int invalidPhones = 0;

        // Create the table header
        report.append(String.format(ROW, "NAME", "EMAIL", "PHONE", "USERNAME"));

        report.append("-".repeat(78)).append(System.lineSeparator());

        // Process each contact
        for (Contact contact : contacts) {
            String email = contact.email();

            // Flag invaild emails
            if (!ContactParser.isValidEmail(email)) {
                invalidEmails++; 
                email = "!! " + email;
            }

            // Count invalid phone numbers
            if (contact.phone().equals("INVALID")) {
                invalidPhones++;
            }

            // Generate username
            String username = ContactParser.makeUsername(contact.name());

            // Add the contact to the report
            report.append(String.format(ROW,contact.name(),email,contact.phone(),username));
        }

        // Add the bottom dividing line
        report.append("-".repeat(78)).append(System.lineSeparator());

        // Add the totals
        report.append("Total contacts: ").append(contacts.size()).append(" | Invalid emails: ").append(invalidEmails).append(" | Invalid phones: ").append(invalidPhones);

        return report.toString();
    }
}
