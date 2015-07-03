package laxstats.api.games;

/**
 * {@code DeleteGame} represents a command to delete a game.
 */
public class DeleteGame extends AbstractGameCommand {

   /**
    * Creates a {@code DeleteGame} command with the given aggregate identifier.
    * 
    * @param gameId
    */
   public DeleteGame(GameId gameId) {
      super(gameId);
   }
}
