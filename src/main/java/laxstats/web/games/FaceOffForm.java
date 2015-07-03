package laxstats.web.games;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;
import laxstats.api.utils.Constants;
import laxstats.query.games.AttendeeEntry;

import org.joda.time.Period;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * {@code FaceOffForm} contains user-defined information with which to create and update a face-off
 * play.
 */
public class FaceOffForm extends AbstractPlayForm {
   @NotNull
   @DateTimeFormat(pattern = Constants.PATTERN_ELAPSED_TIME_FORMAT)
   private Period elapsedTime;

   @NotNull
   private String winnerId;

   @NotNull
   private String loserId;

   private Map<String, List<AttendeeEntry>> winners;
   private Map<String, List<AttendeeEntry>> losers;

   /**
    * Creates a {@code FaceOffForm}.
    */
   public FaceOffForm() {
      super(PlayType.FACEOFF, PlayKey.PLAY);
   }

   /**
    * Returns the period of time between the face-off and he beginning of the play period, in
    * minutes and seconds. Never null.
    *
    * @return
    */
   public Period getElapsedTime() {
      return elapsedTime;
   }

   /**
    * Sets the period of time between the face-off and the beginning of the play period, in minutes
    * and seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * Returns the identifier of the player who won the play. Never null.
    *
    * @return
    */
   public String getWinnerId() {
      return winnerId;
   }

   /**
    * Sets the identifier of the player who won the play. Must not be null.
    *
    * @param winnerId
    */
   public void setWinnerId(String winnerId) {
      this.winnerId = winnerId;
   }

   /**
    * Returns the identifier of the player who lost the play. Never null.
    *
    * @return
    */
   public String getLoserId() {
      return loserId;
   }

   /**
    * Sets the identifier of the player who lost the play. Must not be null.
    *
    * @param loserId
    */
   public void setLoserId(String loserId) {
      this.loserId = loserId;
   }

   /*---------- Drop down menu options ----------*/

   public Map<String, List<AttendeeEntry>> getWinners() {
      return winners;
   }

   public void setWinners(Map<String, List<AttendeeEntry>> winners) {
      this.winners = winners;
   }

   public Map<String, List<AttendeeEntry>> getLosers() {
      return losers;
   }

   public void setLosers(Map<String, List<AttendeeEntry>> losers) {
      this.losers = losers;
   }

}
