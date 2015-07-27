package laxstats.web.games;

/**
 * Thrown when and attendee with associated plays is deleted.
 */
public class AttendeeHasPlaysException extends IllegalArgumentException {

   private static final long serialVersionUID = 5413781737658406283L;

   /**
    * Creates a {@code AttendeeHasPlaysException}.
    */
   public AttendeeHasPlaysException() {
      super("Cannot delete attendee with associated plays");
   }

}
