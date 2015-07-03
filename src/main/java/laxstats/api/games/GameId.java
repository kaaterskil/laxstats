package laxstats.api.games;

import laxstats.api.AggregateId;

/**
 * {@code GameId} represents the aggregate identifier of a game.
 */
public class GameId extends AggregateId {
   private static final long serialVersionUID = 923566508988353139L;

   /**
    * Creates a {@code GameId} with a new unique identifier.
    */
   public GameId() {
   }

   /**
    * Creates a {@code GameId} with the given unique identifier.
    * 
    * @param eventId
    */
   public GameId(String eventId) {
      super(eventId);
   }
}
