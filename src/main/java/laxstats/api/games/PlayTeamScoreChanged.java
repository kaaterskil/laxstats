package laxstats.api.games;

/**
 * {@code PlayTeamScoreChanged} represents an event marking a change in the calculation of
 * cumulative goals by a team, primarily resulting the addition of an out-of-sequence play.
 */
public class PlayTeamScoreChanged {
   private final GameId gameId;
   private final String playId;
   private final int teamScore;

   /**
    * Creates a {@code PlayTeamScoreChanged} event with the given aggregate identifier, play key and
    * new team score.
    * 
    * @param gameId
    * @param playId
    * @param teamScore
    */
   public PlayTeamScoreChanged(GameId gameId, String playId, int teamScore) {
      this.gameId = gameId;
      this.playId = playId;
      this.teamScore = teamScore;
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
    * Returns the unique identifier for the play.
    * 
    * @return
    */
   public String getPlayId() {
      return playId;
   }

   /**
    * Returns the new team score.
    * 
    * @return
    */
   public int getTeamScore() {
      return teamScore;
   }
}
