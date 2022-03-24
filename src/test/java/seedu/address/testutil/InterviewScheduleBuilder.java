package seedu.address.testutil;

import seedu.address.model.InterviewSchedule;
import seedu.address.model.interview.Interview;

/**
 * A utility class to help with building InterviewSchedule objects.
 * Example usage: <br>
 *     {@code InterviewSchedule ab = new InterviewScheduleBuilder().withCandidate("John", "Doe").build();}
 */
public class InterviewScheduleBuilder {

    private InterviewSchedule interviewSchedule;

    public InterviewScheduleBuilder() {
        interviewSchedule = new InterviewSchedule();
    }

    public InterviewScheduleBuilder(InterviewSchedule interviewSchedule) {
        this.interviewSchedule = interviewSchedule;
    }

    /**
     * Adds a new {@code Candidate} to the {@code InterviewSchedule} that we are building.
     */
    public InterviewScheduleBuilder withInterview(Interview interview) {
        interviewSchedule.addInterview(interview);
        return this;
    }

    public InterviewSchedule build() {
        return interviewSchedule;
    }
}
