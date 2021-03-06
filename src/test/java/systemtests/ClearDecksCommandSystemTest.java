package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static seedu.address.testutil.TypicalDecks.KEYWORD_MATCHING_JOHN;

import org.junit.Test;

import seedu.address.logic.commands.ClearDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.DecksView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearDecksCommandSystemTest extends TopDeckSystemTest {

    @Test
    public void clear() {
        Model model = getModel();
        DecksView decksView = (DecksView) model.getViewState();

        /* Case: clear non-empty TopDeck -> cleared */
        assertCommandSuccess(ClearDeckCommand.COMMAND_WORD);

        /* Case: undo clearing address book -> original address book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        assertCommandSuccess(command, expectedResultMessage, model);
        assertSelectedDeckUnchanged();

        /* Case: redo clearing address book -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedDeckUnchanged();

        /* Case: filters the deck list before clearing -> entire address book cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original address book
        showDecksWithName(KEYWORD_MATCHING_JOHN, decksView);
        assertCommandSuccess(ClearDeckCommand.COMMAND_WORD);
        assertSelectedDeckUnchanged();

        /* Case: clear empty address book -> cleared */
        assertCommandSuccess(ClearDeckCommand.COMMAND_WORD);
        assertSelectedDeckUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearDeckCommand#MESSAGE_SUCCESS} and the model related components equal to an empty
     * model.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status
     * changes.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearDeckCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box
     * displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     *
     * @see ClearDecksCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected deck and status bar remain unchanged, and the command
     * box has the
     * error style.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedDeckUnchanged();
        assertCommandBoxShowsErrorStyle();
        //assertStatusBarUnchanged();
    }
}
