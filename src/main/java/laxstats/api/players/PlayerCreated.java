package laxstats.api.players;

/**
 * {@code PlayerCreated} represents an event marking the creation of a new player.
 */
public class PlayerCreated {
   private final PlayerId playerId;
   private final PlayerDTO playerDTO;

   /**
    * Creates a {@code PlayerCreated} event with the given aggregate identifier and player
    * information.
    * 
    * @param playerId
    * @param playerDTO
    */
   public PlayerCreated(PlayerId playerId, PlayerDTO playerDTO) {
      this.playerId = playerId;
      this.playerDTO = playerDTO;
   }

   /**
    * Returns the aggregate identifier of the new player.
    * 
    * @return
    */
   public PlayerId getPlayerId() {
      return playerId;
   }

   /**
    * Returns information about the new player.
    * 
    * @return
    */
   public PlayerDTO getPlayerDTO() {
      return playerDTO;
   }
}
