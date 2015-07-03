package laxstats.web.games;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.Region;
import laxstats.api.games.Conditions;
import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.api.utils.Constants;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teams.TeamEntry;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * {@code GameForm} contains user-defined information with which to create and update a game.
 */
public class GameForm {
   private String id;

   @NotNull
   @DateTimeFormat(pattern = Constants.PATTERN_START_DATETIME_FORMAT)
   private LocalDateTime startsAt;

   @NotNull
   private Schedule schedule = Schedule.REGULAR;

   @NotNull
   private Status status;

   @NotNull
   private String site;

   private String teamOne;
   private String teamTwo;
   private boolean teamOneHome = false;
   private boolean teamTwoHome = false;
   private SiteAlignment alignment = SiteAlignment.HOME;
   private String description;
   private Conditions weather;

   private Map<Region, List<SiteEntry>> sites = new HashMap<>();
   private Map<Region, List<TeamEntry>> teams = new HashMap<>();
   private List<Schedule> schedules;
   private List<Status> statuses;
   private List<SiteAlignment> siteAlignments;
   private List<Conditions> conditions;

   /**
    * Returns the game's unique identifier.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the game's unique identifier.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the scheduled date and time of the start of the game. Never null.
    *
    * @return
    */
   public LocalDateTime getStartsAt() {
      return startsAt;
   }

   /**
    * Sets the scheduled date and time of the start of the game. Must not be null.
    *
    * @param startsAt
    */
   public void setStartsAt(LocalDateTime startsAt) {
      this.startsAt = startsAt;
   }

   /**
    * Returns the game status. Never null.
    *
    * @return
    */
   public Status getStatus() {
      return status;
   }

   /**
    * Sets the game status. Must not be null.
    *
    * @param status
    */
   public void setStatus(Status status) {
      this.status = status;
   }

   /**
    * Returns the identifier of the associated playing field.
    *
    * @return
    */
   public String getSite() {
      return site;
   }

   /**
    * Sets the identifier of the associated playing field.
    *
    * @param site
    */
   public void setSite(String site) {
      this.site = site;
   }

   /**
    * Returns the identifier of the associated Team One.
    *
    * @return
    */
   public String getTeamOne() {
      return teamOne;
   }

   /**
    * Sets the identifier of the associated Team One.
    *
    * @param teamOne
    */
   public void setTeamOne(String teamOne) {
      this.teamOne = teamOne;
   }

   /**
    * Returns the identifier of the associated Team Two.
    *
    * @return
    */
   public String getTeamTwo() {
      return teamTwo;
   }

   /**
    * Sets the identifier of the associated Team Two.
    *
    * @param teamTwo
    */
   public void setTeamTwo(String teamTwo) {
      this.teamTwo = teamTwo;
   }

   /**
    * Returns true if Team One is playing as the home team, false otherwise.
    *
    * @return
    */
   public boolean isTeamOneHome() {
      return teamOneHome;
   }

   /**
    * Sets a flag whether Team One is playing as the home team.
    *
    * @param teamOneHome
    */
   public void setTeamOneHome(boolean teamOneHome) {
      this.teamOneHome = teamOneHome;
   }

   /**
    * Returns true if Team Two is playing as the home team, false otherwise.
    *
    * @return
    */
   public boolean isTeamTwoHome() {
      return teamTwoHome;
   }

   /**
    * Sets a flag whether Team Two is playing as the home team.
    *
    * @param teamTwoHome
    */
   public void setTeamTwoHome(boolean teamTwoHome) {
      this.teamTwoHome = teamTwoHome;
   }

   /**
    * Returns the playing field alignment.
    *
    * @return
    */
   public SiteAlignment getAlignment() {
      return alignment;
   }

   /**
    * Sets the playing field alignment. Defaults to SiteAlignment.HOME as most regular season games
    * are not played on neutral territory.
    *
    * @param alignment
    */
   public void setAlignment(SiteAlignment alignment) {
      this.alignment = alignment;
   }

   /**
    * Returns a description of the game, or null.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets a description of the game. Use null for none.
    *
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Returns game time weather conditions, or null.
    *
    * @return
    */
   public Conditions getWeather() {
      return weather;
   }

   /**
    * Sets the game time weather conditions. Use null for unknown.
    *
    * @param weather
    */
   public void setWeather(Conditions weather) {
      this.weather = weather;
   }

   /**
    * Returns the game schedule. Never null.
    *
    * @return
    */
   public Schedule getSchedule() {
      return schedule;
   }

   /**
    * Sets the game schedule. Must not be null. Defaults to Schedule.REGULAR for a regular season
    * game.
    *
    * @param schedule
    */
   public void setSchedule(Schedule schedule) {
      this.schedule = schedule;
   }

   /*---------- Select element options ----------*/

   public Map<Region, List<SiteEntry>> getSites() {
      return sites;
   }

   public void setSites(Map<Region, List<SiteEntry>> sites) {
      this.sites = sites;
   }

   public Map<Region, List<TeamEntry>> getTeams() {
      return teams;
   }

   public void setTeams(Map<Region, List<TeamEntry>> teams) {
      this.teams = teams;
   }

   public List<Schedule> getSchedules() {
      if (schedules == null) {
         schedules = Arrays.asList(Schedule.values());
      }
      return schedules;
   }

   public List<Status> getStatuses() {
      if (statuses == null) {
         statuses = Arrays.asList(Status.values());
      }
      return statuses;
   }

   public List<SiteAlignment> getSiteAlignments() {
      if (siteAlignments == null) {
         siteAlignments = Arrays.asList(SiteAlignment.values());
      }
      return siteAlignments;
   }

   public List<Conditions> getConditions() {
      if (conditions == null) {
         conditions = Arrays.asList(Conditions.values());
      }
      return conditions;
   }

}
