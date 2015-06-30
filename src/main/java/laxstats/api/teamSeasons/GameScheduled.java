package laxstats.api.teamSeasons;

import laxstats.api.games.GameDTO;

/**
 * {@code GameScheduled} represents an event marking the scheduling of a game.
 */
public class GameScheduled {
   private final TeamSeasonId teamSeasonId;
   private final GameDTO event;

   /**
    * Creates a {@code GameScheduled} event with the aggregate identifier of the team season and
    * game information.
    * 
    * @param teamSeasonId
    * @param event
    */
   public GameScheduled(TeamSeasonId teamSeasonId, GameDTO event) {
      this.teamSeasonId = teamSeasonId;
      this.event = event;
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
    * Returns the game information.
    * 
    * @return
    */
   public GameDTO getEvent() {
      return event;
   }
}
