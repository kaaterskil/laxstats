package laxstats.api.seasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code DeleteSeason} represents a command to delete a season.
 */
public class DeleteSeason {

   @TargetAggregateIdentifier
   private final SeasonId seasonId;

   /**
    * Creates a command with the given aggregate identifier.
    * 
    * @param seasonId
    */
   public DeleteSeason(SeasonId seasonId) {
      this.seasonId = seasonId;
   }

   /**
    * Returns the season aggregate identifier.
    * 
    * @return
    */
   public SeasonId getSeasonId() {
      return seasonId;
   }
}
