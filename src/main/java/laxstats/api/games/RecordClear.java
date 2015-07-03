package laxstats.api.games;

/**
 * {@code RecordClear} represents a command to create a new clear.
 */
public class RecordClear extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code RecordClear} command with the given aggregate identifier, play key and
    * information about the clear.
    *
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public RecordClear(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns information with which to create a new play.
    *
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
