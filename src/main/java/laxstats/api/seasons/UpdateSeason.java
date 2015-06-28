package laxstats.api.seasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code UpdateSeason} represents a command to update a season.
 */
public class UpdateSeason {

   @TargetAggregateIdentifier
   private final SeasonId seasonId;
   private final SeasonDTO seasonDTO;

   /**
    * Creates an {@code UpdateSeason} command with the given aggregate identifier and season
    * information to update.
    *
    * @param seasonId
    * @param seasonDTO
    */
   public UpdateSeason(SeasonId seasonId, SeasonDTO seasonDTO) {
      this.seasonId = seasonId;
      this.seasonDTO = seasonDTO;
   }

   /**
    * Returns the aggregate identifier of the season to update.
    *
    * @return
    */
   public SeasonId getSeasonId() {
      return seasonId;
   }

   /**
    * Returns the updated season information.
    *
    * @return
    */
   public SeasonDTO getSeasonDTO() {
      return seasonDTO;
   }
}
