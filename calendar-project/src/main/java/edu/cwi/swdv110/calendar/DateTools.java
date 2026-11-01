package edu.cwi.swdv110.calendar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.Set;

public class DateTools {

    private static final DateTimeFormatter SHORT_FORMAT = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US);
    private static final DateTimeFormatter LONG_FORMAT = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US);

    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter SLASH_FORMAT = DateTimeFormatter.ofPattern("M/d/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT);
    
    public static LocalDate nthWeekday(int year, Month month, int n, DayOfWeek day) {
        return LocalDate.of(year, month, 1).with(TemporalAdjusters.dayOfWeekInMonth(n, day));
    }

    public static LocalDate lastWeekday(int year, Month month, DayOfWeek day) {
        return LocalDate.of(year, month, 1).with(TemporalAdjusters.lastInMonth(day));
    }

    public static String formatShort(LocalDate date) {
        return date.format(SHORT_FORMAT);
    }

    public static String formatLong(LocalDate date) {
        return date.format(LONG_FORMAT);
    }

    public static LocalDate parseFlexible(String input) {
        DateTimeFormatter[] formats = {
            ISO_FORMAT, SLASH_FORMAT, INPUT_FORMAT
        };

        for (DateTimeFormatter format : formats) {
            try {
                return LocalDate.parse(input.trim(), format);  
            } catch (DateTimeParseException e) {
            }
        }

        throw new IllegalArgumentException("Unrecognized date: " + input);
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static LocalDate addSchoolDays(LocalDate start, int days, Set<LocalDate> holidays) {
        LocalDate date = start;
        int count = 0;

        while (count < days) {
            date = date.plusDays(1);

            if (!isWeekend(date) && !holidays.contains(date)) {
                count++;
            }
        }

        return date;
    }

    public static int latePeneltyPercent(LocalDateTime due, LocalDateTime submitted) {
        if (!submitted.isAfter(due)) {
            return 0;
        }

        long minutesLate = (Duration.between(due, submitted).toSeconds() + 59) / 60;
        long daysLate = (minutesLate + 1439) / 1440;
        return (int) Math.min(daysLate * 10, 50);
    }

    public static String inZone(ZonedDateTime when, ZoneId zone) {
        ZonedDateTime converted = when.withZoneSameInstant(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d 'at' h:mm a z", Locale.US);
        return converted.format(formatter);
    }
}
