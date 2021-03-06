package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.ListItem;
import seedu.address.model.DecksView;
import seedu.address.model.ListViewState;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Deck> PREDICATE_MATCHING_NO_DECKS = unused -> false;
    private static final Predicate<ListItem> PREDICATE_MATCHING_NO_CARDS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(ListViewState<ListItem> viewState, List<ListItem> toDisplay) {
        Optional<Predicate<ListItem>> predicate = toDisplay.stream().map(ModelHelper::getPredicateMatching)
                                                           .reduce(Predicate::or);
        viewState.updateFilteredList(predicate.orElse(PREDICATE_MATCHING_NO_CARDS));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredDeckList(Model model, List<Deck> decksToDisplay) {
        DecksView decksView = (DecksView) model.getViewState();
        Optional<Predicate<Deck>> deckPredicate =
                decksToDisplay.stream().map(ModelHelper::getPredicateMatchingDecks).reduce(Predicate::or);
        decksView.updateFilteredList(deckPredicate.orElse(PREDICATE_MATCHING_NO_DECKS));
    }

    /**
     * @see ModelHelper#setFilteredDeckList(Model, List)
     */
    public static void setFilteredDeckList(Model model, Deck... toDisplay) {
        setFilteredDeckList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code ListItem} equals to {@code other}.
     *
     * @param other
     */
    private static Predicate<ListItem> getPredicateMatching(ListItem other) {
        return item -> item.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Deck} equals to {@code other}.
     */
    private static Predicate<Deck> getPredicateMatchingDecks(Deck other) {
        return deck -> deck.equals(other);
    }

}
