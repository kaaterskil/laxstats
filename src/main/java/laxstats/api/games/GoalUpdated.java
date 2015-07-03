package laxstats.api.games;

/**
 * {@code GoalUpdated} represents an event marking the change in state of an existing goal.
 */
public class GoalUpdated extends AbstractPlayEvent {

   /**
    * Creates a {@code GoalUpdated} event with the given aggregate identifier, play key and updated
    * goal information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public GoalUpdated(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
