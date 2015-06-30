package laxstats.api.players;

/**
 * {@code PlayerUpdated} represents an event marking a change in status of an existing player.
 */
public class PlayerUpdated {
   private final PlayerId playerId;
   private final PlayerDTO playerDTO;

   /**
    * Creates a {@code PlayerUpdated} event with the given aggregate identifier and updated player
    * information.
    * 
    * @param playerId
    * @param playerDTO
    */
   public PlayerUpdated(PlayerId playerId, PlayerDTO playerDTO) {
      this.playerId = playerId;
      this.playerDTO = playerDTO;
   }

   /**
    * Returns the aggregate identifier of the updated player.
    * 
    * @return
    */
   public PlayerId getPlayerId() {
      return playerId;
   }

   /**
    * Returns the updated information.
    * 
    * @return
    */
   public PlayerDTO getPlayerDTO() {
      return playerDTO;
   }
}
