package laxstats.web.people;

/**
 * Thrown when a person with associated team roster records is being deleted.
 */
public class DeletePersonWithTeamHistoryException extends IllegalArgumentException {

   private static final long serialVersionUID = 7943102929971762356L;

   /**
    * Creates a {@code DeletePersonWithTeamHistoryException}
    */
   public DeletePersonWithTeamHistoryException() {
      super("Cannot delete person with associated team history");
   }

}
