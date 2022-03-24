package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABILITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AVAILABILITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AVAILABILITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCandidates.AMY;
import static seedu.address.testutil.TypicalCandidates.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CandidateBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Candidate expectedCandidate = new CandidateBuilder(BOB).withTags(VALID_TAG_FRIEND)
                .withApplicationStatus(VALID_APPLICATION_PENDING)
                .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB, new AddCommand(expectedCandidate));

        // multiple names - last name accepted
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB, new AddCommand(expectedCandidate));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB, new AddCommand(expectedCandidate));

        // multiple tags - all accepted
        Candidate expectedCandidateMultipleTags = new CandidateBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB,
                new AddCommand(expectedCandidateMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Candidate expectedCandidate = new CandidateBuilder(AMY).withTags().build();
        assertParseSuccess(parser, STUDENT_ID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                + COURSE_DESC_AMY + AVAILABILITY_DESC_AMY, new AddCommand(expectedCandidate));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB
                + COURSE_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_COURSE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_COURSE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENT_ID_DESC + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB,
                StudentId.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC
                + COURSE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND + AVAILABILITY_DESC_BOB,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid availability
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + INVALID_AVAILABILITY_DESC,
                Availability.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, STUDENT_ID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                + INVALID_COURSE_DESC + AVAILABILITY_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENT_ID_DESC_BOB + NAME_DESC_BOB
                + COURSE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + AVAILABILITY_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
