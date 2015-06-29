package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

/**
 * {@code TeamSeasonRegistered} represents an event marking the addition of a season to an existing
 * team.
 */
public class TeamSeasonRegistered {
   private final TeamId teamId;
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates a {@code TeamSeasonRegistered} event with the given aggregate identifier and season
    * data.
    * 
    * @param teamId
    * @param teamSeasonDTO
    */
   public TeamSeasonRegistered(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
      this.teamId = teamId;
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the aggregate identifier of the team.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the information for the registered season.
    * 
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
