package laxstats.web.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;

/**
 * {@code ShotForm} contains user-defined information with which to create and update shot plays.
 */
public class ShotForm extends AbstractPlayForm {

   @NotNull
   private String playerId;
   @NotNull
   private ScoreAttemptType attemptType = ScoreAttemptType.REGULAR;
   @NotNull
   private PlayResult result;

   private List<ScoreAttemptType> attemptTypes;
   private List<PlayResult> results;

   /**
    * Creates a {@code ShotForm}.
    */
   public ShotForm() {
      super(PlayType.SHOT, PlayKey.PLAY);
   }

   /**
    * Returns the identifier of the associated player.
    *
    * @return
    */
   public String getPlayerId() {
      return playerId;
   }

   /**
    * Sets the identifier of the associated player. Must not be null.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   /**
    * Returns the shot attempt type. Never null.
    *
    * @return
    */
   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   /**
    * Sets the shot attempt type. Defaults to ScoreAttemptType.REGULAR.
    *
    * @param attemptType
    */
   public void setAttemptType(ScoreAttemptType attemptType) {
      this.attemptType = attemptType;
   }

   /**
    * Returns the play result. Never null.
    *
    * @return
    */
   public PlayResult getResult() {
      return result;
   }

   /**
    * Sets the play result. Must not be null.
    *
    * @param result
    */
   public void setResult(PlayResult result) {
      this.result = result;
   }

   /*---------- Drop down menu options ----------*/

   public List<ScoreAttemptType> getAttemptTypes() {
      if (attemptTypes == null) {
         attemptTypes = Arrays.asList(ScoreAttemptType.values());
      }
      return attemptTypes;
   }

   public List<PlayResult> getResults() {
      if (results == null) {
         results = new ArrayList<PlayResult>();
         for (final PlayResult each : Arrays.asList(PlayResult.values())) {
            if (each.getPlayKey().equals(PlayKey.PLAY)) {
               results.add(each);
            }
         }
      }
      return results;
   }
}
