package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.interview.Interview;

/**
 * Lists all candidates in the address book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_NO_INTERVIEWS_IN_SYSTEM = "No interviews scheduled yet!";

    public static final String MESSAGE_SUCCESS = "Listed below are your upcoming interviews: \n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String message = model.getInterviewSchedule().getInterviewList().size() > 0
                ? MESSAGE_SUCCESS
                + model.getInterviewSchedule().getInterviewList()
                .stream()
                .sorted(Comparator.comparing(Interview::getInterviewDateTime))
                .map(Object::toString)
                .collect(Collectors.joining("\n"))
                : MESSAGE_NO_INTERVIEWS_IN_SYSTEM;
        return new CommandResult(message);
    }
}
