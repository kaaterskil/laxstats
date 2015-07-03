package laxstats.api.games;

/**
 * {@code UpdateClear} represents a command to change the state of an existing clear.
 */
public class UpdateClear extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates an {@code UpdateClear} command with the given aggregate identifier, play key and
    * updated clear information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public UpdateClear(GameId gameId, String playId, PlayDTO playDTO) {
      super(gameId, playId);
      this.playDTO = playDTO;
   }

   /**
    * Returns information with which to update the play.
    * 
    * @return
    */
   public PlayDTO getPlayDTO() {
      return playDTO;
   }
}
