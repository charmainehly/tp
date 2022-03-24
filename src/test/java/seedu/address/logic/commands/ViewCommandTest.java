package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_NO_INTERVIEWS_IN_SYSTEM;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.InterviewSchedule;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.Interview;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());
    }

    @Test
    public void execute_view_success() {
        String expectedMessage = MESSAGE_SUCCESS + model.getInterviewSchedule().getInterviewList()
                .stream().sorted(Comparator.comparing(Interview::getInterviewDateTime))
                .map(Object::toString).collect(Collectors.joining("\n"));
        assertCommandSuccess(new ViewCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsEmpty_showsEmptyMessage() {
        model = new ModelManager(new AddressBook(), new InterviewSchedule(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getInterviewSchedule(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_NO_INTERVIEWS_IN_SYSTEM);
        assertCommandSuccess(new ViewCommand(), model, expectedCommandResult, expectedModel);
    }
}
