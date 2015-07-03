package laxstats.api.games;

/**
 * {@code GoalRecorded} represents an event marking the creation of a new goal.
 */
public class GoalRecorded extends AbstractPlayEvent {

   /**
    * Creates a {@code GoalRecorded} event with the given aggregate identifier, play key, and
    * information about the new goal.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public GoalRecorded(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
