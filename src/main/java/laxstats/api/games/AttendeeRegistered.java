package laxstats.api.games;

/**
 * {@code AttendeeRegistered} represents an event marking the creation of a game attendee.
 */
public class AttendeeRegistered {
   private final GameId gameId;
   private final AttendeeDTO attendeeDTO;

   /**
    * Creates an {@code AttendeeRegistered} event with the given aggregate identifier and new
    * attendee information.
    * 
    * @param gameId
    * @param attendeeDTO
    */
   public AttendeeRegistered(GameId gameId, AttendeeDTO attendeeDTO) {
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
    * Returns information about the new attendee.
    * 
    * @return
    */
   public AttendeeDTO getAttendeeDTO() {
      return attendeeDTO;
   }
}
