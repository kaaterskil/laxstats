package laxstats.api.games;

/**
 * {@code GameUpdated} represents an event marking a change in the state of an existing game.
 */
public class GameUpdated {
   private final GameId gameId;
   private final GameDTO gameDTO;

   /**
    * Creates a {@code GameUpdated} event with the given aggregate identifier and updated game
    * information.
    *
    * @param gameId
    * @param gameDTO
    */
   public GameUpdated(GameId gameId, GameDTO gameDTO) {
      this.gameId = gameId;
      this.gameDTO = gameDTO;
   }

   /**
    * Returns the game aggregate identifier.
    *
    * @return
    */
   public GameId getEventId() {
      return gameId;
   }

   /**
    * Returns updated game information.
    *
    * @return
    */
   public GameDTO getEventDTO() {
      return gameDTO;
   }
}
