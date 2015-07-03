package laxstats.api.games;

/**
 * {@code ShotRecorded} represent an event marking the creation of a new shot.
 */
public class ShotRecorded extends AbstractPlayEvent {

   /**
    * Creates a {@code ShotRecorded} event with the given aggregate identifier, play key and new
    * shot information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public ShotRecorded(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
