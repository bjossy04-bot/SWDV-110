package edu.cwi.swdv110.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class DateToolTest {
    @Test
    public void testNthWeekday() {
        LocalDate result = DateTools.nthWeekday(2026, Month.NOVEMBER, 4, DayOfWeek.THURSDAY);
        assertEquals(LocalDate.of(2026, 11, 26), result);
    }

    @Test
    public void testLastWeekday() {
        LocalDate result = DateTools.lastWeekday(2026, Month.MAY, DayOfWeek.MONDAY);
        assertEquals(LocalDate.of(2026, 5, 25), result);
    }

    @Test
    public void testFormatShort() {
        LocalDate date = LocalDate.of(2026, 11, 26);
        String result = DateTools.formatShort(date);
        assertEquals("Thu, Nov 26", result);
    }

    @Test 
    public void testFormatLong() {
        LocalDate date = LocalDate.of(2026, 11, 26);
        String result = DateTools.formatLong(date);
        assertEquals("Thursday, November 26, 2026", result);
    }

    @Test
    public void testParseFlexibleISO() {
        LocalDate result = DateTools.parseFlexible("2026-11-26");
        assertEquals(LocalDate.of(2026, 11, 26), result);
    }

    @Test
    public void testParseFlexibleSlash() {
        LocalDate result = DateTools.parseFlexible("11/26/2026");
        assertEquals(LocalDate.of(2026, 11, 26), result);
    }

    @Test 
    public void testParseFlexibleWritten() {
        LocalDate result = DateTools.parseFlexible("Nov 26, 2026");
        assertEquals(LocalDate.of(2026, 11, 26), result);
    }

    @Test 
    public void testPareFlexibleInvalid() {
        assertThrows(IllegalArgumentException.class, () -> { DateTools.parseFlexible("not a date");
        });
    }

    @Test 
    public void testParseFlexibleWhitespace() {
        LocalDate result = DateTools.parseFlexible("  2026-11-26");
        assertEquals(LocalDate.of(2026, 11, 26), result);
    }
}

