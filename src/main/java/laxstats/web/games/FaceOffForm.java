package laxstats.web.games;

import java.util.List;
import java.util.Map;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;
import laxstats.query.games.AttendeeEntry;

import org.joda.time.Period;
import org.springframework.format.annotation.DateTimeFormat;

public class FaceOffForm extends AbstractPlayForm {
   @DateTimeFormat(pattern = "mm:ss")
   private Period elapsedTime;

   private String winnerId;
   private String loserId;
   private Map<String, List<AttendeeEntry>> winners;
   private Map<String, List<AttendeeEntry>> losers;

   public FaceOffForm() {
      super(PlayType.FACEOFF, PlayKey.PLAY);
   }

   /*---------- Getter/Setters ----------*/

   public Period getElapsedTime() {
      return elapsedTime;
   }

   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   public String getWinnerId() {
      return winnerId;
   }

   public void setWinnerId(String winnerId) {
      this.winnerId = winnerId;
   }

   public String getLoserId() {
      return loserId;
   }

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
