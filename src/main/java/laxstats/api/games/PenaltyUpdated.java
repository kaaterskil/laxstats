package laxstats.api.games;

/**
 * {@code PenaltyUpdated} represents an event marking the change in state of an existing penalty.
 */
public class PenaltyUpdated extends AbstractPlayEvent {

   /**
    * Creates a {@code OEnaltyUpdated} event with the given aggregate identifier, play key and
    * updated play information.
    *
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public PenaltyUpdated(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
