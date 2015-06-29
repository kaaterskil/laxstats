package laxstats.api.teams;

/**
 * {@code TeamPasswordUpdated} represents an event marking the update of a team password.
 */
public class TeamPasswordUpdated {
   private final TeamId teamId;
   private final TeamDTO teamDTO;

   /**
    * Creates a {@code TeamPasswordUpdated} event with the given aggregate identifier and updated
    * data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public TeamPasswordUpdated(TeamId teamId, TeamDTO teamDTO) {
      this.teamId = teamId;
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the aggregate identifier of the team to update.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the updated data.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
