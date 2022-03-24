package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.candidate.Candidate;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_CANDIDATE = "Candidates list contains duplicate candidate(s).";

    private final List<JsonAdaptedCandidate> candidates = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given candidates.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("candidates") List<JsonAdaptedCandidate> candidates) {
        this.candidates.addAll(candidates);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        candidates.addAll(
                source.getCandidateList().stream().map(JsonAdaptedCandidate::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedCandidate jsonAdaptedCandidate : candidates) {
            Candidate candidate = jsonAdaptedCandidate.toModelType();
            if (addressBook.hasCandidate(candidate)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CANDIDATE);
            }
            addressBook.addCandidate(candidate);
        }
        return addressBook;
    }

}
