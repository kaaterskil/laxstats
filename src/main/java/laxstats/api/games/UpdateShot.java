package laxstats.api.games;

/**
 * {@code UpdateShot} represents a command to change the state of an existing shot.
 */
public class UpdateShot extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates an {@code UpdateShot} command with the given aggregate identifier, play key and
    * updated shot information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public UpdateShot(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns information with which to update the play.
    * 
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
