package laxstats.api.games;

/**
 * {@code GroundBallDeleted} represents an event marking the deletion of a ground ball.
 */
public class GroundBallDeleted extends AbstractPlayEvent {

   /**
    * Creates a {@code GroundBallDeleted} event with the given aggregate identifier and key of the
    * deleted play.
    *
    * @param gameId
    * @param playId
    */
   public GroundBallDeleted(GameId gameId, String playId) {
      super(gameId, playId, null);
   }
}
