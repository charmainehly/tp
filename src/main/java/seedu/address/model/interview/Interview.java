package seedu.address.model.interview;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.model.candidate.Candidate;

/**
 * Represents an Interview in the interview schedule.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Interview {
    private static final int INTERVIEW_DURATION_IN_MINUTES = 30;

    private final Candidate candidate;
    private final LocalDateTime interviewDateTime;
    private final LocalDateTime interviewEndDateTime;

    /**
     * Every field must be present and not null.
     */
    public Interview(Candidate candidate, LocalDateTime interviewDateTime) {
        requireAllNonNull(candidate, interviewDateTime);
        this.candidate = candidate;
        this.interviewDateTime = interviewDateTime;
        this.interviewEndDateTime = interviewDateTime.plusMinutes(INTERVIEW_DURATION_IN_MINUTES);
    }

    /**
     * Returns true if both interviews have the same candidates.
     */
    public boolean isSameInterviewCandidate(Interview otherInterview) {
        if (otherInterview == this) {
            return true;
        }
        return otherInterview != null
                && otherInterview.getCandidate().equals(getCandidate());
    }

    /**
     * Returns true if both interviews have the same interview date and time.
     */
    public boolean isConflictingInterview(Interview otherInterview) {
        if (otherInterview == this) {
            return true;
        }
        return otherInterview != null
                && (!otherInterview.getInterviewDateTime().isAfter(getInterviewDateTime())
                && otherInterview.getInterviewEndDateTime().isAfter(getInterviewDateTime()))
                || (otherInterview.getInterviewDateTime().isBefore(getInterviewEndDateTime())
                && otherInterview.getInterviewEndDateTime().isAfter(getInterviewEndDateTime()));
    }

    /**
     * Returns true if the given date and time is not in the past.
     */
    public static boolean isValidDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.now().isBefore(localDateTime);
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public LocalDateTime getInterviewDateTime() {
        return this.interviewDateTime;
    }

    public LocalTime getInterviewStartTime() {
        return this.interviewDateTime.toLocalTime();
    }
    public LocalDateTime getInterviewEndDateTime() {
        return this.interviewEndDateTime;
    }

    public LocalDate getInterviewDate() {
        return this.interviewDateTime.toLocalDate();
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

        if (!(other instanceof Interview)) {
            return false;
        }

        Interview otherInterview = (Interview) other;
        return otherInterview.getCandidate().equals(getCandidate())
                && otherInterview.getInterviewDateTime().equals(getInterviewDateTime());
    }

    @Override
    public String toString() {
        return this.candidate.getName() + " " + this.candidate.getStudentId() + " "
                + this.getInterviewDate() + " " + this.getInterviewStartTime();
    }
}
