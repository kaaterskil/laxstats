package laxstats.web.games;

import laxstats.api.games.PlayResult;
import laxstats.api.games.ScoreAttemptType;

public interface ShotResource extends PlayResource {

   /**
    * Returns the identifier of the associated player.
    *
    * @return
    */
   public String getPlayerId();

   /**
    * Sets the identifier of the associated player. Must not be null.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId);

   /**
    * Returns the shot attempt type. Never null.
    *
    * @return
    */
   public ScoreAttemptType getAttemptType();

   /**
    * Sets the shot attempt type. Defaults to ScoreAttemptType.REGULAR.
    *
    * @param attemptType
    */
   public void setAttemptType(ScoreAttemptType attemptType);

   /**
    * Returns the play result. Never null.
    *
    * @return
    */
   public PlayResult getResult();

   /**
    * Sets the play result. Must not be null.
    *
    * @param result
    */
   public void setResult(PlayResult result);

}
