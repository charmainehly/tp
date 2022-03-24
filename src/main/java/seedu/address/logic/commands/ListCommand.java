package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CANDIDATES;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;

/**
 * Lists all candidates in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all candidates in the system!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);
        String message = model.getAddressBook().getCandidateList().size() > 0
                ? MESSAGE_SUCCESS
                : Messages.MESSAGE_NO_CANDIDATES_IN_SYSTEM;
        return new CommandResult(message);
    }
}
