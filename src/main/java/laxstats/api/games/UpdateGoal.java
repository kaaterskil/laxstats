package laxstats.api.games;

/**
 * {@code UpdateGoal} represents a command to change the state of an existing goal.
 */
public class UpdateGoal extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates an {@code UpdateGoal} ommand with the given aggregate identifier, play key and updated
    * goal information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public UpdateGoal(GameId gameId, String playId, PlayDTO playDTO) {
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
