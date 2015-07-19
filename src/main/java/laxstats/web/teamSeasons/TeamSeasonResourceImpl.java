package laxstats.web.teamSeasons;

import javax.validation.constraints.NotNull;

import laxstats.api.teamSeasons.TeamStatus;

import org.joda.time.LocalDate;

/**
 * {@code TeamSeasonResource} represents a team season resource for remote clients.
 */
public class TeamSeasonResourceImpl implements TeamSeasonResource {
   private String id;

   @NotNull
   private String team;

   @NotNull
   private String season;

   private String affiliation;

   @NotNull
   private String startsOn;

   private String endsOn;
   private String name;

   @NotNull
   private TeamStatus status;

   private String teamTitle;

   /**
    * Creates a {@code TeamSeasonResource} with the given information.
    *
    * @param id
    * @param team
    * @param season
    * @param affiliation
    * @param startsOn
    * @param endsOn
    * @param name
    * @param status
    * @param teamTitle
    */
   public TeamSeasonResourceImpl(String id, String team, String season, String affiliation,
      String startsOn, String endsOn, String name, TeamStatus status, String teamTitle) {
      this.id = id;
      this.team = team;
      this.season = season;
      this.affiliation = affiliation;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
      this.name = name;
      this.status = status;
      this.teamTitle = teamTitle;
   }

   /**
    * Creates an empty {@code TeamSeasonResource} for internal use.
    */
   public TeamSeasonResourceImpl() {
   }

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
      assert team != null;
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
      assert season != null;
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
      return startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOn(String startsOn) {
      assert startsOn != null;
      this.startsOn = startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getStartsOnAsLocalDate() {
      return startsOn == null || startsOn.length() == 0 ? null : LocalDate.parse(startsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOn(LocalDate startsOn) {
      assert startsOn != null;
      this.startsOn = startsOn == null ? null : startsOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getEndsOn() {
      return endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getEndsOnAsLocalDate() {
      return endsOn == null || endsOn.length() == 0 ? null : LocalDate.parse(endsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOn(LocalDate endsOn) {
      this.endsOn = endsOn == null ? null : endsOn.toString();
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
      assert status != null;
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
    * {@inheritDoc}
    */
   @Override
   public void setTeamTitle(String teamTitle) {
      this.teamTitle = teamTitle;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return String.format(
         "TeamSeasonResourceImpl[id=%s,team=%s,season=%s,affiliation=%s,startsOn=%s,"
            + "endsOn=%s,name=%s,status=%s,teamTitle=%s]", id, team, season, affiliation, startsOn,
         endsOn, name, status, teamTitle);
   }
}
