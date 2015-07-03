package laxstats.api.games;

/**
 * {@code FaceOffUpdated} represents an event marking the change in state of an existing face-off.
 */
public class FaceOffUpdated extends AbstractPlayEvent {

   /**
    * Creates a {@code FaceOffUpdated} event with the given aggregate identifier, play key and
    * updated play information.
    *
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public FaceOffUpdated(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
