package laxstats.api.games;

/**
 * {@code DeleteClear} represents a command to delete a clear.
 */
public class DeleteClear extends AbstractPlayCommand {

   /**
    * Creates a {@code DeleteClear} command with the given aggregate identifier and play key.
    * 
    * @param gameId
    * @param playId
    */
   public DeleteClear(GameId gameId, String playId) {
      super(gameId, playId);
   }
}
