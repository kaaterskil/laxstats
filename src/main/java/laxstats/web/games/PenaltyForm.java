package laxstats.web.games;

import java.util.HashMap;
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
 * {@code PenaltyForm} contains user-defined information with which to create and update penalty
 * plays.
 */
public class PenaltyForm extends AbstractPlayForm implements PenaltyResource {
   @NotNull
   @DateTimeFormat(pattern = Constants.PATTERN_ELAPSED_TIME_FORMAT)
   private Period elapsedTime;

   @NotNull
   private String committedById;

   private String committedAgainstId;

   @NotNull
   private String violationId;

   @NotNull
   private Period duration;

   private Map<String, String> violationData = new HashMap<>();
   private Map<String, List<AttendeeEntry>> violators;
   private Map<String, List<AttendeeEntry>> opponents;

   /**
    * Creates a {@code PenaltyForm}.
    */
   public PenaltyForm() {
      super(PlayType.PENALTY, PlayKey.PLAY);
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
   public String getCommittedById() {
      return committedById;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCommittedById(String committedById) {
      this.committedById = committedById;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getCommittedAgainstId() {
      return committedAgainstId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCommittedAgainstId(String committedAgainstId) {
      this.committedAgainstId = committedAgainstId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getViolationId() {
      return violationId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setViolationId(String violationId) {
      this.violationId = violationId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Period getDurationAsPeriod() {
      return duration;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDuration(Period duration) {
      this.duration = duration;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getDuration() {
      return duration.toString();
   }

   @Override
   public void setDuration(String duration) {
      this.duration = Period.parse(duration);
   }

   /*---------- Drop down menu options ----------*/

   public Map<String, String> getViolationData() {
      return violationData;
   }

   public void setViolationData(Map<String, String> violationData) {
      this.violationData = violationData;
   }

   public Map<String, List<AttendeeEntry>> getViolators() {
      return violators;
   }

   public void setViolators(Map<String, List<AttendeeEntry>> violators) {
      this.violators = violators;
   }

   public Map<String, List<AttendeeEntry>> getOpponents() {
      return opponents;
   }

   public void setOpponents(Map<String, List<AttendeeEntry>> opponents) {
      this.opponents = opponents;
   }

}
