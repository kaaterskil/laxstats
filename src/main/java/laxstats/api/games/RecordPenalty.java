package laxstats.api.games;

/**
 * {@code RecordPenalty} represents a command to create a new penalty.
 */
public class RecordPenalty extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code RecordPenalty} command with the given aggregate identifier, lay key and new
    * penalty information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public RecordPenalty(GameId gameId, String playId, PlayDTO playDTO) {
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
