package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalCandidates;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CANDIDATES_FILE = TEST_DATA_FOLDER.resolve("typicalCandidatesAddressBook.json");
    private static final Path INVALID_CANDIDATE_FILE = TEST_DATA_FOLDER.resolve("invalidCandidateAddressBook.json");
    private static final Path DUPLICATE_CANDIDATE_FILE = TEST_DATA_FOLDER.resolve("duplicateCandidateAddressBook.json");

    @Test
    public void toModelType_typicalCandidatesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_CANDIDATES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalCandidatesAddressBook = TypicalCandidates.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalCandidatesAddressBook);
    }

    @Test
    public void toModelType_invalidCandidateFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_CANDIDATE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateCandidates_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CANDIDATE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_CANDIDATE,
                dataFromFile::toModelType);
    }

}
