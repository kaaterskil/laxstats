package laxstats.api.teams;

/**
 * {@code CreateTeamPassword} represents a command to create a new password for a team.
 */
public class CreateTeamPassword extends AbstractTeamCommand {
   private final TeamDTO teamDTO;

   /**
    * Creates a {@code CreateTeamPassword} command with the given aggregate identifier and data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public CreateTeamPassword(TeamId teamId, TeamDTO teamDTO) {
      super(teamId);
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the data to create a new password.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
