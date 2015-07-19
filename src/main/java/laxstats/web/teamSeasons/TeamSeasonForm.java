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
public class TeamSeasonForm implements TeamSeasonResource {
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
   public String getTeam() {
      return team;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeam(String team) {
      this.team = team;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getSeason() {
      return season;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setSeason(String season) {
      this.season = season;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getLeague() {
      return affiliation;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLeague(String affiliation) {
      this.affiliation = affiliation;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getStartsOn() {
      return startsOn == null ? null : startsOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOn(String startsOn) {
      this.startsOn = startsOn == null || startsOn.length() == 0 ? null : LocalDate.parse(startsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getStartsOnAsLocalDate() {
      return startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOnFromLocalDate(LocalDate startsOn) {
      this.startsOn = startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getEndsOn() {
      return endsOn == null ? null : endsOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn == null || endsOn.length() == 0 ? null : LocalDate.parse(endsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getEndsOnAsLocalDate() {
      return endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOnFromLocalDate(LocalDate endsOn) {
      this.endsOn = endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName() {
      return name;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setName(String name) {
      this.name = name;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public TeamStatus getStatus() {
      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStatus(TeamStatus status) {
      this.status = status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getTeamTitle() {
      return teamTitle;
   }

   /**
    * Sets the title of the team.
    *
    * @param teamTitle
    */
   @Override
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
