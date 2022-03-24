package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate} matches any of the keywords given.
 */
public class CandidateContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {
    private final List<String> keywords;

    /**
     * Creates a new {@link CandidateContainsKeywordsPredicate} object with the
     * {@link CandidateContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}.
     */
    public CandidateContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
        this.keywords = keywords;
    }

    /**
     * Tests if any part of {@code Candidate} matches any of the specified
     * {@link CandidateContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the description.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(candidate.toString(), keyword));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link CandidateContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link CandidateContainsKeywordsPredicate} with the same
     * {@link CandidateContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CandidateContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CandidateContainsKeywordsPredicate) other).keywords)); // state check
    }

}
