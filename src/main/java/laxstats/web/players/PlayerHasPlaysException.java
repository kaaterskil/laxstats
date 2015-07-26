package laxstats.web.players;

/**
 * Thrown when a player who has earned playing statistics is beign deleted.
 */
public class PlayerHasPlaysException extends IllegalArgumentException {

   private static final long serialVersionUID = 7317032336280500438L;

   /**
    * Creates a {@code PlayerHasPlaysException}.
    */
   public PlayerHasPlaysException() {
      super("Cannot delete player with associated plays");
   }

}
