package laxstats.api.games;

/**
 * {@code ClearRecorded} represents an event marking the creation of a clear.
 */
public class ClearRecorded extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code ClearRecorded} event with the given aggregate identifier, play key and play
    * information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public ClearRecorded(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns the new play information.
    * 
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
