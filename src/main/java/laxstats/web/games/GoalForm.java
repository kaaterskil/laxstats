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

public class GoalForm extends AbstractPlayForm implements GoalResource {
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
    * {@inheritDoc}
    */
   @Override
   public String getElapsedTime() {
      return elapsedTime.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setElapsedTime(String elapsedTime) {
      this.elapsedTime = Period.parse(elapsedTime);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Period getElapsedTimeAsPeriod() {
      return elapsedTime;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getScorerId() {
      return scorerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setScorerId(String scorerId) {
      this.scorerId = scorerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getAssistId() {
      return assistId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAssistId(String assistId) {
      this.assistId = assistId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAttemptType(ScoreAttemptType attemptType) {
      if (attemptType == null) {
         attemptType = ScoreAttemptType.REGULAR;
      }
      this.attemptType = attemptType;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getComments() {
      return comments;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
