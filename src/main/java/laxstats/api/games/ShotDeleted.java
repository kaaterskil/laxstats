package laxstats.api.games;

/**
 * {@code ShotDeleted} represents an event marking the deletion of a shot.
 */
public class ShotDeleted extends AbstractPlayEvent {

   /**
    * Creates a {@code ShotDeleted} event with the given aggregate identifier and key of the deleted
    * play.
    * 
    * @param gameId
    * @param playId
    */
   public ShotDeleted(GameId gameId, String playId) {
      super(gameId, playId, null);
   }
}
