package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedCandidate.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCandidates.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.InterviewStatus;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.StudentId;

public class JsonAdaptedCandidateTest {
    private static final String INVALID_STUDENT_ID = "A0123456";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_COURSE = "computrr Science";
    private static final String INVALID_SENIORITY = "0.0";
    private static final String INVALID_AVAILABILITY = "9";

    private static final String VALID_STUDENT_ID = BENSON.getStudentId().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_COURSE = BENSON.getCourse().toString();
    private static final String VALID_SENIORITY = BENSON.getSeniority().toString();
    private static final String VALID_APPLICATION_STATUS = new ApplicationStatus("Pending").toString();
    private static final String VALID_INTERVIEW_STATUS = new InterviewStatus("Pending").toString();
    private static final String VALID_AVAILABILITY = BENSON.getAvailability().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();


    @Test
    public void toModelType_validCandidateDetails_returnsCandidate() throws Exception {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(BENSON);
        assertEquals(BENSON, candidate.toModelType());
    }

    @Test
    public void toModelType_invalidStudentId_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(
                INVALID_STUDENT_ID, VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COURSE, VALID_SENIORITY,
                VALID_APPLICATION_STATUS, VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = StudentId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_nullStudentId_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(null, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate =
                new JsonAdaptedCandidate(VALID_STUDENT_ID, INVALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                        VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, null, VALID_PHONE,
                VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate =
                new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, INVALID_PHONE,
                        VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                        VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, null,
                VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate =
                new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                        INVALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                        VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                null, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS, VALID_INTERVIEW_STATUS,
                VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_invalidCourse_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, INVALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        assertThrows(IllegalValueException.class, candidate::toModelType);
    }

    @Test
    public void toModelType_nullCourse_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, null, VALID_SENIORITY, VALID_APPLICATION_STATUS, VALID_INTERVIEW_STATUS,
                VALID_AVAILABILITY, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Course.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }

    @Test
    public void toModelType_invalidSeniority_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_COURSE, INVALID_SENIORITY, VALID_APPLICATION_STATUS,
                VALID_INTERVIEW_STATUS, VALID_AVAILABILITY, VALID_REMARK);
        assertThrows(IllegalValueException.class, candidate::toModelType);
    }

    @Test
    public void toModelType_invalidAvailability_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate =
                new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                        VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                        VALID_INTERVIEW_STATUS, INVALID_AVAILABILITY, VALID_REMARK);
        assertThrows(IllegalValueException.class, candidate::toModelType);
    }

    @Test
    public void toModelType_nullAvailability_throwsIllegalValueException() {
        JsonAdaptedCandidate candidate = new JsonAdaptedCandidate(VALID_STUDENT_ID, VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_COURSE, VALID_SENIORITY, VALID_APPLICATION_STATUS,
                VALID_INTERVIEW_STATUS, null, VALID_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Availability.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, candidate::toModelType);
    }
}
