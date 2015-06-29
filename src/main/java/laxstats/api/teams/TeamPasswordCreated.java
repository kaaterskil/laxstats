package laxstats.api.teams;

/**
 * {@code TeamPasswordCreated} represents an event marking the creation of a new team password.
 */
public class TeamPasswordCreated {
   private final TeamId teamId;
   private final TeamDTO teamDTO;

   /**
    * Creates a {@code Create TeamPassword} event with the given aggregate identifier and data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public TeamPasswordCreated(TeamId teamId, TeamDTO teamDTO) {
      this.teamId = teamId;
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the aggregate identifier of the team.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the data with which to create a new password.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
