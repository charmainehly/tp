package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.candidate.Candidate;
import seedu.address.model.interview.Interview;

/**
 * A utility class to help with building Candidate objects.
 */
public class InterviewBuilder {

    private Candidate candidate;
    private LocalDateTime interviewDateTime;

    /**
     * Creates a {@code CandidateBuilder} with the default details.
     */
    public InterviewBuilder() {
        candidate = new CandidateBuilder().build();
        interviewDateTime = LocalDateTime.now();
    }

    /**
     * Initializes the CandidateBuilder with the data of {@code interviewToCopy}.
     */
    public InterviewBuilder(Interview interviewToCopy) {
        candidate = interviewToCopy.getCandidate();
        interviewDateTime = interviewToCopy.getInterviewDateTime();
    }

    /**
     * Sets the {@code StudentID} of the {@code Candidate} that we are building.
     */
    public InterviewBuilder withCandidate(Candidate c) {
        this.candidate = c;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Candidate} that we are building.
     */
    public InterviewBuilder withInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
        return this;
    }

    public Interview build() {
        return new Interview(candidate, interviewDateTime);
    }

}
