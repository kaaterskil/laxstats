package laxstats.web.teamSeasons;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.seasons.SeasonEntry;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * {@code TeamSeasonForm} contains user-defined information for creating and updating team seasons.
 */
public class TeamSeasonForm {
   private String id;

   @NotNull
   private String team;

   @NotNull
   private String season;

   private String affiliation;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate startsOn;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate endsOn;

   private String name;

   @NotNull
   private TeamStatus status;

   private String teamTitle;
   private List<SeasonEntry> seasons;
   private List<LeagueEntry> leagues;
   private List<TeamStatus> statuses;

   /**
    * Returns the team season primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the team season primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * eturns the parent team.
    *
    * @return
    */
   public String getTeam() {
      return team;
   }

   /**
    * Sets the parent team.
    *
    * @param team
    */
   public void setTeam(String team) {
      this.team = team;
   }

   /**
    * Returns the associated season.
    *
    * @return
    */
   public String getSeason() {
      return season;
   }

   /**
    * Sets the associated season.
    *
    * @param season
    */
   public void setSeason(String season) {
      this.season = season;
   }

   /**
    * Returns the associated league.
    *
    * @return
    */
   public String getLeague() {
      return affiliation;
   }

   /**
    * Sets the associated league.
    *
    * @param affiliation
    */
   public void setLeague(String affiliation) {
      this.affiliation = affiliation;
   }

   /**
    * Returns the season starting date.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Sets the season starting date.
    *
    * @param startsOn
    */
   public void setStartsOn(LocalDate startsOn) {
      this.startsOn = startsOn;
   }

   /**
    * Returns the season end date.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }

   /**
    * Sets the season end date.
    *
    * @param endsOn
    */
   public void setEndsOn(LocalDate endsOn) {
      this.endsOn = endsOn;
   }

   /**
    * Returns the team season name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the team season name.
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Returns the team season status.
    *
    * @return
    */
   public TeamStatus getStatus() {
      return status;
   }

   /**
    * Sets the team season status.
    *
    * @param status
    */
   public void setStatus(TeamStatus status) {
      this.status = status;
   }

   /**
    * Returns the title of the team.
    *
    * @return
    */
   public String getTeamTitle() {
      return teamTitle;
   }

   /**
    * Sets the title of the team.
    *
    * @param teamTitle
    */
   public void setTeamTitle(String teamTitle) {
      this.teamTitle = teamTitle;
   }

   public List<SeasonEntry> getSeasons() {
      return seasons;
   }

   public void setSeasons(List<SeasonEntry> seasons) {
      this.seasons = seasons;
   }

   public List<LeagueEntry> getLeagues() {
      return leagues;
   }

   public void setLeagues(List<LeagueEntry> leagues) {
      this.leagues = leagues;
   }

   public List<TeamStatus> getStatuses() {
      if (statuses == null) {
         statuses = Arrays.asList(TeamStatus.values());
      }
      return statuses;
   }
}
