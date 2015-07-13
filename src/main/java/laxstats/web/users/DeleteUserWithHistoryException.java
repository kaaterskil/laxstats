package laxstats.web.users;

/**
 * Thrown when a user is being deleted and has created or modified games or plays.
 */
public class DeleteUserWithHistoryException extends IllegalArgumentException {

   private static final long serialVersionUID = 8019075344834947303L;

   /**
    * Creates a {@code DeleteUserWithHistoryException}.
    */
   public DeleteUserWithHistoryException() {
      super("Cannot delete user with game or play history");
   }

}
