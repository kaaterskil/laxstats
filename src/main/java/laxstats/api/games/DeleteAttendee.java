package laxstats.api.games;

/**
 * {@code DeleteAttendee} represents a command to delete a game attendee.
 */
public class DeleteAttendee extends AbstractGameCommand {
   private final String attendeeId;

   /**
    * Creates a {@code DeleteAttendee} command with the given aggregate identifier and attendee key.
    * 
    * @param gameId
    * @param attendeeId
    */
   public DeleteAttendee(GameId gameId, String attendeeId) {
      super(gameId);
      this.attendeeId = attendeeId;
   }

   /**
    * Returns the unique key of the game attendee to delete.
    * 
    * @return
    */
   public String getAttendeeId() {
      return attendeeId;
   }
}
