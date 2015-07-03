package laxstats.api.games;

/**
 * {@code RecordGroundBall} represents a command to create a new ground ball.
 */
public class RecordGroundBall extends AbstractPlayCommand {
   private final PlayDTO playDTO;

   /**
    * Creates a {@code RecordGroundBall} caommdn with the given aggregate identifier, play key and
    * new ground ball information.
    * 
    * @param gameId
    * @param playId
    * @param playDTO
    */
   public RecordGroundBall(GameId gameId, String playId, PlayDTO playDTO) {
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
