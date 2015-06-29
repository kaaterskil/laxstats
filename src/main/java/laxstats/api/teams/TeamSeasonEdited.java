package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

/**
 * {@code TeamSeasonEdited} represents an event marking the update to an existing team season.
 */
public class TeamSeasonEdited {
   private final TeamId teamId;
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates a {@code TeamSeasonEdited} event with the given aggregate identifier and updated data.
    * 
    * @param teamId
    * @param teamSeasonDTO
    */
   public TeamSeasonEdited(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
      this.teamId = teamId;
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the aggregate identifier of the updated team.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the updated team season data.
    * 
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
