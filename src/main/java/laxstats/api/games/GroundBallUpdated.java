package laxstats.api.games;

/**
 * {@code GroundBallUpdated} represents an event marking the change in state of an existing ground
 * ball.
 */
public class GroundBallUpdated extends AbstractPlayEvent {

   /**
    * Creates a {@code GroundBallUpdated} event with the given aggregate identifier, play key and
    * updated play information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public GroundBallUpdated(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
