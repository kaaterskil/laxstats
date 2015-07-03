package laxstats.api.games;

/**
 * {@code RegisterAttendee} represents a command to create a new game attendee.
 */
public class RegisterAttendee extends AbstractGameCommand {
   private final AttendeeDTO attendeeDTO;

   /**
    * Creates a {@code RegisterAttendee} command with the given aggregate identifier and new
    * attendee information.
    * 
    * @param gameId
    * @param attendeeDTO
    */
   public RegisterAttendee(GameId gameId, AttendeeDTO attendeeDTO) {
      super(gameId);
      this.attendeeDTO = attendeeDTO;
   }

   /**
    * Returns information with which to create a new game attendee.
    * 
    * @return
    */
   public AttendeeDTO getAttendeeDTO() {
      return attendeeDTO;
   }
}
