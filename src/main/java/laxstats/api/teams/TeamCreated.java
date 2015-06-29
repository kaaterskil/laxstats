package laxstats.api.teams;

/**
 * {@code TeamCreated} represents an event marking the creation of a team.
 */
public class TeamCreated {
   private final TeamId teamId;
   private final TeamDTO teamDTO;

   /**
    * Creates a {@code TeamCreated} event with the given aggregate identifier and new team data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public TeamCreated(TeamId teamId, TeamDTO teamDTO) {
      this.teamId = teamId;
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the aggregate identifier of the new team.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the new team data.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
