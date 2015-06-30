package laxstats.api.teamSeasons;

/**
 * {@code TeamSeasonDeleted} represents an event marking the deletion of a team season.
 */
public class TeamSeasonDeleted {
   private final TeamSeasonId teamSeasonId;

   /**
    * Creates a {@code TeamSeasonDeleted} event with the given aggregate identifier.
    * 
    * @param teamSeasonId
    */
   public TeamSeasonDeleted(TeamSeasonId teamSeasonId) {
      this.teamSeasonId = teamSeasonId;
   }

   /**
    * Returns the aggregate identifier of the deleted team season.
    * 
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }
}
