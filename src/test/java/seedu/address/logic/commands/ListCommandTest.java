package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCandidateAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.InterviewSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getInterviewSchedule(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCandidateAtIndex(model, INDEX_FIRST_CANDIDATE);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsEmpty_showsEmptyMessage() {
        model = new ModelManager(new AddressBook(), new InterviewSchedule(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getInterviewSchedule(), new UserPrefs());
        assertCommandSuccess(new ListCommand(), model, Messages.MESSAGE_NO_CANDIDATES_IN_SYSTEM, expectedModel);
    }
}
