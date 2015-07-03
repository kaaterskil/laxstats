package laxstats.api.games;

/**
 * {@code GoalDeleted} represents an event marking the deletion of a goal.
 */
public class GoalDeleted extends AbstractPlayEvent {

   /**
    * Creates a {@code GoalDeleted} event with the given aggregate identifier and key of the deleted
    * play.
    * 
    * @param gameId
    * @param playId
    */
   public GoalDeleted(GameId gameId, String playId) {
      super(gameId, playId, null);
   }
}
