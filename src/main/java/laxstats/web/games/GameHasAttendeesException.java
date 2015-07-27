package laxstats.web.games;

/**
 * Thrown when a game with associated attendees is deleted.
 */
public class GameHasAttendeesException extends IllegalArgumentException {

   private static final long serialVersionUID = -8073832657584477703L;

   /**
    * Creates a {@code GameHasAttendeesException}.
    */
   public GameHasAttendeesException() {
      super("Cannot delete game with associated attendees");
   }

}
