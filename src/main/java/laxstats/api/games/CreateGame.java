package laxstats.api.games;

/**
 * {@code CreateGame} represents a command to create a new game.
 */
public class CreateGame extends AbstractGameCommand {
   private final GameDTO gameDTO;

   /**
    * Creates a {@code CreateGame} command with the given aggregate identifier and game information.
    * 
    * @param gameId
    * @param gameDTO
    */
   public CreateGame(GameId gameId, GameDTO gameDTO) {
      super(gameId);
      this.gameDTO = gameDTO;
   }

   /**
    * Returns information with which to create a new game.
    * 
    * @return
    */
   public GameDTO getEventDTO() {
      return gameDTO;
   }

}
