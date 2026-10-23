# Contact Cleaner

This Java program reads contact information from a text file,
cleans and formats the information, and creates a contact report.

The program also checks for invalid emails and phone numbers
and allows users to search for contacts by name.

## How to Run

Run the tests:

mvn test

Run the program:

mvn -q compile exec:java

## Reflection Questions

### 1. Strings are immutable. Name a place in your code where forgetting that would have caused a bug.

In my normalizeEmail method, I used String methods to remove
extra spaces and change letters to lowercase. These methods
return new Strings rather than changing the original String.
If I forgot to use the returned value, the email would not
be cleaned correctly.

### 2. Why does split("|") behave differently from split("\\|")?

The split method uses regular expressions. The pipe symbol
has a special meaning in regular expressions, so split("|")
does not split on the actual pipe character as expected.
Using split("\\|") tells Java to split at literal pipe symbols.

### 3. Where did you choose StringBuilder instead of +, and why?

I used StringBuilder in ContactReport.java to build the
contact report. StringBuilder allows me to append information
to the report without creating a new String each time.
This is useful when adding multiple contact rows.

### 4. Paste the output of mvn test (the summary line is enough).

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS