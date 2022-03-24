package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPLICATION_PENDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAILABILITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_NOT_SCHEDULED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCandidates.ALICE;
import static seedu.address.testutil.TypicalCandidates.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;
import seedu.address.model.candidate.exceptions.DuplicateCandidateException;
import seedu.address.testutil.CandidateBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCandidateList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateCandidates_throwsDuplicateCandidateException() {
        // Two candidates with the same identity fields
        Candidate editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND)
                .withApplicationStatus(VALID_APPLICATION_PENDING)
                .withInterviewStatus(VALID_INTERVIEW_NOT_SCHEDULED)
                .withAvailability(VALID_AVAILABILITY_BOB)
                .build();
        List<Candidate> newCandidates = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newCandidates);

        assertThrows(DuplicateCandidateException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void sortCandidates_basedOnName_returnsSortedList() {
        ObservableList<Candidate> newData = addressBook.getCandidateList();
        List<Candidate> candidatesCopy = new ArrayList<Candidate>(newData);
        Comparator<Candidate> sortComparator = Comparator.comparing(l -> l.getName().toString().toLowerCase());
        candidatesCopy.sort(sortComparator);
        addressBook.setCandidates(newData);
        addressBook.sortCandidates(newData, sortComparator);

        assertEquals(candidatesCopy, addressBook.getCandidateList());
    }


    @Test
    public void hasCandidate_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasCandidate(null));
    }

    @Test
    public void hasCandidate_candidateNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCandidate(ALICE));
    }

    @Test
    public void hasCandidate_candidateInAddressBook_returnsTrue() {
        addressBook.addCandidate(ALICE);
        assertTrue(addressBook.hasCandidate(ALICE));
    }

    @Test
    public void hasCandidate_candidateWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCandidate(ALICE);
        Candidate editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND)
                .withAvailability(VALID_AVAILABILITY_BOB).build();
        assertTrue(addressBook.hasCandidate(editedAlice));
    }

    @Test
    public void getCandidateList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getCandidateList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose candidates list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Candidate> candidates = FXCollections.observableArrayList();

        AddressBookStub(Collection<Candidate> candidates) {
            this.candidates.setAll(candidates);
        }

        @Override
        public ObservableList<Candidate> getCandidateList() {
            return candidates;
        }
    }

}
