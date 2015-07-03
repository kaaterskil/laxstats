package laxstats.api.games;

/**
 * Exception thrown when a play participant has not been registered as a game attendee.
 */
public class ParticipantNotRegisteredException extends RuntimeException {
   private static final long serialVersionUID = 6488048098363666314L;

   public ParticipantNotRegisteredException(String string) {
      super(string);
   }

}
