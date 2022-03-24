package seedu.address.model.candidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCandidates.ALICE;
import static seedu.address.testutil.TypicalCandidates.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.candidate.exceptions.DuplicateCandidateException;
import seedu.address.model.candidate.exceptions.CandidateNotFoundException;
import seedu.address.testutil.CandidateBuilder;

public class UniqueCandidateListTest {

    private final UniqueCandidateList uniqueCandidateList = new UniqueCandidateList();

    @Test
    public void contains_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.contains(null));
    }

    @Test
    public void contains_candidateNotInList_returnsFalse() {
        assertFalse(uniqueCandidateList.contains(ALICE));
    }

    @Test
    public void contains_candidateInList_returnsTrue() {
        uniqueCandidateList.add(ALICE);
        assertTrue(uniqueCandidateList.contains(ALICE));
    }

    @Test
    public void contains_candidateWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCandidateList.add(ALICE);
        Candidate editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueCandidateList.contains(editedAlice));
    }

    @Test
    public void add_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.add(null));
    }

    @Test
    public void add_duplicateCandidate_throwsDuplicateCandidateException() {
        uniqueCandidateList.add(ALICE);
        assertThrows(DuplicateCandidateException.class, () -> uniqueCandidateList.add(ALICE));
    }

    @Test
    public void setCandidate_nullTargetCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.setCandidate(null, ALICE));
    }

    @Test
    public void setCandidate_nullEditedCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.setCandidate(ALICE, null));
    }

    @Test
    public void setCandidate_targetCandidateNotInList_throwsCandidateNotFoundException() {
        assertThrows(CandidateNotFoundException.class, () -> uniqueCandidateList.setCandidate(ALICE, ALICE));
    }

    @Test
    public void setCandidate_editedCandidateIsSameCandidate_success() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.setCandidate(ALICE, ALICE);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(ALICE);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setCandidate_editedCandidateHasSameIdentity_success() {
        uniqueCandidateList.add(ALICE);
        Candidate editedAlice = new CandidateBuilder(ALICE).withCourse(VALID_COURSE_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueCandidateList.setCandidate(ALICE, editedAlice);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(editedAlice);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setCandidate_editedCandidateHasDifferentIdentity_success() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.setCandidate(ALICE, BOB);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(BOB);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setCandidate_editedCandidateHasNonUniqueIdentity_throwsDuplicateCandidateException() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.add(BOB);
        assertThrows(DuplicateCandidateException.class, () -> uniqueCandidateList.setCandidate(ALICE, BOB));
    }

    @Test
    public void remove_nullCandidate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.remove(null));
    }

    @Test
    public void remove_candidateDoesNotExist_throwsCandidateNotFoundException() {
        assertThrows(CandidateNotFoundException.class, () -> uniqueCandidateList.remove(ALICE));
    }

    @Test
    public void remove_existingCandidate_removesCandidate() {
        uniqueCandidateList.add(ALICE);
        uniqueCandidateList.remove(ALICE);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setCandidates_nullUniqueCandidateList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.setCandidates((UniqueCandidateList) null));
    }

    @Test
    public void setCandidates_uniqueCandidateList_replacesOwnListWithProvidedUniqueCandidateList() {
        uniqueCandidateList.add(ALICE);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(BOB);
        uniqueCandidateList.setCandidates(expectedUniqueCandidateList);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setCandidates_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCandidateList.setCandidates((List<Candidate>) null));
    }

    @Test
    public void setCandidates_list_replacesOwnListWithProvidedList() {
        uniqueCandidateList.add(ALICE);
        List<Candidate> candidateList = Collections.singletonList(BOB);
        uniqueCandidateList.setCandidates(candidateList);
        UniqueCandidateList expectedUniqueCandidateList = new UniqueCandidateList();
        expectedUniqueCandidateList.add(BOB);
        assertEquals(expectedUniqueCandidateList, uniqueCandidateList);
    }

    @Test
    public void setCandidates_listWithDuplicateCandidates_throwsDuplicateCandidateException() {
        List<Candidate> listWithDuplicateCandidates = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateCandidateException.class, () -> uniqueCandidateList.setCandidates(listWithDuplicateCandidates));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCandidateList.asUnmodifiableObservableList().remove(0));
    }
}
