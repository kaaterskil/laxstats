package laxstats.web.teams;

/**
 * Thrown when a team is being deleted and has created or modified games or plays.
 */
public class DeleteTeamWithHistoryException extends IllegalArgumentException {

   private static final long serialVersionUID = -3991515406183143153L;

   /**
    * Creates a {@code TeamDeletedWithHistoryException}.
    */
   public DeleteTeamWithHistoryException() {
      super("Cannot delete team with game or play history");
   }

}
