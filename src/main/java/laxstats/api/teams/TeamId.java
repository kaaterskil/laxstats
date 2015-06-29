package laxstats.api.teams;

import laxstats.api.AggregateId;

/**
 * {@code TeamId} represents a team aggregate identifier.
 */
public class TeamId extends AggregateId {
   private static final long serialVersionUID = -2148472630083353413L;

   /**
    * Creates a {@code TeamId} with a new unique identifier.
    */
   public TeamId() {
      super();
   }

   /**
    * Creates a {@code TeamId} with the given unique identifier.
    * 
    * @param identifier
    */
   public TeamId(String identifier) {
      super(identifier);
   }
}
