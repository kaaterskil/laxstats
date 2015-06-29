package laxstats.api.teams;

import laxstats.api.teamSeasons.TeamSeasonDTO;

/**
 * {@code RegisterTeamSeason} represents a command to add a season to an existing team.
 */
public class RegisterTeamSeason extends AbstractTeamCommand {
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates a {@code RegisterTeamSeason} command with the given aggregate identifier and team
    * season information.
    * 
    * @param teamId
    * @param teamSeasonDTO
    */
   public RegisterTeamSeason(TeamId teamId, TeamSeasonDTO teamSeasonDTO) {
      super(teamId);
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the data with which to add a season to the team.
    * 
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
