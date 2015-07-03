package laxstats.api.games;

/**
 * {@code DeletePenalty} represents a command to delete a penalty.
 */
public class DeletePenalty extends AbstractPlayCommand {

   /**
    * Creates a {@code DeletePenalty} command with the given aggregate identifier and key of the
    * play to delete.
    * 
    * @param gameId
    * @param playId
    */
   public DeletePenalty(GameId gameId, String playId) {
      super(gameId, playId);
   }
}
