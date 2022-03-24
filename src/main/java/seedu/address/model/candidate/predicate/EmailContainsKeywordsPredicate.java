package seedu.address.model.candidate.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.candidate.Candidate;

/**
 * Tests that a {@code Candidate}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends ContainsKeywordsPredicate implements Predicate<Candidate> {
    private final List<String> keywords;

    /**
     * Creates a new {@link EmailContainsKeywordsPredicate} object with the
     * {@link EmailContainsKeywordsPredicate#keywords} initialised.
     * @param keywords contain keyword(s) to find in {@code Candidate}'s {@code Email}.
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
        this.keywords = keywords;
    }

    /**
     * Tests if any part of {@code Candidate}'s {@code Email} matches any of the specified
     * {@link EmailContainsKeywordsPredicate#keywords}.
     * @param candidate object to retrieve the {@code Email}.
     * @return true if a match is found, and false otherwise.
     */
    @Override
    public boolean test(Candidate candidate) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsStringIgnoreCase(candidate.getEmail().toString(), keyword));
    }

    /**
     * Checks if another object are instances of the same class and contains the same
     * {@link EmailContainsKeywordsPredicate#keywords}.
     * @param other object to compare against.
     * @return true if both objects are instances of {@link EmailContainsKeywordsPredicate} with the same
     * {@link EmailContainsKeywordsPredicate#keywords}.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
