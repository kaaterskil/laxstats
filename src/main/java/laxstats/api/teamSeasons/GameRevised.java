package laxstats.api.teamSeasons;

import laxstats.api.games.GameDTO;

/**
 * {@code GameRevised} represents an event marking the update of a game.
 */
public class GameRevised {
   private final TeamSeasonId teamSeasonId;
   private final GameDTO gameDTO;

   /**
    * Creates a {@code GameRevised} event with the given team season aggregate identifier and
    * updated game information.
    * 
    * @param teamSeasonId
    * @param gameDTO
    */
   public GameRevised(TeamSeasonId teamSeasonId, GameDTO gameDTO) {
      this.teamSeasonId = teamSeasonId;
      this.gameDTO = gameDTO;
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
    * Returns the updated game information.
    * 
    * @return
    */
   public GameDTO getEventDTO() {
      return gameDTO;
   }
}
