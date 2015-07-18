package laxstats.web.teams;

/**
 * Thrown when a user attempts to delete a team with which they are not associated.
 */
public class InvalidTeamOwnerException extends IllegalArgumentException {

   private static final long serialVersionUID = -2611859800419258147L;

   /**
    * Creates a {@code InvalidTeamOwnerException}.
    */
   public InvalidTeamOwnerException() {
      super("Invalid team owner");
   }

}
