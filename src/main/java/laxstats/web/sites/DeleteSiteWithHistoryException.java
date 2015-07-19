package laxstats.web.sites;

/**
 * Thrown when a playing field with associated games or plays is deleted.
 */
public class DeleteSiteWithHistoryException extends IllegalArgumentException {

   private static final long serialVersionUID = -7308586582675366418L;

   /**
    * Creates a {@code DeleteSiteWithHistoryException}.
    */
   public DeleteSiteWithHistoryException() {
      super("Cannot delete playing field with associated games or plays");
   }

}
