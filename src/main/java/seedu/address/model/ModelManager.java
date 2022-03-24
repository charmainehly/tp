package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.interview.Interview;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final InterviewSchedule interviewSchedule;
    private final UserPrefs userPrefs;
    private final FilteredList<Candidate> filteredCandidates;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyInterviewSchedule interviewList,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + ", InterviewSchedule: " + interviewList
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        this.interviewSchedule = new InterviewSchedule(interviewList);
        filteredCandidates = new FilteredList<>(this.addressBook.getCandidateList());
    }

    public ModelManager() {
        this(new AddressBook(), new InterviewSchedule(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getInterviewScheduleFilePath() {
        return userPrefs.getInterviewListFilePath();
    }

    @Override
    public void setInterviewScheduleFilePath(Path interviewListFilePath) {
        requireNonNull(interviewListFilePath);
        userPrefs.setInterviewListFilePath(interviewListFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasCandidate(Candidate candidate) {
        requireNonNull(candidate);
        return addressBook.hasCandidate(candidate);
    }

    @Override
    public void deleteCandidate(Candidate target) {
        addressBook.removeCandidate(target);
    }

    @Override
    public void addCandidate(Candidate candidate) {
        addressBook.addCandidate(candidate);
        updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);
    }

    @Override
    public void setCandidate(Candidate target, Candidate editedCandidate) {
        requireAllNonNull(target, editedCandidate);

        addressBook.setCandidate(target, editedCandidate);
    }

    //=========== InterviewSchedule ================================================================================

    @Override
    public void setInterviewSchedule(ReadOnlyInterviewSchedule interviewList) {
        this.interviewSchedule.resetData(interviewList);
    }

    @Override
    public ReadOnlyInterviewSchedule getInterviewSchedule() {
        return interviewSchedule;
    }

    @Override
    public boolean hasInterviewCandidate(Interview interview) {
        requireNonNull(interview);
        return interviewSchedule.hasCandidate(interview);
    }

    @Override
    public boolean hasConflictingInterview(Interview interview) {
        requireNonNull(interview);
        return interviewSchedule.hasConflictingInterview(interview);
    }

    /*@Override
    public void deleteInterview(Interview target) {
        interviewSchedule.removeInterview(target);
    }*/

    @Override
    public void addInterview(Interview interview) {
        interviewSchedule.addInterview(interview);
        //updateFilteredCandidateList(PREDICATE_SHOW_ALL_CANDIDATES);
    }

    /*@Override
    public void setInterview(Interview target, Interview editedInterview) {
        requireAllNonNull(target, editedInterview);

        interviewSchedule.setInterview(target, editedInterview);
    }*/

    //=========== Filtered/Sort Candidate List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Candidate} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Candidate> getFilteredCandidateList() {
        return filteredCandidates;
    }

    @Override
    public void updateFilteredCandidateList(Predicate<Candidate> predicate) {
        requireNonNull(predicate);
        filteredCandidates.setPredicate(predicate);
    }

    @Override
    public void updateSortedCandidateList(Comparator<Candidate> sortComparator) {
        requireNonNull(sortComparator);
        addressBook.sortCandidates(filteredCandidates, sortComparator);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredCandidates.equals(other.filteredCandidates)
                && interviewSchedule.equals(other.interviewSchedule);
    }

}
