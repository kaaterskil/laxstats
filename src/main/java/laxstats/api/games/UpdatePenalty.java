package laxstats.api.games;

/**
 * {@code UpdatePenalty} represents a command to change the state of an existing penalty.
 */
public class UpdatePenalty extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates an {@code UpdatePenalty} command with the given aggregate identifier, play key and
    * updated penalty information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public UpdatePenalty(GameId gameId, String playId, PlayDTO playDTO) {
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
