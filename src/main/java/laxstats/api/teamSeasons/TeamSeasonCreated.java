package laxstats.api.teamSeasons;

/**
 * {@code TeamSeasonCreated} represents an event marking the creation of a new team season.
 */
public class TeamSeasonCreated {
   private final TeamSeasonId identifier;
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates a {@code TeamseasonCreated} event with the given aggregate identifier and team season
    * information.
    *
    * @param identifier
    * @param teamSeasonDTO
    */
   public TeamSeasonCreated(TeamSeasonId identifier, TeamSeasonDTO teamSeasonDTO) {
      this.identifier = identifier;
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the aggregate identifier of the new team season.
    *
    * @return
    */
   public TeamSeasonId getIdentifier() {
      return identifier;
   }

   /**
    * Returns the new team season information.
    *
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
