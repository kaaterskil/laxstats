package laxstats.api.games;

/**
 * {@code UpdateGame} represents a command to change the state of an existing game.
 */
public class UpdateGame extends AbstractGameCommand {
   private final GameDTO gameDTO;

   /**
    * Creates an {@code UpdateGame} command with the given ggregate identifier and updated game
    * information.
    * 
    * @param gameId
    * @param gameDTO
    */
   public UpdateGame(GameId gameId, GameDTO gameDTO) {
      super(gameId);
      this.gameDTO = gameDTO;
   }

   /**
    * Returns information with which to update the game.
    * 
    * @return
    */
   public GameDTO getEventDTO() {
      return gameDTO;
   }

}
