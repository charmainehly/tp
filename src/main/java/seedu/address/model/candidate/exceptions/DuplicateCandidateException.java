package seedu.address.model.candidate.exceptions;

/**
 * Signals that the operation will result in duplicate Candidates
 * (Candidates are considered duplicates if they have the same identity).
 */
public class DuplicateCandidateException extends RuntimeException {
    public DuplicateCandidateException() {
        super("Operation would result in duplicate candidates");
    }
}
