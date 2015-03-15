package laxstats.web.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;
import laxstats.query.events.AttendeeEntry;

import org.joda.time.Period;
import org.springframework.format.annotation.DateTimeFormat;

public class PenaltyForm extends AbstractPlayForm {
   @DateTimeFormat(pattern = "mm:ss")
   private Period elapsedTime;

   private String committedById;
   private String committedAgainstId;
   private String violationId;
   private Period duration;
   private Map<String, String> violationData = new HashMap<>();
   private Map<String, List<AttendeeEntry>> violators;
   private Map<String, List<AttendeeEntry>> opponents;

   public PenaltyForm() {
      super(PlayType.PENALTY, PlayKey.PLAY);
   }

   /*---------- Getter/Setters ----------*/

   public Period getElapsedTime() {
      return elapsedTime;
   }

   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   public String getCommittedById() {
      return committedById;
   }

   public void setCommittedById(String committedById) {
      this.committedById = committedById;
   }

   public String getCommittedAgainstId() {
      return committedAgainstId;
   }

   public void setCommittedAgainstId(String committedAgainstId) {
      this.committedAgainstId = committedAgainstId;
   }

   public String getViolationId() {
      return violationId;
   }

   public void setViolationId(String violationId) {
      this.violationId = violationId;
   }

   public Period getDuration() {
      return duration;
   }

   public void setDuration(Period duration) {
      this.duration = duration;
   }

   public Map<String, String> getViolationData() {
      return violationData;
   }

   public void setViolationData(Map<String, String> violationData) {
      this.violationData = violationData;
   }

   /*---------- Drop down menu options ----------*/

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
