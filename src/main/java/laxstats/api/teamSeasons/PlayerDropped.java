package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerId;

/**
 * {@code PlayerDropped} represents an event marking the removal of a player from a team roster.
 */
public class PlayerDropped {
   private final TeamSeasonId teamSeasonId;
   private final PlayerId playerId;

   /**
    * Creates a {@code PlayerDropped} event with the given aggregate identifiers.
    * 
    * @param teamSeasonId
    * @param playerId
    */
   public PlayerDropped(TeamSeasonId teamSeasonId, PlayerId playerId) {
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
    * Returns the aggregate identifier of the dropped player.
    * 
    * @return
    */
   public PlayerId getPlayerId() {
      return playerId;
   }
}
