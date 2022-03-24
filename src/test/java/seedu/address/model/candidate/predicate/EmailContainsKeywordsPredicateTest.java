package seedu.address.model.candidate.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CandidateBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.singletonList(
                "alice@email.com"));
        assertTrue(predicate.test(new CandidateBuilder().withEmail("alice@email.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "@gmail"));
        assertTrue(predicate.test(new CandidateBuilder().withEmail("Alice@gmail.com.sg").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "email.com"));
        assertTrue(predicate.test(new CandidateBuilder().withEmail("Alice@gmail.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("aLIce", "@GMAIL.com"));
        assertTrue(predicate.test(new CandidateBuilder().withEmail("Alice@gmail.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CandidateBuilder().withEmail("Alice@gmail.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("gmail.com"));
        assertFalse(predicate.test(new CandidateBuilder().withEmail("alice@email.com").build()));

        // Keywords match other fields, but does/do not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("87654321", "E0324444", "Main",
                "Street"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));
    }
}
