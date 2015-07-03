package laxstats.api.games;

/**
 * {@code UpdateFaceOff} represents a command to change the state of an existing face-off.
 */
public class UpdateFaceOff extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates an {@code UpdateFaceOff} command with the given aggregate identifier, play key and
    * updated face-off information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public UpdateFaceOff(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns the information with which to update the play.
    * 
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
