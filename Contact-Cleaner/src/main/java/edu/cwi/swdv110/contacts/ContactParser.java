package edu.cwi.swdv110.contacts;

/**
 * Helper methods for cleaning contact information.
 */
public final class ContactParser {

    private ContactParser() {
    }

        // Part 1: Capitalize a word.
        public static String titleCase(String word) {
            StringBuilder result = new StringBuilder(word.toLowerCase());

            for (int i = 0; i < result.length(); i++) {

                if (i == 0 || result.charAt(i - 1) == '-') {
                    result.setCharAt(i, Character.toUpperCase(result.charAt(i)));
                }
            }

            return result.toString();
        }

        // Part 1: Clean and reorder a person's name.
        public static String normalizeName(String raw) {

            String name = raw.trim();

            if (name.isEmpty()) {
                return "";
            }

            int comma = name.indexOf(',');

            if (comma != -1) {
                String last = name.substring(0, comma).trim();
                String first = name.substring(comma + 1).trim();

                name = first + " " + last;
            }

            String[] words = name.trim().split("\\s+");

            for (int i = 0; i < words.length; i++) {
                words[i] = titleCase(words[i]);
            }

            return String.join(" ", words);
        }

        // Part 2: Check whether an email is valid.
        public static boolean isValidEmail(String email) {

            String cleaned = email.trim();

            if (cleaned.indexOf(' ') != -1) {
                return false;
            }

            int atIndex = cleaned.indexOf('@');

            if (atIndex <= 0 || atIndex != cleaned.lastIndexOf('@')) {
                return false;
            }

            String domain = cleaned.substring(atIndex + 1);

            int dotIndex = domain.indexOf('.');

            if (dotIndex <= 0 || dotIndex == domain.length() - 1 || domain.indexOf("..") != -1) {
                return false;
            }

            return true;
        }

        // Part 2: Clean an email address.
        public static String normalizeEmail(String email) {
            return email.trim().toLowerCase();
        }

        // Part 2: Hide part of an email address.
        public static String maskEmail(String email) {
            int atIndex = email.indexOf('@');

            if (atIndex <= 1) {
                return email;
            }

            StringBuilder masked = new StringBuilder();

            masked.append(email.charAt(0));

            for (int i = 1; i < atIndex; i++) {
                masked.append('*');
            }

            masked.append(email.substring(atIndex));
            
            return masked.toString();
        }

        // Part 3: Format a phone number.
        public static String formatPhone(String raw) {
            StringBuilder digits = new StringBuilder();

            for (int i = 0; i < raw.length(); i++) {
                char ch = raw.charAt(i);

                if (Character.isDigit(ch)) {
                    digits.append(ch);
                }
            }

            if (digits.length() == 11 && digits.charAt(0) == '1') {
                digits.deleteCharAt(0);
            }

            if (digits.length() != 10) {
                    return "INVALID";
            }

            String number = digits.toString();

            return "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6);
        }

        // Part 4: Generate a username.
        public static String makeUsername(String fullName) {
            if (fullName.trim().isEmpty()) {
                return "";
            }

            String[] words = fullName.trim().toLowerCase().split("\\s+");

            String base;

            if (words.length == 1) {
                base = words[0];
            } else {
                base = words[0].substring(0, 1) + words[words.length - 1];
            }

            StringBuilder username = new StringBuilder();

            for (int i = 0; i < base.length(); i++) {
                char ch = base.charAt(i);

                if (ch >= 'a' && ch <= 'z') {
                    username.append(ch);
                }

                if (username.length() == 8) {
                    break;
                }
            }

            return username.toString();
        }

        // Part 2: Process one line of contact information.
        public static Contact parseLine(String line) {
            String[] fields = line.split("\\|", -1);

            if (fields.length != 3) {
                throw new IllegalArgumentException("expected 3 fields but found" + fields.length);
            }

            String name = normalizeName(fields[0]);

            if (name.isEmpty()) {
                throw new IllegalArgumentException("name is blank");
            }

            String email = normalizeEmail(fields[1]);
            String phone = formatPhone(fields[2]);

            return new Contact(name, email, phone);
        }
}
