package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.candidate.predicate.CandidateContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.CourseContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.candidate.predicate.StudentIdContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_KEYWORD, PREFIX_FIELD);

        // throws exception if no keywords are specified
        if (!arePrefixesPresent(argMultimap, PREFIX_KEYWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> keywords = argMultimap.getAllValues(PREFIX_KEYWORD);
        Optional<String> field = argMultimap.getValue(PREFIX_FIELD);
        String fieldString;

        if (field.isEmpty()) {
            fieldString = "";
        } else {
            fieldString = field.get().toLowerCase();
        }

        switch (fieldString) {
        case "email":
            return new FindCommand(new EmailContainsKeywordsPredicate(keywords));
        case "course":
            return new FindCommand(new CourseContainsKeywordsPredicate(keywords));
        case "name":
            return new FindCommand(new NameContainsKeywordsPredicate(keywords));
        case "candidate":
        case "":
            return new FindCommand(new CandidateContainsKeywordsPredicate(keywords));
        case "phone":
            return new FindCommand(new PhoneContainsKeywordsPredicate(keywords));
        case "studentid":
            return new FindCommand(new StudentIdContainsKeywordsPredicate(keywords));
        default:
            return new FindCommand(new CandidateContainsKeywordsPredicate(Collections.<String>emptyList()));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
