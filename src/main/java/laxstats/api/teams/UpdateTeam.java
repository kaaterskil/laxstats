package laxstats.api.teams;

/**
 * {@code UpdateTeam} represents a command to update the state of an existing team.
 */
public class UpdateTeam extends AbstractTeamCommand {
   private final TeamDTO teamDTO;

   /**
    * Creates an {@code UpdateTeam} command with the given aggregate identifier and updated data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public UpdateTeam(TeamId teamId, TeamDTO teamDTO) {
      super(teamId);
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the data with which to update the team.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
