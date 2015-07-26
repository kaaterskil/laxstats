package laxstats.web.players;

/**
 * Thrown when a player who attended one or more games is being deleted.
 */
public class PlayerAttendedGamesException extends IllegalArgumentException {

   private static final long serialVersionUID = 8186117957634138421L;

   /**
    * Creates a {@code PlayerAttendedGamesException}.
    */
   public PlayerAttendedGamesException() {
      super("Cannot delete player who attended one or more games");
   }

}
