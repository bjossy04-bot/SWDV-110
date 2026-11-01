package edu.cwi.swdv110.calendar;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class SemesterTest {
    @Test 
    void testClassDates() {
        List<LocalDate> dates = Semester.getClassDates();
        assertNotNull(dates);
        assertFalse(dates.isEmpty());
        assertEquals(LocalDate.of(2027, 1, 11), dates.get(0));
    }
}
