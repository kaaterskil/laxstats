package laxstats.api.games;

/**
 * An exception thrown when an attempt was made to register a player who is injured or inactive as a
 * game attendee.
 */
public class UnavailablePlayerException extends IllegalArgumentException {
   private static final long serialVersionUID = 3831683389784831745L;

}
