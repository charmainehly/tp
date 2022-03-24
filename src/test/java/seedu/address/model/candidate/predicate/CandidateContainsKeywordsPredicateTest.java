package seedu.address.model.candidate.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CandidateBuilder;

public class CandidateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CandidateContainsKeywordsPredicate firstPredicate = new CandidateContainsKeywordsPredicate(firstPredicateKeywordList);
        CandidateContainsKeywordsPredicate secondPredicate =
                new CandidateContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CandidateContainsKeywordsPredicate firstPredicateCopy =
                new CandidateContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_candidateContainsKeywords_returnsTrue() {
        // One keyword
        CandidateContainsKeywordsPredicate predicate = new CandidateContainsKeywordsPredicate(Collections.singletonList(
                "Alice"));
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));

        // Multiple keywords
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Alice", "Business Analytics"));
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));

        // Only one matching keyword
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));

        // Mixed-case keywords
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("aLIce", "e0324"));
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));
    }

    @Test
    public void test_candidateDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CandidateContainsKeywordsPredicate predicate = new CandidateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));

        // Non-matching keyword
        predicate = new CandidateContainsKeywordsPredicate(Arrays.asList("Carol Alice"));
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("87654321")
                .withEmail("alice@email.com").withCourse("Business Analytics").withStudentId("E0324444").build()));
    }
}
