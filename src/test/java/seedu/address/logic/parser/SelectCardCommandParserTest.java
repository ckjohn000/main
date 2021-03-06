package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;
import static seedu.address.testutil.TypicalCards.getTypicalTopDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.SelectCardCommand;
import seedu.address.model.CardsView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Test scope: similar to {@code DeleteCardCommandParserTest}.
 *
 * @see DeleteCardCommandParserTest
 */
public class SelectCardCommandParserTest {

    private Model model = new ModelManager(getTypicalTopDeck(), new UserPrefs());
    private SelectCardCommandParser parser;

    @Before
    public void initialize() {
        model.changeDeck(getTypicalDeck());
        assertTrue(model.isAtCardsView());
        parser = new SelectCardCommandParser((CardsView) model.getViewState());
    }

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1",
                           new SelectCardCommand((CardsView) model.getViewState(), INDEX_FIRST_CARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                           String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));
    }
}
