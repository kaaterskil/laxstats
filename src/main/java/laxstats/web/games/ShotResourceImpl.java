package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;

/**
 * {@code ShotResource} represents a shot resource for remote clients.
 */
public class ShotResourceImpl extends AbstractPlayResource implements ShotResource {

   @NotNull
   private String playerId;

   @NotNull
   private ScoreAttemptType attemptType = ScoreAttemptType.REGULAR;

   @NotNull
   private PlayResult result;

   /**
    * Creates a {@code ShotResource} from the given information.
    *
    * @param playId
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    * @param playerId
    * @param attemptType
    * @param result
    */
   public ShotResourceImpl(String playId, String gameId, String teamSeasonId, int period,
      String comment, String teamName, String playerId, ScoreAttemptType attemptType,
      PlayResult result) {
      super(playId, PlayType.SHOT, PlayKey.PLAY, gameId, teamSeasonId, period, comment, teamName);
      this.playerId = playerId;
      this.attemptType = attemptType;
      this.result = result;
   }

   /**
    * Creates an empty {@code ShotResource}.
    */
   public ShotResourceImpl() {
      super(PlayType.SHOT, PlayKey.PLAY);
   }

   /**
    * Returns the identifier of the associated player.
    *
    * @return
    */
   @Override
   public String getPlayerId() {
      return playerId;
   }

   /**
    * Sets the identifier of the associated player. Must not be null.
    *
    * @param playerId
    */
   @Override
   public void setPlayerId(String playerId) {
      assert playerId != null;
      this.playerId = playerId;
   }

   /**
    * Returns the shot attempt type. Never null.
    *
    * @return
    */
   @Override
   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   /**
    * Sets the shot attempt type. Defaults to ScoreAttemptType.REGULAR.
    *
    * @param attemptType
    */
   @Override
   public void setAttemptType(ScoreAttemptType attemptType) {
      assert attemptType != null;
      this.attemptType = attemptType;
   }

   /**
    * Returns the play result. Never null.
    *
    * @return
    */
   @Override
   public PlayResult getResult() {
      return result;
   }

   /**
    * Sets the play result. Must not be null.
    *
    * @param result
    */
   @Override
   public void setResult(PlayResult result) {
      assert result != null;
      this.result = result;
   }
}
