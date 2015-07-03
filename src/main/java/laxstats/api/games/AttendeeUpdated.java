package laxstats.api.games;

/**
 * {@code AttendeeUpdated} represented an event marking the change in state of a game attendee.
 */
public class AttendeeUpdated {
   private final GameId gameId;
   private final AttendeeDTO attendeeDTO;

   /**
    * Creates an {@code AttendeeUpdated} event with the given aggregate identifier and updated
    * attendee information.
    *
    * @param gameId
    * @param attendeeDTO
    */
   public AttendeeUpdated(GameId gameId, AttendeeDTO attendeeDTO) {
      this.gameId = gameId;
      this.attendeeDTO = attendeeDTO;
   }

   /**
    * Returns the game aggregate identifier.
    *
    * @return
    */
   public GameId getEventId() {
      return gameId;
   }

   /**
    * Returns the updated attendee information.
    *
    * @return
    */
   public AttendeeDTO getAttendeeDTO() {
      return attendeeDTO;
   }
}
