package laxstats.api.seasons;

/**
 * {@code SeasonCreated} represents an event marking the creation of a season.
 */
public class SeasonCreated {
   private final SeasonId seasonId;
   private final SeasonDTO seasonDTO;

   /**
    * Creates a {@code SeasonCreated} event with the given aggregate identifier and season
    * information.
    *
    * @param seasonId
    * @param seasonDTO
    */
   public SeasonCreated(SeasonId seasonId, SeasonDTO seasonDTO) {
      this.seasonId = seasonId;
      this.seasonDTO = seasonDTO;
   }

   /**
    * Returns the aggregate identifier of the created season.
    *
    * @return
    */
   public SeasonId getSeasonId() {
      return seasonId;
   }

   /**
    * Returns the season information.
    *
    * @return
    */
   public SeasonDTO getSeasonDTO() {
      return seasonDTO;
   }
}
