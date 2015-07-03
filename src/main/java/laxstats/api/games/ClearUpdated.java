package laxstats.api.games;

/**
 * {@code ClearUpdated} represents an event marking the change in state of an existing clear play.
 */
public class ClearUpdated extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code ClearUpdated} event with the given aggregate identifier, play key and updated
    * play information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public ClearUpdated(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns the updated play information.
    * 
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
