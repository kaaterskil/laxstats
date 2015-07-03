package laxstats.web.games;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.query.games.AttendeeEntry;

public abstract class AbstractPlayForm {
   private String playId;
   private final String playType;
   private final PlayKey playKey;
   @NotNull
   private String gameId;
   @NotNull
   private String teamSeasonId;
   private int period = 0;
   private String comment;

   private String teamName;

   private Map<String, String> teams = new HashMap<String, String>();
   private Map<String, String> participants = new HashMap<String, String>();
   private Map<String, List<AttendeeEntry>> attendees = new HashMap<>();

   /**
    * Creates an {@code AbstractPlayForm} with the given class type and category.
    *
    * @param playType
    * @param playKey
    */
   protected AbstractPlayForm(String playType, PlayKey playKey) {
      this.playType = playType;
      this.playKey = playKey;
   }

   /**
    * Returns the play's unique identifier.
    *
    * @return
    */
   public String getPlayId() {
      return playId;
   }

   /**
    * Sets the play's unique identifier.
    *
    * @param playId
    */
   public void setPlayId(String playId) {
      this.playId = playId;
   }

   /**
    * Returns the play class type.
    *
    * @return
    */
   public String getPlayType() {
      return playType;
   }

   /**
    * Returns the major ply category. Never null.
    *
    * @return
    */
   public PlayKey getPlayKey() {
      return playKey;
   }

   /**
    * Returns the identifier of the associated game. Never null.
    *
    * @return
    */
   public String getGameId() {
      return gameId;
   }

   /**
    * Sets the identifier of the associated game. Must not be null.
    *
    * @param gameId
    */
   public void setGameId(String gameId) {
      this.gameId = gameId;
   }

   /**
    * Returns the identifier of the associated team season. Never null.
    *
    * @return
    */
   public String getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Sets the identifier of the associated team season. Must not be null.
    *
    * @param teamSeasonId
    */
   public void setTeamSeasonId(String teamSeasonId) {
      this.teamSeasonId = teamSeasonId;
   }

   /**
    * Returns the play period.
    *
    * @return
    */
   public int getPeriod() {
      return period;
   }

   /**
    * Sets the play period.
    *
    * @param period
    */
   public void setPeriod(int period) {
      this.period = period;
   }

   /**
    * Returns comments, or null.
    *
    * @return
    */
   public String getComment() {
      return comment;
   }

   /**
    * Sets a comment. Use null if none.
    *
    * @param comment
    */
   public void setComment(String comment) {
      this.comment = comment;
   }

   /**
    * Returns the team name.
    *
    * @return
    */
   public String getTeamName() {
      return teamName;
   }

   /**
    * Sets the team name.
    *
    * @param teamName
    */
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
