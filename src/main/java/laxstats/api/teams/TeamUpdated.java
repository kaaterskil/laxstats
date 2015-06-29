package laxstats.api.teams;

/**
 * {@code TeamUpdated} represents an event marking a update to the state of an existing team.
 */
public class TeamUpdated {
   private final TeamId teamId;
   private final TeamDTO teamDTO;

   /**
    * Creates a {@code TeamUpdated} event with the given aggregate identifier and updated data.
    * 
    * @param teamId
    * @param teamDTO
    */
   public TeamUpdated(TeamId teamId, TeamDTO teamDTO) {
      this.teamId = teamId;
      this.teamDTO = teamDTO;
   }

   /**
    * Returns the aggregate identifier of the updated team.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the updated team data.
    * 
    * @return
    */
   public TeamDTO getTeamDTO() {
      return teamDTO;
   }

}
