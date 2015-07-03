package laxstats.api.games;

/**
 * {@code AttendeeDeleted} represents an event marking the deletion of a game attendee.
 */
public class AttendeeDeleted {
   private final GameId gameId;
   private final String attendeeId;

   /**
    * Creates an {@code AttendeeDeleted} event with the given aggregate identifier and attendee key.
    * 
    * @param gameId
    * @param attendeeId
    */
   public AttendeeDeleted(GameId gameId, String attendeeId) {
      this.gameId = gameId;
      this.attendeeId = attendeeId;
   }

   /**
    * Returns the game aggregate idntifier.
    * 
    * @return
    */
   public GameId getEventId() {
      return gameId;
   }

   /**
    * Reutrns the unique identifier of the deleted attendee.
    * 
    * @return
    */
   public String getAttendeeId() {
      return attendeeId;
   }
}
