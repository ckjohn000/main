package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.ListItem;
import seedu.address.logic.StudyView;
import seedu.address.model.deck.Card;


/**
 * The Study Panel of the App.
 */
public class StudyPanel extends UiPart<Region> {

    private static final String FXML = "StudyPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    private final String YOUR_ANSWER_LABEL = "Your answer: ";

    private static final PseudoClass ANSWER = PseudoClass.getPseudoClass("answer");

    @FXML
    private HBox studyPane;

    @FXML
    private HBox card;

    @FXML
    private Label question;

    @FXML
    private Label id;

    @FXML
    private Label userAnswerLabel;


    public StudyPanel(ObservableValue<String> textShown,
                      ObservableValue<StudyView.studyState> studyState,
                      ObservableValue<String> userAnswer) {
        super(FXML);

        question.setText(textShown.getValue());
        userAnswerLabel.setVisible(false);

        card.pseudoClassStateChanged(ANSWER, false);

        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("textShown changed to: " + newValue);
            question.setText(textShown.getValue());
        });

        studyState.addListener((observable, oldValue, newValue) -> {
            logger.info("color changed for: " + newValue);
            card.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.studyState.ANSWER);
            question.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.studyState.ANSWER);
            userAnswerLabel.setVisible(studyState.getValue() == StudyView.studyState.ANSWER);
        });

        userAnswer.addListener((observable, oldValue, newValue) -> {
            logger.info("user answer changed to: " + newValue);
            userAnswerLabel.setText(YOUR_ANSWER_LABEL + userAnswer.getValue());
        });

    }

}