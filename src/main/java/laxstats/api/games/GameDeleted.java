package laxstats.api.games;

/**
 * {@code GameDeleted} represents an event marking the deletion of a game.
 */
public class GameDeleted {
   private final GameId gameId;

   /**
    * Creates a {@code GameDeleted} event with the aggregate identifier of the deleted game.
    * 
    * @param gameId
    */
   public GameDeleted(GameId gameId) {
      this.gameId = gameId;
   }

   /**
    * Returns the aggregate identifier of the deleted game.
    * 
    * @return
    */
   public GameId getEventId() {
      return gameId;
   }
}
