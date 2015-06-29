package laxstats.api.teams;

/**
 * {@code TeamDeleted} represents an event marking the deletion of an existing team.
 */
public class TeamDeleted {

   private final TeamId teamId;

   /**
    * Creates a {@code TeamDeleted} event with the given aggregate identifier.
    * 
    * @param teamId
    */
   public TeamDeleted(TeamId teamId) {
      this.teamId = teamId;
   }

   /**
    * Returns the aggregate identifier of the deleted team.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }
}
