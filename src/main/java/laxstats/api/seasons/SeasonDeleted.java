package laxstats.api.seasons;

/**
 * {@code SeasonDeleted} represents an event marking the deletion of a season.
 */
public class SeasonDeleted {
   private final SeasonId seasonId;

   /**
    * Creates a {@code SeasonDeleted} event with the given aggregate identifier.
    * 
    * @param seasonId
    */
   public SeasonDeleted(SeasonId seasonId) {
      this.seasonId = seasonId;
   }

   /**
    * Returns the aggregate identifier of the deleted season.
    * 
    * @return
    */
   public SeasonId getSeasonId() {
      return seasonId;
   }
}
