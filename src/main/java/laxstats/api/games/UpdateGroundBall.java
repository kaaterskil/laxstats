package laxstats.api.games;

/**
 * {@code UpdateGroundBall} represents a command to change the state of an existing ground ball.
 */
public class UpdateGroundBall extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates an {@code UpdateGroundBall} command with the given aggregate identifier, play key and
    * updated ground ball information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public UpdateGroundBall(GameId gameId, String playId, PlayDTO playDTO) {
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
