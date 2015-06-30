package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerDTO;

/**
 * {@code PlayerRegistered} represents an event marking the addition of a player on a team roster.
 */
public class PlayerRegistered {
   private final TeamSeasonId teamSeasonId;
   private final PlayerDTO playerDTO;

   /**
    * Creates a {@code PlayerRegistered} event with the given aggregate identifier and player
    * information.
    * 
    * @param teamSeasonId
    * @param playerDTO
    */
   public PlayerRegistered(TeamSeasonId teamSeasonId, PlayerDTO playerDTO) {
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
