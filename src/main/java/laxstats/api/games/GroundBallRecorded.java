package laxstats.api.games;

/**
 * {@code GroundBallReorded} represents an event marking the creation of a new ground ball.
 */
public class GroundBallRecorded extends AbstractPlayEvent {

   /**
    * Creates a {@code GroundBallRecorded} event with the given aggregate identifier, play key, and
    * information about the new play.
    *
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public GroundBallRecorded(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
