package laxstats.web.users;

/**
 * Thrown when the current user attempts to delete their own record.
 */
public class DeleteSameUserException extends IllegalArgumentException {

   private static final long serialVersionUID = 8639427952667689992L;

   /**
    * Creates a {@code DeleteSameUserException}.
    */
   public DeleteSameUserException() {
      super("You cannot delete yourself");
   }

}
