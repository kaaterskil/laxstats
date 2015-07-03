package laxstats.api.games;

/**
 * {@code GameCreated} represents an event marking the creation of a new game.
 */
public class GameCreated {
   private final GameId gameId;
   private final GameDTO gameDTO;

   /**
    * Creates a {@code GameCreated} evnet with the given aggregate identifier and information about
    * the new game.
    *
    * @param gameId
    * @param gameDTO
    */
   public GameCreated(GameId gameId, GameDTO gameDTO) {
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
    * Returns information about the newly created game.
    * 
    * @return
    */
   public GameDTO getEventDTO() {
      return gameDTO;
   }
}
