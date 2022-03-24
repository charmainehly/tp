package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CANDIDATES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_ALICE;
import static seedu.address.testutil.TypicalInterviews.INTERVIEW_BENSON;
import static seedu.address.testutil.TypicalCandidates.ALICE;
import static seedu.address.testutil.TypicalCandidates.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.candidate.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.InterviewScheduleBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new InterviewSchedule(), new InterviewSchedule(modelManager.getInterviewSchedule()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasCandidate_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCandidate(null));
    }

    @Test
    public void hasCandidate_candidateNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCandidate(ALICE));
    }

    @Test
    public void hasCandidate_candidateInAddressBook_returnsTrue() {
        modelManager.addCandidate(ALICE);
        assertTrue(modelManager.hasCandidate(ALICE));
    }

    @Test
    public void getFilteredCandidateList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCandidateList().remove(0));
    }

    //=======TODO======
    @Test
    public void setInterviewScheduleFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInterviewScheduleFilePath(null));
    }

    @Test
    public void setInterviewScheduleFilePath_validPath_setsInterviewScheduleFilePath() {
        Path path = Paths.get("interview/schedule/file/path");
        modelManager.setInterviewScheduleFilePath(path);
        assertEquals(path, modelManager.getInterviewScheduleFilePath());
    }

    @Test
    public void hasInterviewCandidate_nullinterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasInterviewCandidate(null));
    }

    @Test
    public void hasConflictingInterview_nullinterview_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasConflictingInterview(null));
    }

    @Test
    public void hasInterviewCandidate_candidateNotInInterviewSchedule_returnsFalse() {
        assertFalse(modelManager.hasInterviewCandidate(INTERVIEW_ALICE));
    }

    @Test
    public void hasConflictingInterview_interviewNotInInterviewSchedule_returnsFalse() {
        assertFalse(modelManager.hasConflictingInterview(INTERVIEW_ALICE));
    }

    @Test
    public void hasInterviewCandidate_interviewInInterviewSchedule_returnsTrue() {
        modelManager.addInterview(INTERVIEW_ALICE);
        assertTrue(modelManager.hasInterviewCandidate(INTERVIEW_ALICE));
    }

    @Test
    public void hasConflictingInterview_interviewInInterviewSchedule_returnsTrue() {
        modelManager.addInterview(INTERVIEW_ALICE);
        assertTrue(modelManager.hasConflictingInterview(INTERVIEW_ALICE));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCandidate(ALICE).withCandidate(BENSON).build();
        InterviewSchedule interviewSchedule = new InterviewScheduleBuilder().withInterview(INTERVIEW_ALICE)
                .withInterview(INTERVIEW_BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        InterviewSchedule differentInterviewSchedule = new InterviewSchedule();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, interviewSchedule, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, interviewSchedule, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook,
                differentInterviewSchedule, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCandidateList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, interviewSchedule, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, interviewSchedule, differentUserPrefs)));
    }
}
