package laxstats.api.seasons;

/**
 * {@code SeasonUpdated} represents an event marking the update of a season.
 */
public class SeasonUpdated {
   private final SeasonId seasonId;
   private final SeasonDTO seasonDTO;

   /**
    * Creates a {@code SeasonUpdated} event with the given aggregate identifier and updated season
    * information.
    *
    * @param seasonId
    * @param seasonDTO
    */
   public SeasonUpdated(SeasonId seasonId, SeasonDTO seasonDTO) {
      this.seasonId = seasonId;
      this.seasonDTO = seasonDTO;
   }

   /**
    * Returns the aggregate identifier of the updated season.
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
