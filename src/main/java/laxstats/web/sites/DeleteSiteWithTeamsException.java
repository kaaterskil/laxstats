package laxstats.web.sites;

/**
 * Thrown when a playing field with an associated team is deleted.
 */
public class DeleteSiteWithTeamsException extends IllegalArgumentException {

   private static final long serialVersionUID = -8349507039799634471L;

   /**
    * Creates a {@code DeleteSiteWithTeamsException}.
    */
   public DeleteSiteWithTeamsException() {
      super("Cannot delete a plyaing field with an associated team");
   }

}
