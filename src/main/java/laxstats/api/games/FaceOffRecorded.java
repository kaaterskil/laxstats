package laxstats.api.games;

/**
 * {@code FaceOffRecorded} represents an event marking the creation of a face-off.
 */
public class FaceOffRecorded extends AbstractPlayEvent {

   /**
    * Creates a {@code FaceOffRecorded} event wit the given aggregate identifier, play key and
    * information about the new play.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public FaceOffRecorded(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
