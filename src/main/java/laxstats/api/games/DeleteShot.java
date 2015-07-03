package laxstats.api.games;

/**
 * {@code DeleteShot} represents a command to delete a shot.
 */
public class DeleteShot extends AbstractPlayCommand {

   /**
    * Creates a {@code DeleteShot} command with the given aggregate identifier and key of the play
    * to delete.
    * 
    * @param gameId
    * @param playId
    */
   public DeleteShot(GameId gameId, String playId) {
      super(gameId, playId);
   }
}
