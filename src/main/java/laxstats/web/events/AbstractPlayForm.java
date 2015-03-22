package laxstats.web.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.api.events.PlayKey;
import laxstats.query.events.AttendeeEntry;

public abstract class AbstractPlayForm {
   private String playId;
   private final String playType;
   private final PlayKey playKey;
   private String gameId;
   private String teamSeasonId;
   private int period = 0;
   private String comment;

   private String teamName;

   private Map<String, String> teams = new HashMap<String, String>();
   private Map<String, String> participants = new HashMap<String, String>();
   private Map<String, List<AttendeeEntry>> attendees = new HashMap<>();

   protected AbstractPlayForm(String playType, PlayKey playKey) {
      this.playType = playType;
      this.playKey = playKey;
   }

   /*---------- Getter/Setters ----------*/

   public String getPlayId() {
      return playId;
   }

   public void setPlayId(String playId) {
      this.playId = playId;
   }

   public String getPlayType() {
      return playType;
   }

   public PlayKey getPlayKey() {
      return playKey;
   }

   public String getGameId() {
      return gameId;
   }

   public void setGameId(String gameId) {
      this.gameId = gameId;
   }

   public String getTeamSeasonId() {
      return teamSeasonId;
   }

   public void setTeamSeasonId(String teamSeasonId) {
      this.teamSeasonId = teamSeasonId;
   }

   public int getPeriod() {
      return period;
   }

   public void setPeriod(int period) {
      this.period = period;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public String getTeamName() {
      return teamName;
   }

   public void setTeamName(String teamName) {
      this.teamName = teamName;
   }

   /*---------- Drop-down menu options ----------*/

   public Map<String, String> getTeams() {
      return teams;
   }

   public void setTeams(Map<String, String> teams) {
      this.teams = teams;
   }

   public Map<String, String> getParticipants() {
      return participants;
   }

   public void setParticipants(Map<String, String> participants) {
      this.participants = participants;
   }

   public Map<String, List<AttendeeEntry>> getAttendees() {
      return attendees;
   }

   public void setAttendees(Map<String, List<AttendeeEntry>> attendees) {
      this.attendees = attendees;
   }

}
