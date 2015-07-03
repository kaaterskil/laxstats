package laxstats.api.games;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code AbstractGameCommand} is the base class of all game commands.
 */
public abstract class AbstractGameCommand {

   @TargetAggregateIdentifier
   private final GameId gameId;

   protected AbstractGameCommand(GameId gameId) {
      this.gameId = gameId;
   }

   /**
    * Returns the game aggregate identifier.
    * 
    * @return
    */
   public GameId getEventId() {
      return gameId;
   }
}
