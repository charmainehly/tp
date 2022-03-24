package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_ALICE;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_AMY_TYPICAL;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_BOB_TYPICAL;
import static seedu.address.testutil.TypicalInterviews.TYPICAL_INTERVIEW_DATE_TIME;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;
import static seedu.address.testutil.TypicalCandidates.BOB;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.ConflictingInterviewException;
import seedu.address.model.interview.exceptions.DuplicateCandidateException;
import seedu.address.testutil.InterviewBuilder;

public class InterviewScheduleTest {

    private final InterviewSchedule interviewSchedule = new InterviewSchedule();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), interviewSchedule.getInterviewList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> interviewSchedule.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInterviewSchedule_replacesData() {
        InterviewSchedule newData = getTypicalInterviewSchedule();
        interviewSchedule.resetData(newData);
        assertEquals(newData, interviewSchedule);
    }

    @Test
    public void resetData_withDuplicateInterviewCandidates_throwsDuplicateCandidateException() {
        // Two interviews with the same candidate
        Interview editedAliceInterview =
                new InterviewBuilder(INTERVIEW_ALICE).withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME)
                .build();
        List<Interview> newInterviews = Arrays.asList(INTERVIEW_ALICE, editedAliceInterview);
        InterviewScheduleStub newData = new InterviewScheduleStub(newInterviews);

        assertThrows(DuplicateCandidateException.class, () -> interviewSchedule.resetData(newData));
    }

    @Test
    public void resetData_withConflictingInterviews_throwsConflictingInterviewException() {
        // Two interviews with the same date and time
        Interview editedAliceInterview =
                new InterviewBuilder(INTERVIEW_ALICE).withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME)
                        .build();
        List<Interview> newInterviews = Arrays.asList(INTERVIEW_AMY_TYPICAL, editedAliceInterview);
        InterviewScheduleStub newData = new InterviewScheduleStub(newInterviews);

        assertThrows(ConflictingInterviewException.class, () -> interviewSchedule.resetData(newData));
    }


    @Test
    public void hasCandidate_nullInterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> interviewSchedule.hasCandidate(null));
    }

    @Test
    public void hasConflictingInterview_nullInterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> interviewSchedule.hasConflictingInterview(null));
    }

    @Test
    public void hasCandidate_interviewNotInInterviewSchedule_returnsFalse() {
        assertFalse(interviewSchedule.hasCandidate(INTERVIEW_ALICE));
    }

    @Test
    public void hasConflictingInterview_interviewNotInInterviewSchedule_returnsFalse() {
        assertFalse(interviewSchedule.hasConflictingInterview(INTERVIEW_ALICE));
    }

    @Test
    public void hasCandidate_interviewInInterviewSchedule_returnsTrue() {
        interviewSchedule.addInterview(INTERVIEW_ALICE);
        assertTrue(interviewSchedule.hasCandidate(INTERVIEW_ALICE));
    }

    @Test
    public void hasConflictingInterview_interviewInInterviewSchedule_returnsTrue() {
        interviewSchedule.addInterview(INTERVIEW_AMY_TYPICAL);
        assertTrue(interviewSchedule.hasConflictingInterview(INTERVIEW_BOB_TYPICAL));
    }

    @Test
    public void hasCandidate_interviewWithSameCandidateInInterviewSchedule_returnsTrue() {
        interviewSchedule.addInterview(INTERVIEW_ALICE);
        Interview editedAliceInterview = new InterviewBuilder(INTERVIEW_ALICE)
                .withInterviewDateTime(TYPICAL_INTERVIEW_DATE_TIME).build();
        assertTrue(interviewSchedule.hasCandidate(editedAliceInterview));
    }

    @Test
    public void hasConflictingInterview_interviewWithSameInterviewDateTimeInInterviewSchedule_returnsTrue() {
        interviewSchedule.addInterview(INTERVIEW_ALICE);
        Interview editedAliceInterview = new InterviewBuilder(INTERVIEW_ALICE)
                .withCandidate(BOB).build();
        assertTrue(interviewSchedule.hasConflictingInterview(editedAliceInterview));
    }

    /**
     * A stub ReadOnlyInterviewSchedule whose candidates list can violate interface constraints.
     */
    private static class InterviewScheduleStub implements ReadOnlyInterviewSchedule {
        private final ObservableList<Interview> interviews = FXCollections.observableArrayList();

        InterviewScheduleStub(Collection<Interview> interviews) {
            this.interviews.setAll(interviews);
        }

        @Override
        public ObservableList<Interview> getInterviewList() {
            return interviews;
        }
    }

}
