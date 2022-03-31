package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a Candidate's course in TAlent Assistant™.
 * Guarantees: immutable; is valid as declared in {@link #isValidCourse(String)}
 */
public class Course {
    public static final String MESSAGE_CONSTRAINTS =
            "Courses should only contain alphabets, and it should not be blank\n"
            + "Accepting only courses from this list:\n"
            + "1. Business Analytics\n2. Computer Engineering\n"
            + "3. Computer Science\n4. Information Security\n5. Information Systems";
    private static final String[] COURSES = {
        "Business Analytics", "Computer Engineering", "Computer Science",
        "Information Security", "Information Systems" };
    private static final int[] LENGTHS = { 18, 20, 16, 20, 19 };
    private static final int MAX_LENGTH = 20;

    public final String course;

    /**
     * Constructs a {@code StudentId}.
     *
     * @param course A valid course.
     */
    public Course(String course) {
        requireNonNull(course);
        checkArgument(isValidCourse(course), MESSAGE_CONSTRAINTS);

        String tempCourse = "";

        for (int i = 0; i < COURSES.length; i++) {
            if (COURSES[i].equalsIgnoreCase(course)) {
                tempCourse = COURSES[i];
                break;
            }
        }

        this.course = tempCourse;
    }

    /**
     * Returns true if a given string is a valid course.
     */
    public static boolean isValidCourse(String test) {
        if (test.length() > MAX_LENGTH) {
            return false;
        }

        for (int index = 0; index < COURSES.length; ++index) {
            if (test.equalsIgnoreCase(COURSES[index]) && test.length() == LENGTHS[index]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return course;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Course // instanceof handles nulls
                && course.equals(((Course) other).course)); // state check
    }

    @Override
    public int hashCode() {
        return course.hashCode();
    }
}
