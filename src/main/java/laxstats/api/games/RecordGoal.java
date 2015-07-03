package laxstats.api.games;

/**
 * {@code RecordGoal} represents a command to create a new goal.
 */
public class RecordGoal extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code RecordGoal} command with the given aggregate identifier, play key and new
    * goal information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public RecordGoal(GameId gameId, String playId, PlayDTO playDTO) {
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
