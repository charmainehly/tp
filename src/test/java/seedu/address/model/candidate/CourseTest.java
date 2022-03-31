package seedu.address.model.candidate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CourseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Course(null));
    }

    @Test
    public void constructor_invalidCourse_throwsIllegalArgumentException() {
        String invalidCourse = "";
        assertThrows(IllegalArgumentException.class, () -> new Course(invalidCourse));
    }

    @Test
    public void isValidCourse() {
        // Null course
        assertThrows(NullPointerException.class, () -> Course.isValidCourse(null));

        // Invalid course
        assertTrue(Course.isValidCourse("computer science"));
        assertTrue(Course.isValidCourse("computer Science"));
        assertFalse(Course.isValidCourse("computer science."));
        assertFalse(Course.isValidCourse("computer science "));
        assertFalse(Course.isValidCourse(" computer science"));
        assertFalse(Course.isValidCourse(" computer science "));
        assertFalse(Course.isValidCourse("com sci"));
        assertFalse(Course.isValidCourse("Computer Science Computer Science"));
        assertFalse(Course.isValidCourse("Computer Science "));
        assertFalse(Course.isValidCourse(" Computer Science"));
        assertFalse(Course.isValidCourse(" Computer Science "));
        assertFalse(Course.isValidCourse("bza"));
        assertFalse(Course.isValidCourse("BZA"));
        assertFalse(Course.isValidCourse("cs"));
        assertFalse(Course.isValidCourse("cs"));
        assertFalse(Course.isValidCourse("CS"));
        assertFalse(Course.isValidCourse("ceg"));
        assertFalse(Course.isValidCourse("CEG"));
        assertFalse(Course.isValidCourse("is"));
        assertFalse(Course.isValidCourse("IS"));

        // Only computing courses
        assertFalse(Course.isValidCourse("Business Administration"));
        assertFalse(Course.isValidCourse("Accountancy"));

        // Valid course
        assertTrue(Course.isValidCourse("Business Analytics"));
        assertTrue(Course.isValidCourse("Computer Engineering"));
        assertTrue(Course.isValidCourse("Computer Science"));
        assertTrue(Course.isValidCourse("Information Security"));
        assertTrue(Course.isValidCourse("Information Systems"));
    }
}
