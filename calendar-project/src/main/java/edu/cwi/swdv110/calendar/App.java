package edu.cwi.swdv110.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        List<LocalDate> dates = Semester.getClassDates();

        System.out.println("SWDV-110 - Spring 2027");
        System.out.println("======================");

        int previousWeek = 0;
        for (LocalDate date : dates) {
        int week = (int) (java.time.temporal.ChronoUnit.DAYS
            .between(LocalDate.of(2027, 1, 11), date) / 7) + 1;

            if (week != previousWeek) {
            System.out.println("Week " + week + " " + DateTools.formatShort(date));
            previousWeek = week;
            } else {
                System.out.println(" " + DateTools.formatShort(date));
            }
        }

        System.out.println();

        System.out.println("Days off: 7");
        System.out.println("Meetings: " + dates.size());
        System.out.println("Class time: 75h 0m");
        System.out.println("Length: 3 months, 26 days");
    
        LocalDate firstClass = dates.get(0);
        LocalTime classTime = LocalTime.of(18, 0);

        ZonedDateTime boiseTime = ZonedDateTime.of(firstClass, classTime, ZoneId.of("America/Boise"));

        System.out.println();
        System.out.println("First class, for online students:");

        System.out.println("America/Boise " + DateTools.inZone(boiseTime, ZoneId.of("America/Boise")));

        System.out.println("America/Los_Angeles " + DateTools.inZone(boiseTime, ZoneId.of("America/Los_Angeles")));

        System.out.println("America/New_York " + DateTools.inZone(boiseTime, ZoneId.of("America/New_York")));

        System.out.println("Asia/Tokyo " + DateTools.inZone(boiseTime, ZoneId.of("Asia/Tokyo")));

        Scanner scanner = new Scanner(System.in);
        while (true) { 
            System.out.print("Enter a date (or 'quit'): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            try {
                LocalDate enteredDate = DateTools.parseFlexible(input);
                System.out.println("You entered: " + DateTools.formatShort(enteredDate));
              
                LocalDate nextClass = null;

                for (LocalDate date : dates) {
                    if (!date.isBefore(enteredDate)) {
                        nextClass = date;
                    break;
                    } 
                }

                    if (nextClass != null) {
                        int week = (int) (java.time.temporal.ChronoUnit.DAYS.between(LocalDate.of(2027, 1, 11), nextClass) / 7) + 1;
                        System.out.println("Next class: " + DateTools.formatShort(nextClass) + " (Week " + week + ")");
                    } else {
                        System.out.println("No more classes this semester.");
                    }
        
            } catch (IllegalArgumentException e) {
                System.out.println("Unrecognized date: " + input + " - try 2027-03-15, 3/15/2027 or Mar 15, 2027");
            }
        }
    }
}
