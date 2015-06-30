package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerDTO;

/**
 * {@code PlayerEdited} represents an event marking the update of a player on a team roster.
 */
public class PlayerEdited {
   private final TeamSeasonId teamSeasonId;
   private final PlayerDTO playerDTO;

   /**
    * Creates a {@code PlayerEdited} event with the given aggregate identifier and updated player
    * information.
    * 
    * @param teamSeasonId
    * @param playerDTO
    */
   public PlayerEdited(TeamSeasonId teamSeasonId, PlayerDTO playerDTO) {
      this.teamSeasonId = teamSeasonId;
      this.playerDTO = playerDTO;
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
    * Returns the updated layer information.
    * 
    * @return
    */
   public PlayerDTO getPlayerDTO() {
      return playerDTO;
   }
}
