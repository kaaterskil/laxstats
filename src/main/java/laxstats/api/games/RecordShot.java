package laxstats.api.games;

/**
 * {@code RecordShot} represents a command to create a new shot.
 */
public class RecordShot extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code RecordShot} command with the given aggregate identifier, play key and new
    * shot information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public RecordShot(GameId gameId, String playId, PlayDTO playDTO) {
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
