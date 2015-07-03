package laxstats.api.games;

/**
 * {@code PenaltyRecorded} represents an event marking the creation of a penalty.
 */
public class PenaltyRecorded extends AbstractPlayEvent {

   /**
    * Creates a {@code PenaltyRecorded} event with the given aggregate identifier, play key and
    * information about the new play.
    *
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public PenaltyRecorded(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId, playDTO);
   }
}
