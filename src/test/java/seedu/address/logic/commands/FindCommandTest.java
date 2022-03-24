package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CANDIDATES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalInterviews.getTypicalInterviewSchedule;
import static seedu.address.testutil.TypicalCandidates.CARL;
import static seedu.address.testutil.TypicalCandidates.ELLE;
import static seedu.address.testutil.TypicalCandidates.FIONA;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.candidate.predicate.CandidateContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalInterviewSchedule(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(),
            getTypicalInterviewSchedule(), new UserPrefs());

    @Test
    public void equals() {
        CandidateContainsKeywordsPredicate firstPredicate =
                new CandidateContainsKeywordsPredicate(Collections.singletonList("first"));
        CandidateContainsKeywordsPredicate secondPredicate =
                new CandidateContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different candidate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCandidateFound() {
        String expectedMessage = String.format(MESSAGE_CANDIDATES_LISTED_OVERVIEW, 0);
        CandidateContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCandidateList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCandidateList());
    }

    @Test
    public void execute_multipleKeywords_multipleCandidatesFound() {
        String expectedMessage = String.format(MESSAGE_CANDIDATES_LISTED_OVERVIEW, 3);
        CandidateContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredCandidateList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCandidateList());
    }

    /**
     * Parses {@code userInput} into a {@code CandidateContainsKeywordsPredicate}.
     */
    private CandidateContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CandidateContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
