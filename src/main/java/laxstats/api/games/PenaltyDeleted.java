package laxstats.api.games;

/**
 * {@code PenaltyDeleted} represents an event marking the deletion of a penalty.
 */
public class PenaltyDeleted extends AbstractPlayEvent {

   /**
    * Creates a {@code PenaltyDeleted} event with the given aggregate identifier and key of the
    * deleted play.
    *
    * @param gameId
    * @param playId
    */
   public PenaltyDeleted(GameId gameId, String playId) {
      super(gameId, playId, null);
   }
}
