package laxstats.api.teams;

/**
 * {@code UpdateTeamPassword} represents a command to update an existing team password.
 */
public class UpdateTeamPassword extends AbstractTeamCommand {
   private final TeamDTO teamDTO;

   /**
    * Creates an {@code UpdateTeamPassword} command with the given aggregate identifier and updated
    * data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public UpdateTeamPassword(TeamId teamId, TeamDTO teamDTO) {
      super(teamId);
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the data with which to update the team's password.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
