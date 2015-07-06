package laxstats.web.games;

import org.joda.time.Period;

public interface FaceOffResource extends PlayResource {

   /**
    * Returns the period of time between the face-off and he beginning of the play period, in
    * minutes and seconds. Never null.
    *
    * @return
    */
   public String getElapsedTime();

   /**
    * Sets the period of time between the face-off and the beginning of the play period, in minutes
    * and seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(String elapsedTime);

   /**
    * Returns the period of time between the face-off and he beginning of the play period, in
    * minutes and seconds. Never null.
    *
    * @return
    */
   public Period getElapsedTimeAsPeriod();

   /**
    * Sets the period of time between the face-off and the beginning of the play period, in minutes
    * and seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime);

   /**
    * Returns the identifier of the player who won the play. Never null.
    *
    * @return
    */
   public String getWinnerId();

   /**
    * Sets the identifier of the player who won the play. Must not be null.
    *
    * @param winnerId
    */
   public void setWinnerId(String winnerId);

   /**
    * Returns the identifier of the player who lost the play. Never null.
    *
    * @return
    */
   public String getLoserId();

   /**
    * Sets the identifier of the player who lost the play. Must not be null.
    *
    * @param loserId
    */
   public void setLoserId(String loserId);

}
