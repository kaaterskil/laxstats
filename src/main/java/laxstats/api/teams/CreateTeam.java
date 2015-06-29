package laxstats.api.teams;

/**
 * {@code CreateTeam} represents a command to create and persist a new team.
 */
public class CreateTeam extends AbstractTeamCommand {
   private final TeamDTO teamDTO;

   /**
    * Creates a {@code CreateTeam} command with the given aggregate identifier and data.
    *
    * @param teamId
    * @param teamDTO
    */
   public CreateTeam(TeamId teamId, TeamDTO teamDTO) {
      super(teamId);
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the data for a new team.
    *
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
