package laxstats.api.games;

/**
 * {@code RecordFaceOff} represents a command to create a new face-off.
 */
public class RecordFaceoff extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code RecordFaceOff} command with the given aggregate identifier, play key and
    * face-off information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public RecordFaceoff(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns information with which to create a new play.
    * 
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
