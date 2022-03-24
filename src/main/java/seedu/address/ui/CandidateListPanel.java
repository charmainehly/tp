package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.candidate.Candidate;

/**
 * Panel containing the list of candidates.
 */
public class CandidateListPanel extends UiPart<Region> {
    private static final String FXML = "CandidateListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CandidateListPanel.class);

    @FXML
    private ListView<Candidate> candidateListView;

    /**
     * Creates a {@code CandidateListPanel} with the given {@code ObservableList}.
     */
    public CandidateListPanel(ObservableList<Candidate> candidateList) {
        super(FXML);
        candidateListView.setItems(candidateList);
        candidateListView.setCellFactory(listView -> new CandidateListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Candidate} using a {@code CandidateCard}.
     */
    class CandidateListViewCell extends ListCell<Candidate> {
        @Override
        protected void updateItem(Candidate candidate, boolean empty) {
            super.updateItem(candidate, empty);

            if (empty || candidate == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CandidateCard(candidate, getIndex() + 1).getRoot());
            }
        }
    }

}
