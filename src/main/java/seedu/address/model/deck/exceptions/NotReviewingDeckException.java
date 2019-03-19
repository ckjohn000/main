package seedu.address.model.deck.exceptions;

/**
 * Signals that the operation cannot occur without reviewing deck first.
 */
public class NotReviewingDeckException extends RuntimeException {
    public NotReviewingDeckException() {
        super("Operation requires user to be reviewing deck.");
    }
}
