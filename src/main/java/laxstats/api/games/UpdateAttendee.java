package laxstats.api.games;

/**
 * {@code UpdateAttendee} represents a command to change the state of an existing game attendee.
 */
public class UpdateAttendee extends AbstractGameCommand {
   private final AttendeeDTO attendeeDTO;

   /**
    * Creates a {@code UpdateAttendee} command with the given aggregate identifier and updated
    * attendee information.
    * 
    * @param gameId
    * @param attendeeDTO
    */
   public UpdateAttendee(GameId gameId, AttendeeDTO attendeeDTO) {
      super(gameId);
      this.attendeeDTO = attendeeDTO;
   }

   /**
    * Returns information with which to update the game attendee.
    * 
    * @return
    */
   public AttendeeDTO getAttendeeDTO() {
      return attendeeDTO;
   }
}
