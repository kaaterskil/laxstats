package laxstats.api.teamSeasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code UpdateTeamSeason} represents a command to update the state of an existing team season.
 */
public class UpdateTeamSeason {
   @TargetAggregateIdentifier
   private final TeamSeasonId teamSeasonId;
   private final TeamSeasonDTO teamSeasonDTO;

   /**
    * Creates an {@code UpdateTeamSeason} command with the given aggregate identifier and
    * information to update.
    *
    * @param teamSeasonId
    * @param teamSeasonDTO
    */
   public UpdateTeamSeason(TeamSeasonId teamSeasonId, TeamSeasonDTO teamSeasonDTO) {
      this.teamSeasonId = teamSeasonId;
      this.teamSeasonDTO = teamSeasonDTO;
   }

   /**
    * Returns the aggregate identifier of the team season to update.
    * 
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Returns the data with which to update the team season.
    * 
    * @return
    */
   public TeamSeasonDTO getTeamSeasonDTO() {
      return teamSeasonDTO;
   }
}
