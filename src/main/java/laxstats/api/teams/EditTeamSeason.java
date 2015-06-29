package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

/**
 * {@code EditTeamSeason} represents a command to update an existing team's season.
 */
public class EditTeamSeason extends AbstractTeamCommand {
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates a {@code EditTeamSeason} command with the given aggregate identifier and update data.
    * 
    * @param teamId
    * @param teamSeasonDTO
    */
   public EditTeamSeason(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
      super(teamId);
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the data with which to update the team season.
    * 
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
