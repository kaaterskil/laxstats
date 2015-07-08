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
public class GameForm implements GameResource {
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
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getStartsAt() {
      return startsAt == null ? null : startsAt.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsAt(String startsAt) {
      this.startsAt = startsAt == null ? null : LocalDateTime.parse(startsAt);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDateTime getStartsAtAsDateTime() {
      return startsAt;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsAtAsDateTime(LocalDateTime startsAt) {
      this.startsAt = startsAt;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Status getStatus() {
      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStatus(Status status) {
      this.status = status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getSite() {
      return site;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setSite(String site) {
      this.site = site;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getTeamOne() {
      return teamOne;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeamOne(String teamOne) {
      this.teamOne = teamOne;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getTeamTwo() {
      return teamTwo;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeamTwo(String teamTwo) {
      this.teamTwo = teamTwo;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isTeamOneHome() {
      return teamOneHome;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeamOneHome(boolean teamOneHome) {
      this.teamOneHome = teamOneHome;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isTeamTwoHome() {
      return teamTwoHome;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeamTwoHome(boolean teamTwoHome) {
      this.teamTwoHome = teamTwoHome;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public SiteAlignment getAlignment() {
      return alignment;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAlignment(SiteAlignment alignment) {
      this.alignment = alignment;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getDescription() {
      return description;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Conditions getWeather() {
      return weather;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setWeather(Conditions weather) {
      this.weather = weather;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Schedule getSchedule() {
      return schedule;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
