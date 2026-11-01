package edu.cwi.swdv110.calendar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Semester {
    private static final LocalDate START_DATE = LocalDate.of(2027, 1, 11);
    private static final LocalDate END_DATE = LocalDate.of(2027, 5, 7);
    private static final LocalTime CLASS_START = LocalTime.of(18, 0);
    private static final Duration CLASS_DURATION = Duration.ofMinutes(150);
    private static final LocalDate MLK_DAY = LocalDate.of(2027, 1, 18);
    private static final LocalDate PRESIDENTS_DAY = LocalDate.of(2027, 2, 15);
    private static final LocalDate SPRING_BREAK_START = LocalDate.of(2027, 3, 22);
    private static final LocalDate SPRING_BREAK_END = LocalDate.of(2027, 3, 26);

    public static List<LocalDate>getClassDates() {
        List<LocalDate> classDates = new ArrayList<>();

        for (LocalDate date = START_DATE; !date.isAfter(END_DATE); date = date.plusDays(1)) {

            DayOfWeek day = date.getDayOfWeek();

            boolean isClassDay = day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY;
            boolean isMLKDay = date.equals(MLK_DAY);
            boolean isPresidentsDay = date.equals(PRESIDENTS_DAY);
            boolean isSpringBreak = !date.isBefore(SPRING_BREAK_START) && !date.isAfter(SPRING_BREAK_END);

            if (isClassDay && !isMLKDay && !isPresidentsDay && !isSpringBreak) {
                classDates.add(date);
            }
        }
        
        return classDates;
    }
}
