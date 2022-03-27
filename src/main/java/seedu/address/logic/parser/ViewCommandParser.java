package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.predicate.AllWithinTimePeriodPredicate;
import seedu.address.model.interview.predicate.ThisMonthWithinTimePeriodPredicate;
import seedu.address.model.interview.predicate.ThisWeekWithinTimePeriodPredicate;
import seedu.address.model.interview.predicate.TodayWithinTimePeriodPredicate;
import seedu.address.model.interview.predicate.UpcomingWithinTimePeriodPredicate;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    private final Logger logger = LogsCenter.getLogger(ViewCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        LocalDateTime now = LocalDateTime.now();
        try {
            ParserUtil.parseTimePeriod(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }

        String timePeriod = ParserUtil.parseTimePeriod(args);

        switch (timePeriod) {
        case "today":
            logger.info("Time period parsed - today");
            return new ViewCommand(new TodayWithinTimePeriodPredicate(now));
        case "week":
            logger.info("Time period parsed - week");
            return new ViewCommand(new ThisWeekWithinTimePeriodPredicate(now));
        case "month":
            logger.info("Time period parsed - month");
            return new ViewCommand(new ThisMonthWithinTimePeriodPredicate(now));
        case "month":
            logger.info("Time period parsed - upcoming");
            return new ViewCommand(new UpcomingWithinTimePeriodPredicate(now));
        case "all":
        case "":
        default:
            logger.info("Time period parsed - all time");
            return new ViewCommand(new AllWithinTimePeriodPredicate(now));
        }
    }

}
