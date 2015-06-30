package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerDTO;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code Edit Player} represents a command to update the state of a player on a team roster.
 */
public class EditPlayer {
   @TargetAggregateIdentifier
   private final TeamSeasonId teamSeasonId;
   private final PlayerDTO playerDTO;

   /**
    * Creates an {@code EditPlayer} command with the given aggregate Identifier and information with
    * which to update the player.
    *
    * @param teamSeasonId
    * @param playerDTO
    */
   public EditPlayer(TeamSeasonId teamSeasonId, PlayerDTO playerDTO) {
      this.teamSeasonId = teamSeasonId;
      this.playerDTO = playerDTO;
   }

   /**
    * Returns the aggregate of the team season.
    *
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Returns the data with which to update the player.
    *
    * @return
    */
   public PlayerDTO getPlayerDTO() {
      return playerDTO;
   }
}
