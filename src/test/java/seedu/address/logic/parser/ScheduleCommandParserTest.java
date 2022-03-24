package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CANDIDATE;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ScheduleCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ScheduleCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ScheduleCommandParserTest {

    private ScheduleCommandParser parser = new ScheduleCommandParser();
    private LocalDateTime interviewDateTime = LocalDateTime.of(2023, 03, 20, 10, 00);

    @Test
    public void parse_validArgs_returnsScheduleCommand() {
        assertParseSuccess(parser, "1 /at 20/03/2023 10:00", new ScheduleCommand(INDEX_FIRST_CANDIDATE,
                interviewDateTime));
    }

    @Test
    public void parse_invalidDelimiter_throwsParseException() {
        assertParseFailure(parser, "1 /by 20/03/2023 10:00", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }
}
