package laxstats.web.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;

public class ShotForm extends AbstractPlayForm {
   private String playerId;
   private ScoreAttemptType attemptType;
   private PlayResult result;
   private List<ScoreAttemptType> attemptTypes;
   private List<PlayResult> results;

   public ShotForm() {
      super(PlayType.SHOT, PlayKey.PLAY);
   }

   /*---------- Getter/Setters ----------*/

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   public void setAttemptType(ScoreAttemptType attemptType) {
      this.attemptType = attemptType;
   }

   public PlayResult getResult() {
      return result;
   }

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
