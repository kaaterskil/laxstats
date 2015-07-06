package laxstats.web.games;

import org.joda.time.Period;

public interface PenaltyResource extends PlayResource {

   /**
    * Returns the period of time between the violation and the beginning of the period, in minutes
    * and seconds. Never null.
    *
    * @return
    */
   public String getElapsedTime();

   /**
    * Sets the period of time between the violation and the beginning of the period, in minutes and
    * seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(String elapsedTime);

   /**
    * Returns the period of time between the violation and the beginning of the period, in minutes
    * and seconds. Never null.
    *
    * @return
    */
   public Period getElapsedTimeAsPeriod();

   /**
    * Sets the period of time between the violation and the beginning of the period, in minutes and
    * seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime);

   /**
    * Returns the identifier of the player who committed the violation. Never null.
    *
    * @return
    */
   public String getCommittedById();

   /**
    * Sets the identifier of the player who committed the violation. Must not be null.
    *
    * @param committedById
    */
   public void setCommittedById(String committedById);

   /**
    * Returns the identifier of the player against whom the violation was committed, or null.
    *
    * @return
    */
   public String getCommittedAgainstId();

   /**
    * Sets the identifier of the player against whom the violation ws committed. Use null for
    * unknown.
    *
    * @param committedAgainstId
    */
   public void setCommittedAgainstId(String committedAgainstId);

   /**
    * Returns the identifier of the associated violation. Never null.
    *
    * @return
    */
   public String getViolationId();

   /**
    * Sets the identifier of the associated violation. Must not be null.
    *
    * @param violationId
    */
   public void setViolationId(String violationId);

   /**
    * Returns the duration of the assessed penalty as a period of time. Never nll.
    *
    * @return
    */
   public String getDuration();

   /**
    * Sets a period of time as the duration of the penalty. Must not be null.
    *
    * @param duration
    */
   public void setDuration(String duration);

   /**
    * Returns the duration of the assessed penalty as a period of time. Never nll.
    *
    * @return
    */
   public Period getDurationAsPeriod();

   /**
    * Sets a period of time as the duration of the penalty. Must not be null.
    *
    * @param duration
    */
   public void setDuration(Period duration);

}
