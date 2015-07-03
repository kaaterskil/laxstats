package laxstats.api.games;

/**
 * {@code PlaySequenceNumberChanged} represents an event marking a re-ordering of the play sequence,
 * primarily the result of adding a play to an existing sequence.
 */
public class PlaySequenceNumberChanged {
   private final GameId gameId;
   private final String playId;
   private final int sequenceNumber;

   /**
    * Creates a {@code PlaySequenceNumberChanged} event with the given aggregate identifier, play
    * key and new sequence number.
    * 
    * @param gameId
    * @param playId
    * @param sequenceNumber
    */
   public PlaySequenceNumberChanged(GameId gameId, String playId, int sequenceNumber) {
      this.gameId = gameId;
      this.playId = playId;
      this.sequenceNumber = sequenceNumber;
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
    * Returns the new sequence number for the play.
    * 
    * @return
    */
   public int getSequenceNumber() {
      return sequenceNumber;
   }
}
