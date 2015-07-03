package laxstats.api.games;

/**
 * {@code DeleteFaceOff} represents a command to delete a face-off.
 */
public class DeleteFaceOff extends AbstractPlayCommand {

   /**
    * Creates a {@code DeleteFaceOff} command with the given aggregate identifier and key of the
    * play to delete.
    * 
    * @param gameId
    * @param playId
    */
   public DeleteFaceOff(GameId gameId, String playId) {
      super(gameId, playId);
   }
}
