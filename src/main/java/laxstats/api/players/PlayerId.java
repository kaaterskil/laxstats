package laxstats.api.players;

import laxstats.api.AggregateId;

/**
 * {@code PlayerId} represents the aggregate identifier of a player.
 */
public class PlayerId extends AggregateId {
   private static final long serialVersionUID = 8363684825886208308L;

   /**
    * Creates a {@code PlayerId} with a new unique identifier.
    */
   public PlayerId() {
      super();
   }

   /**
    * Creates a {@code PlayerId} with the given unique identifier.
    * 
    * @param identifier
    */
   public PlayerId(String identifier) {
      super(identifier);
   }
}
