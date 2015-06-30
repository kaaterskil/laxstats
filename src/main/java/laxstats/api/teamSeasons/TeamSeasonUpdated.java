package laxstats.api.teamSeasons;

/**
 * {@code TeamSeasonUpdated} represents an event marking the update of an existing team season.
 */
public class TeamSeasonUpdated {
   private final TeamSeasonId identifier;
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates a {@code TeamSeasonUpdated} event with the given aggregate identifier and updated
    * information.
    * 
    * @param identifier
    * @param teamSeasonDTO
    */
   public TeamSeasonUpdated(TeamSeasonId identifier, TeamSeasonDTO teamSeasonDTO) {
      this.identifier = identifier;
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the aggregate identifier of the updated team season.
    * 
    * @return
    */
   public TeamSeasonId getIdentifier() {
      return identifier;
   }

   /**
    * Returns the updated team season information.
    * 
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
