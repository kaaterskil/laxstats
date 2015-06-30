package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerDTO;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code RegisterPlayer} represents a command to add a player to a team roster.
 */
public class RegisterPlayer {
   @TargetAggregateIdentifier
   private final TeamSeasonId teamSeasonId;
   private final PlayerDTO playerDTO;

   /**
    * Creates a {@code RegisterPlayer} command with the given aggregate identifier and player
    * information.
    * 
    * @param teamSeasonId
    * @param playerDTO
    */
   public RegisterPlayer(TeamSeasonId teamSeasonId, PlayerDTO playerDTO) {
      this.teamSeasonId = teamSeasonId;
      this.playerDTO = playerDTO;
   }

   /**
    * Returns the aggregate identifier of the team roster.
    * 
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Returns the player information.
    * 
    * @return
    */
   public PlayerDTO getPlayerDTO() {
      return playerDTO;
   }
}
