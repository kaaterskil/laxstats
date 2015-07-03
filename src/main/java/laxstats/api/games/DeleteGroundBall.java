package laxstats.api.games;

/**
 * {@code DeleteGroundBall} represents a command to delete a ground ball.
 */
public class DeleteGroundBall extends AbstractPlayCommand {

   /**
    * Creates a {@code DeleteGroundBall} command with the given aggregate identifier and play key.
    * 
    * @param gameId
    * @param playId
    */
   public DeleteGroundBall(GameId gameId, String playId) {
      super(gameId, playId);
   }
}
