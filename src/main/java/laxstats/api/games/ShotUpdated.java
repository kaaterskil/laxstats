package laxstats.api.games;

/**
 * {@code ShotUpdated} represents an event marking the change in state of an existing shot.
 */
public class ShotUpdated extends AbstractPlayEvent {

   /**
    * Creates a {@code ShotUpdated} event with the given aggregate identifier, play key and updated
    * shot information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public ShotUpdated(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
