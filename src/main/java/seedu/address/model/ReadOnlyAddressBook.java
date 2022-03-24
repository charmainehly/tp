package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.candidate.Candidate;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the candidates list.
     * This list will not contain any duplicate candidates.
     */
    ObservableList<Candidate> getCandidateList();

}
