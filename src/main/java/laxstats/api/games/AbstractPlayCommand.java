package laxstats.api.games;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code AbstractPlayCommand} is the base class of all play commands.
 */
abstract public class AbstractPlayCommand {

   @TargetAggregateIdentifier
   protected GameId gameId;

   protected String playId;

   protected AbstractPlayCommand(GameId gameId, String playId) {
      this.gameId = gameId;
      this.playId = playId;
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
    * Returns the unique identifier of the play.
    *
    * @return
    */
   public String getPlayId() {
      return playId;
   }
}
