package seedu.address.model.candidate;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Candidate in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Candidate {

    // Identity fields
    private final StudentId studentId;
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Course course;
    private final ApplicationStatus applicationStatus;
    private final InterviewStatus interviewStatus;
    private final Availability availability;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Candidate(StudentId studentId, Name name, Phone phone, Email email, Course course, Set<Tag> tags,
            ApplicationStatus applicationStatus, InterviewStatus interviewStatus, Availability availability) {
        requireAllNonNull(studentId, name, phone, email, course, tags, availability);
        this.studentId = studentId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.course = course;
        this.tags.addAll(tags);
        this.applicationStatus = applicationStatus;
        this.interviewStatus = interviewStatus;
        this.availability = availability;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Course getCourse() {
        return course;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public InterviewStatus getInterviewStatus() {
        return interviewStatus;
    }

    public Availability getAvailability() {
        return availability;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both candidates have the same name.
     * This defines a weaker notion of equality between two candidates.
     */
    public boolean isSameCandidate(Candidate otherCandidate) {
        if (otherCandidate == this) {
            return true;
        }

        return otherCandidate != null
                && otherCandidate.getStudentId().equals(getStudentId());
    }

    /**
     * Returns true if both candidates have the same identity and data fields.
     * This defines a stronger notion of equality between two candidates.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Candidate)) {
            return false;
        }

        Candidate otherCandidate = (Candidate) other;
        return otherCandidate.getStudentId().equals(getStudentId())
                && otherCandidate.getName().equals(getName())
                && otherCandidate.getPhone().equals(getPhone())
                && otherCandidate.getEmail().equals(getEmail())
                && otherCandidate.getCourse().equals(getCourse())
                && otherCandidate.getTags().equals(getTags())
                && otherCandidate.getApplicationStatus().equals(getApplicationStatus())
                && otherCandidate.getInterviewStatus().equals(getInterviewStatus())
                && otherCandidate.getAvailability().equals(getAvailability());
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, phone, email, course, tags, applicationStatus, interviewStatus,
                availability);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student ID: ").append(getStudentId())
                .append("; Name: ")
                .append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Course: ")
                .append(getCourse());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        builder.append("; Application Status: ")
                .append(getApplicationStatus())
                .append("; Interview Status: ")
                .append(getInterviewStatus())
                .append("; Availability: ")
                .append(getAvailability());
        return builder.toString();
    }

}
