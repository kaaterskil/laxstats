package laxstats.api.seasons;

import laxstats.api.AggregateId;

/**
 * {@code SeasonId} is the unique identifier for a season aggregate.
 */
public class SeasonId extends AggregateId {
   private static final long serialVersionUID = 7511307761939198548L;

   /**
    * Creates a season identifier with a new unique key.
    */
   public SeasonId() {
      super();
   }

   /**
    * Creates a season aggregate identifier with the given unique key.
    * 
    * @param identifier
    */
   public SeasonId(String identifier) {
      super(identifier);
   }
}
