package laxstats.api.players;

/**
 * {@code PlayerDeleted} represents an event marking the deletion of a player.
 */
public class PlayerDeleted {
   private final PlayerId playerId;

   /**
    * Creates a {@code PlayerDeleted} event with the given aggregate identifier.
    * 
    * @param playerId
    */
   public PlayerDeleted(PlayerId playerId) {
      this.playerId = playerId;
   }

   /**
    * Returns the aggregate identifier of the deleted player.
    * 
    * @return
    */
   public PlayerId getPlayerId() {
      return playerId;
   }

}
