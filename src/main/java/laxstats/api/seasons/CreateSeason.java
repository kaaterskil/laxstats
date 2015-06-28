package laxstats.api.seasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code CreateSeason} represents a command to create a new season.
 */
public class CreateSeason {

   @TargetAggregateIdentifier
   private final SeasonId seasonId;
   private final SeasonDTO seasonDTO;

   /**
    * Creates the command with the given aggregate identifier and data.
    *
    * @param seasonId
    * @param seasonDTO
    */
   public CreateSeason(SeasonId seasonId, SeasonDTO seasonDTO) {
      this.seasonId = seasonId;
      this.seasonDTO = seasonDTO;
   }

   /**
    * Returns the aggregate identifier of the season to create.
    *
    * @return
    */
   public SeasonId getSeasonId() {
      return seasonId;
   }

   /**
    * Returns the data to create a new season.
    *
    * @return
    */
   public SeasonDTO getSeasonDTO() {
      return seasonDTO;
   }
}
