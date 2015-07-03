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
public class PenaltyForm extends AbstractPlayForm {
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
    * Returns the period of time between the violation and the beginning of the period, in minutes
    * and seconds. Never null.
    *
    * @return
    */
   public Period getElapsedTime() {
      return elapsedTime;
   }

   /**
    * Sets the period of time between the violation and the beginning of the period, in minutes and
    * seconds. Must not be null.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * Returns the identifier of the player who committed the violation. Never null.
    *
    * @return
    */
   public String getCommittedById() {
      return committedById;
   }

   /**
    * Sets the identifier of the player who committed the violation. Must not be null.
    *
    * @param committedById
    */
   public void setCommittedById(String committedById) {
      this.committedById = committedById;
   }

   /**
    * Returns the identifier of the player against whom the violation was committed, or null.
    *
    * @return
    */
   public String getCommittedAgainstId() {
      return committedAgainstId;
   }

   /**
    * Sets the identifier of the player against whom the violation ws committed. Use null for
    * unknown.
    *
    * @param committedAgainstId
    */
   public void setCommittedAgainstId(String committedAgainstId) {
      this.committedAgainstId = committedAgainstId;
   }

   /**
    * Returns the identifier of the associated violation. Never null.
    *
    * @return
    */
   public String getViolationId() {
      return violationId;
   }

   /**
    * Sets the identifer of the associated violation. Must not be null.
    *
    * @param violationId
    */
   public void setViolationId(String violationId) {
      this.violationId = violationId;
   }

   /**
    * Returns the duration of the assessed penalty as a period of time. Never nll.
    *
    * @return
    */
   public Period getDuration() {
      return duration;
   }

   /**
    * Sets a period of time as the duration of the penalty. Must not be null.
    *
    * @param duration
    */
   public void setDuration(Period duration) {
      this.duration = duration;
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
