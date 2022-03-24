package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALICE_INTERVIEW_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BENSON_INTERVIEW_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARL_INTERVIEW_DATE_TIME;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.InterviewSchedule;
import seedu.address.model.interview.Interview;


/**
 * A utility class containing a list of {@code Candidate} objects to be used in tests.
 */
public class TypicalInterviews {
    public static final LocalDateTime TYPICAL_INTERVIEW_DATE_TIME = LocalDateTime.of(2022, Month.DECEMBER, 23, 10, 00);

    public static final Interview INTERVIEW_ALICE = new InterviewBuilder().withCandidate(TypicalCandidates.ALICE)
            .withInterviewDateTime(VALID_ALICE_INTERVIEW_DATE_TIME).build();
    public static final Interview INTERVIEW_BENSON = new InterviewBuilder().withCandidate(TypicalCandidates.BENSON)
            .withInterviewDateTime(VALID_BENSON_INTERVIEW_DATE_TIME).build();
    public static final Interview INTERVIEW_CARL = new InterviewBuilder().withCandidate(TypicalCandidates.CARL)
            .withInterviewDateTime(VALID_CARL_INTERVIEW_DATE_TIME).build();

    // Manually added - Candidate's details found in {@code CommandTestUtil}
    public static final Interview INTERVIEW_AMY_TYPICAL = new InterviewBuilder().withCandidate(TypicalCandidates.AMY)
            .withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME).build();
    public static final Interview INTERVIEW_BOB_TYPICAL = new InterviewBuilder().withCandidate(TypicalCandidates.BOB)
            .withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME).build();


    private TypicalInterviews() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical candidates.
     */
    public static InterviewSchedule getTypicalInterviewSchedule() {
        InterviewSchedule schedule = new InterviewSchedule();
        for (Interview interview : getTypicalInterviews()) {
            schedule.addInterview(interview);
        }
        return schedule;
    }

    public static List<Interview> getTypicalInterviews() {
        return new ArrayList<>(Arrays.asList(INTERVIEW_ALICE, INTERVIEW_BENSON, INTERVIEW_AMY_TYPICAL));
    }
}
