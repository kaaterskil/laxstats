package laxstats.api.games;

/**
 * {@code AbstratPlayEvent} is the base class of all play events.
 */
public abstract class AbstractPlayEvent {
   protected GameId gameId;
   protected final String playId;
   protected final PlayDTO playDTO;

   protected AbstractPlayEvent(GameId gameId, String playId, PlayDTO playDTO) {
      this.gameId = gameId;
      this.playId = playId;
      this.playDTO = playDTO;
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

   /**
    * Returns information about the play.
    *
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
