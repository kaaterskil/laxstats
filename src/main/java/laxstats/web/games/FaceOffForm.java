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
public class FaceOffForm extends AbstractPlayForm implements FaceOffResource {
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
   public String getWinnerId() {
      return winnerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setWinnerId(String winnerId) {
      this.winnerId = winnerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getLoserId() {
      return loserId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
