package laxstats.web.games;

/**
 * Thrown when a game with associated plays is deleted.
 */
public class GameHasPlaysException extends IllegalArgumentException {

   private static final long serialVersionUID = -2525400339812065209L;

   /**
    * Creates a {@code GameHasAttendeesException}.
    */
   public GameHasPlaysException() {
      super("Cannot delete game with associated plays");
   }

}
