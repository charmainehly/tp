package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.interview.Interview;

/**
 * Unmodifiable view of an interview list
 */
public interface ReadOnlyInterviewSchedule {
    /**
     * Returns an unmodifiable view of the candidates list.
     * This list will not contain any duplicate candidates.
     */
    ObservableList<Interview> getInterviewList();
}
