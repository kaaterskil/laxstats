package laxstats.api.games;

/**
 * Exception thrown when an attempt is made to change the state of a game attendee who is not
 * registered with the game.
 */
public class AttendeeNotRegisteredException extends IllegalArgumentException {
   private static final long serialVersionUID = -8573964324017353948L;

   public AttendeeNotRegisteredException() {
      super();
   }

   public AttendeeNotRegisteredException(String s) {
      super(s);
   }

}
