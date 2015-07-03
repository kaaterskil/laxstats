package laxstats.api.games;

/**
 * {@code DeleteGoal} represents a command to delete a goal.
 */
public class DeleteGoal extends AbstractPlayCommand {

   /**
    * Creates a {@code DeleteGoal} command with the given aggregate identifier and play key.
    * 
    * @param gameId
    * @param playId
    */
   public DeleteGoal(GameId gameId, String playId) {
      super(gameId, playId);
   }
}
