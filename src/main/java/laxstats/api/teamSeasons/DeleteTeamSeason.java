package laxstats.api.teamSeasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code DeleteTeamSeason} represents a command to delete an existing team season.
 */
public class DeleteTeamSeason {
   @TargetAggregateIdentifier
   private final TeamSeasonId teamSeasonId;

   /**
    * Creates a {@code DeleteTeamSeason} command with the given aggregate identifier.
    * 
    * @param teamSeasonId
    */
   public DeleteTeamSeason(TeamSeasonId teamSeasonId) {
      this.teamSeasonId = teamSeasonId;
   }

   /**
    * Returns the aggregate identifier of the team season to delete.
    * 
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }
}
