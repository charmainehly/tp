package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPLICATION_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVAILABILITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CANDIDATES;
import static seedu.address.model.candidate.ApplicationStatus.ACCEPTED_STATUS;
import static seedu.address.model.candidate.ApplicationStatus.REJECTED_STATUS;
import static seedu.address.model.candidate.InterviewStatus.COMPLETED;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.candidate.ApplicationStatus;
import seedu.address.model.candidate.Availability;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.Course;
import seedu.address.model.candidate.Email;
import seedu.address.model.candidate.InterviewStatus;
import seedu.address.model.candidate.Name;
import seedu.address.model.candidate.Phone;
import seedu.address.model.candidate.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing candidate in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the candidate identified "
            + "by the index number used in the displayed candidate list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ID + "ID] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_COURSE + "COURSE] "
            + "[" + PREFIX_APPLICATION_STATUS + "APPLICATION STATUS] "
            + "[" + PREFIX_INTERVIEW_STATUS + "INTERVIEW STATUS] "
            + "[" + PREFIX_AVAILABILITY + "AVAILABILITY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "E0123456@u.nus.edu";

    public static final String MESSAGE_EDIT_CANDIDATE_SUCCESS = "Edited Candidate: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CANDIDATE = "This candidate already exists in the system";

    private final Index index;
    private final EditCandidateDescriptor editCandidateDescriptor;

    /**
     * @param index of the candidate in the filtered candidate list to edit
     * @param editCandidateDescriptor details to edit the candidate with
     */
    public EditCommand(Index index, EditCandidateDescriptor editCandidateDescriptor) {
        requireNonNull(index);
        requireNonNull(editCandidateDescriptor);

        this.index = index;
        this.editCandidateDescriptor = new EditCandidateDescriptor(editCandidateDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Candidate> lastShownList = model.getFilteredCandidateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CANDIDATE_DISPLAYED_INDEX);
        }

        Candidate candidateToEdit = lastShownList.get(index.getZeroBased());
        Candidate editedCandidate = createEditedCandidate(candidateToEdit, editCandidateDescriptor);

        if (!candidateToEdit.isSameCandidate(editedCandidate) && model.hasCandidate(editedCandidate)) {
            throw new CommandException(MESSAGE_DUPLICATE_CANDIDATE);
        }

        model.setCandidate(candidateToEdit, editedCandidate);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);
        return new CommandResult(String.format(MESSAGE_EDIT_CANDIDATE_SUCCESS, editedCandidate));
    }

    /**
     * Creates and returns a {@code Candidate} with the details of {@code candidateToEdit}
     * edited with {@code editCandidateDescriptor}.
     */
    private static Candidate createEditedCandidate(
            Candidate candidateToEdit, EditCandidateDescriptor editCandidateDescriptor) {
        assert candidateToEdit != null;

        StudentId updatedID = editCandidateDescriptor.getStudentId().orElse(candidateToEdit.getStudentId());
        Name updatedName = editCandidateDescriptor.getName().orElse(candidateToEdit.getName());
        Phone updatedPhone = editCandidateDescriptor.getPhone().orElse(candidateToEdit.getPhone());
        Email updatedEmail = editCandidateDescriptor.getEmail().orElse(candidateToEdit.getEmail());
        Course updatedCourse = editCandidateDescriptor.getCourse().orElse(candidateToEdit.getCourse());
        Set<Tag> updatedTags = editCandidateDescriptor.getTags().orElse(candidateToEdit.getTags());
        ApplicationStatus applicationStatus = editCandidateDescriptor.getApplicationStatus()
                .orElse(candidateToEdit.getApplicationStatus());
        InterviewStatus interviewStatus = editCandidateDescriptor.getInterviewStatus()
                .orElse(candidateToEdit.getInterviewStatus());
        Availability updatedAvailability = editCandidateDescriptor.getAvailability()
                .orElse(candidateToEdit.getAvailability());

        return new Candidate(updatedID, updatedName, updatedPhone, updatedEmail,
                updatedCourse, updatedTags, applicationStatus, interviewStatus, updatedAvailability);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editCandidateDescriptor.equals(e.editCandidateDescriptor);
    }

    /**
     * Stores the details to edit the candidate with. Each non-empty field value will replace the
     * corresponding field value of the candidate.
     */
    public static class EditCandidateDescriptor {
        private StudentId studentId;
        private Name name;
        private Phone phone;
        private Email email;
        private Course course;

        private Set<Tag> tags;
        private ApplicationStatus applicationStatus;
        private InterviewStatus interviewStatus;
        private Availability availability;

        public EditCandidateDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCandidateDescriptor(EditCandidateDescriptor toCopy) {
            setStudentId(toCopy.studentId);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setCourse(toCopy.course);
            setTags(toCopy.tags);
            setApplicationStatus(toCopy.applicationStatus);
            setInterviewStatus(toCopy.interviewStatus);
            setAvailability(toCopy.availability);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(studentId, name, phone, email, course, tags,
                    applicationStatus, interviewStatus, availability);
        }

        public void setStudentId(StudentId id) {
            this.studentId = id;
        }

        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public Optional<Course> getCourse() {
            return Optional.ofNullable(course);
        }

        public void setApplicationStatus(ApplicationStatus applicationStatus) {
            this.applicationStatus = applicationStatus;
            triggerInterviewStatus(applicationStatus);
        }

        public Optional<ApplicationStatus> getApplicationStatus() {
            return Optional.ofNullable(applicationStatus);
        }

        public void setInterviewStatus(InterviewStatus interviewStatus) {
            this.interviewStatus = interviewStatus;
        }

        public Optional<InterviewStatus> getInterviewStatus() {
            return Optional.ofNullable(interviewStatus);
        }

        public void setAvailability(Availability availability) {
            this.availability = availability;
        }

        public Optional<Availability> getAvailability() {
            return Optional.ofNullable(availability);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCandidateDescriptor)) {
                return false;
            }

            // state check
            EditCandidateDescriptor e = (EditCandidateDescriptor) other;

            return getStudentId().equals(e.getStudentId())
                    && getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getCourse().equals(e.getCourse())
                    && getTags().equals(e.getTags())
                    && getApplicationStatus().equals(e.getApplicationStatus())
                    && getInterviewStatus().equals(e.getInterviewStatus())
                    && getAvailability().equals(e.getAvailability());
        }


        /**
         * Allows the {@code InterviewStatus} to be triggered by ApplicationStatus.
         */
        public void triggerInterviewStatus(ApplicationStatus applicationStatus) {
            if (getApplicationStatus().isPresent()) {
                if (applicationStatus.toString().equals(ACCEPTED_STATUS)
                        || applicationStatus.toString().equals(REJECTED_STATUS)) {
                    setInterviewStatus(new InterviewStatus(COMPLETED));
                }
            }
        }
    }
}
