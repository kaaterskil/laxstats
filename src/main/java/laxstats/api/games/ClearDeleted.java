package laxstats.api.games;

/**
 * {@code ClearDeleted} represents an event marking the deletion of a clear.
 */
public class ClearDeleted extends AbstractPlayEvent {

   /**
    * Creates a {@code ClearDeleted} event with the given aggregate identifier and play key.
    * 
    * @param gameId
    * @param playId
    */
   public ClearDeleted(GameId gameId, String playId) {
      super(gameId, playId, null);
   }
}
