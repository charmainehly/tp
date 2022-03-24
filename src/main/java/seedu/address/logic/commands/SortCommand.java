package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTKEY;

import java.util.Comparator;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.candidate.Candidate;

/**
 * Reorders all candidates in TAlent Assistant™ based on the sort key provided.
 * The sort key will point to a specific candidate field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all candidates by the field specified "
            + "and displays them as a list with index numbers.\n"
            + " The search can be conducted only on a specific field in candidates' description by specifying the"
            + PREFIX_SORTKEY + " SORTKEY argument.\n"
            + "Parameters: " + PREFIX_SORTKEY + "SORTKEY \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SORTKEY + "name";

    private final Comparator<Candidate> sortComparator;
    private final String sortKey;

    /**
     * Creates new SortCommand object.
     *
     * @param sortComparator contains the Comparator for sorting the list of candidates
     * @param sortKey contains the valid sort key for equality state check
     */
    public SortCommand(Comparator<Candidate> sortComparator, String sortKey) {
        requireNonNull(sortComparator);
        requireNonNull(sortKey);

        this.sortComparator = sortComparator;
        this.sortKey = sortKey;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateSortedCandidateList(sortComparator);
        return new CommandResult(
                String.format(Messages.MESSAGE_CANDIDATES_LISTED_OVERVIEW, model.getFilteredCandidateList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand
                && sortKey.equals(((SortCommand) other).sortKey)); // state check
    }
}
