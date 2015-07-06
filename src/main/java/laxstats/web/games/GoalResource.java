package laxstats.web.games;

import laxstats.api.games.ScoreAttemptType;

import org.joda.time.Period;

public interface GoalResource extends PlayResource {

   /**
    * Returns the period of time between the goal and the beginning of the play period, in minutes
    * and seconds. Never null.
    *
    * @return
    */
   public String getElapsedTime();

   /**
    * Sets the period of time between the goal and the beginning of the play period, in minutes and
    * seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(String elapsedTime);

   /**
    * Returns the period of time between the goal and the beginning of the play period, in minutes
    * and seconds. Never null.
    *
    * @return
    */
   public Period getElapsedTimeAsPeriod();

   /**
    * Sets the period of time between the goal and the beginning of the play period, in minutes and
    * seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime);

   /**
    * Returns the identifier of the player who scored the goal. Never null.
    *
    * @return
    */
   public String getScorerId();

   /**
    * Returns the identifier of the player who scored the goal. Must not be null.
    *
    * @param scorerId
    */
   public void setScorerId(String scorerId);

   /**
    * Returns the identifier of the player who assisted the goal, or null.
    *
    * @return
    */
   public String getAssistId();

   /**
    * Sets the identifier of the player who assisted the goal. Use null for none.
    *
    * @param assistId
    */
   public void setAssistId(String assistId);

   /**
    * Returns the score attempt type. Never null.
    *
    * @return
    */
   public ScoreAttemptType getAttemptType();

   /**
    * Sets the score attempt type. Defaults to ScoreAttemptType.REGULAR.
    *
    * @param attemptType
    */
   public void setAttemptType(ScoreAttemptType attemptType);

   /**
    * Returns comments, or null.
    *
    * @return
    */
   public String getComments();

   /**
    * Sets a comment. Use null for none.
    *
    * @param comments
    */
   public void setComments(String comments);

}
