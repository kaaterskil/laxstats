package laxstats.api.teamSeasons;

import laxstats.api.AggregateId;

/**
 * {@code TeamSeasonId} represents the aggregate identifier for a team season.
 */
public class TeamSeasonId extends AggregateId {
   private static final long serialVersionUID = -5776121020631292936L;

   /**
    * Creates a {@code TeamSeasonId} with a new unique identifier.
    */
   public TeamSeasonId() {
      super();
   }

   /**
    * Creates a {@code TeamSeasonId} with the given unique identifier.
    * 
    * @param identifier
    */
   public TeamSeasonId(String identifier) {
      super(identifier);
   }
}
