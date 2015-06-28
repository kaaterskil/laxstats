package laxstats.web.games;

import java.util.Arrays;
import java.util.List;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;

import org.joda.time.Period;

public class GoalForm extends AbstractPlayForm {
   private Period elapsedTime;
   private String scorerId;
   private String assistId;
   private ScoreAttemptType attemptType;
   private String comments;
   private List<ScoreAttemptType> attemptTypes;

   public GoalForm() {
      super(PlayType.GOAL, PlayKey.GOAL);
   }

   /*---------- Getter/Setters ----------*/

   public Period getElapsedTime() {
      return elapsedTime;
   }

   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   public String getScorerId() {
      return scorerId;
   }

   public void setScorerId(String scorerId) {
      this.scorerId = scorerId;
   }

   public String getAssistId() {
      return assistId;
   }

   public void setAssistId(String assistId) {
      this.assistId = assistId;
   }

   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   public void setAttemptType(ScoreAttemptType attemptType) {
      this.attemptType = attemptType;
   }

   public String getComments() {
      return comments;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }

   public List<ScoreAttemptType> getAttemptTypes() {
      if (attemptTypes == null) {
         attemptTypes = Arrays.asList(ScoreAttemptType.values());
      }
      return attemptTypes;
   }
}