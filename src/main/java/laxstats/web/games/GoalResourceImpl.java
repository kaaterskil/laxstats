package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;
import laxstats.api.games.ScoreAttemptType;

import org.joda.time.Period;

/**
 * {@code GoalResource} represents a goal resource for remote clients.
 */
public class GoalResourceImpl extends AbstractPlayResource implements GoalResource {
   @NotNull
   private String elapsedTime;

   @NotNull
   private String scorerId;

   private String assistId;

   @NotNull
   private ScoreAttemptType attemptType = ScoreAttemptType.REGULAR;

   private String comments;

   /**
    * Creates a {@code GoalResource} from the given information.
    *
    * @param playId
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    * @param elapsedTime
    * @param scorerId
    * @param assistId
    * @param attemptType
    * @param comments
    */
   public GoalResourceImpl(String playId, String gameId, String teamSeasonId, int period,
      String comment, String teamName, String elapsedTime, String scorerId, String assistId,
      ScoreAttemptType attemptType, String comments) {
      super(playId, PlayType.GOAL, PlayKey.GOAL, gameId, teamSeasonId, period, comment, teamName);
      this.elapsedTime = elapsedTime;
      this.scorerId = scorerId;
      this.assistId = assistId;
      this.attemptType = attemptType;
      this.comments = comments;
   }

   /**
    * Creates an empty {@code GoalResource}.
    */
   public GoalResourceImpl() {
      super(PlayType.GOAL, PlayKey.GOAL);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getElapsedTime() {
      return elapsedTime;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setElapsedTime(String elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Period getElapsedTimeAsPeriod() {
      return Period.parse(elapsedTime);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime.toString();
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
}
