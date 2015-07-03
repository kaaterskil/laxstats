package laxstats.api.games;

/**
 * {@code FaceOffDeleted} represents an event marking the deletion of a face-off.
 */
public class FaceOffDeleted extends AbstractPlayEvent {

   /**
    * Creates a {@code FaceOffDeleted} event with the given aggregate identifier and key of the
    * deleted play.
    * 
    * @param gameId
    * @param playId
    */
   public FaceOffDeleted(GameId gameId, String playId) {
      super(gameId, playId, null);
   }
}
