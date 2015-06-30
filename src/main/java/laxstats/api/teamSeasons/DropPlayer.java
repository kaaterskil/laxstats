package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerId;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code DropPlayer} represents a command to remove a player from the team roster.
 */
public class DropPlayer {
   @TargetAggregateIdentifier
   private final TeamSeasonId teamSeasonId;
   private final PlayerId playerId;

   /**
    * Creates a {@code DropPlayer} command with the given aggregate identifiers.
    *
    * @param teamSeasonId
    * @param playerId
    */
   public DropPlayer(TeamSeasonId teamSeasonId, PlayerId playerId) {
      this.teamSeasonId = teamSeasonId;
      this.playerId = playerId;
   }

   /**
    * Returns the aggregate identifier of the team season.
    *
    * @return
    */
   public TeamSeasonId getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Returns the aggregate identifier of the player to drop.
    *
    * @return
    */
   public PlayerId getPlayerId() {
      return playerId;
   }
}
