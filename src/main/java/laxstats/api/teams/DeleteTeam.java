package laxstats.api.teams;

/**
 * {@code DeleteTeam} represents a command to delete an existing team.
 */
public class DeleteTeam extends AbstractTeamCommand {

   /**
    * Creates a {@code DeleteTeam} command with the given aggregate identifier.
    * 
    * @param teamId
    */
   public DeleteTeam(TeamId teamId) {
      super(teamId);
   }
}
