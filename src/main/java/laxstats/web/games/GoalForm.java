package laxstats.web.games;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.utils.Constants;

import org.joda.time.Period;
import org.springframework.format.annotation.DateTimeFormat;

public class GoalForm extends AbstractPlayForm {
   @NotNull
   @DateTimeFormat(pattern = Constants.PATTERN_ELAPSED_TIME_FORMAT)
   private Period elapsedTime;

   @NotNull
   private String scorerId;

   private String assistId;

   @NotNull
   private ScoreAttemptType attemptType = ScoreAttemptType.REGULAR;

   private String comments;

   private List<ScoreAttemptType> attemptTypes;

   /**
    * Creates a {@code GoalForm}.
    */
   public GoalForm() {
      super(PlayType.GOAL, PlayKey.GOAL);
   }

   /**
    * Returns the period of time between the goal and the beginning of the play period, in minutes
    * ans seconds. Never null.
    *
    * @return
    */
   public Period getElapsedTime() {
      return elapsedTime;
   }

   /**
    * Sets the period of time between the goal and the beginning of the play period, in minutes and
    * seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * Returns the identifier of the player who scored the goal. Never null.
    *
    * @return
    */
   public String getScorerId() {
      return scorerId;
   }

   /**
    * Returns the identifier of the player who scored the goal. Must not be null.
    *
    * @param scorerId
    */
   public void setScorerId(String scorerId) {
      this.scorerId = scorerId;
   }

   /**
    * Returns the identifier of the player who assisted the goal, or null.
    *
    * @return
    */
   public String getAssistId() {
      return assistId;
   }

   /**
    * Sets the identifier of the player who assisted the goal. Use null for none.
    *
    * @param assistId
    */
   public void setAssistId(String assistId) {
      this.assistId = assistId;
   }

   /**
    * Returns the score attempt type. Never null.
    *
    * @return
    */
   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   /**
    * Sets the score attempt type. Defaults to ScoreAttemptType.REGULAR.
    *
    * @param attemptType
    */
   public void setAttemptType(ScoreAttemptType attemptType) {
      if (attemptType == null) {
         attemptType = ScoreAttemptType.REGULAR;
      }
      this.attemptType = attemptType;
   }

   /**
    * Returns comments, or null.
    *
    * @return
    */
   public String getComments() {
      return comments;
   }

   /**
    * Sets a comment. Use null for none.
    *
    * @param comments
    */
   public void setComments(String comments) {
      this.comments = comments;
   }

   /*---------- Drop-down Menus ----------*/

   public List<ScoreAttemptType> getAttemptTypes() {
      if (attemptTypes == null) {
         attemptTypes = Arrays.asList(ScoreAttemptType.values());
      }
      return attemptTypes;
   }
}
