package laxstats.web.teamSeasons;

import javax.validation.constraints.NotNull;

import laxstats.api.teamSeasons.TeamStatus;

/**
 * {@code TeamSeasonResource} represents a team season resource for remote clients.
 */
public class TeamSeasonResource {
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
   public TeamSeasonResource(String id, String team, String season, String affiliation,
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
   public TeamSeasonResource() {
   }

   /**
    * Returns the team season primary key, or null if the team season has not been persisted.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the team season primary key. Use null if the team season has ot been persisted.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the parent team. Never null.
    *
    * @return
    */
   public String getTeam() {
      return team;
   }

   /**
    * Sets the parent team. Must not be null.
    *
    * @param team
    */
   public void setTeam(String team) {
      assert team != null;
      this.team = team;
   }

   /**
    * Returns the associated season. Never null.
    *
    * @return
    */
   public String getSeason() {
      return season;
   }

   /**
    * Sets the associated season. Must not be null.
    *
    * @param season
    */
   public void setSeason(String season) {
      assert season != null;
      this.season = season;
   }

   /**
    * Returns the associated league, or null.
    *
    * @return
    */
   public String getLeague() {
      return affiliation;
   }

   /**
    * Sets the associated league. Use null for none or unknown.
    *
    * @param affiliation
    */
   public void setLeague(String affiliation) {
      this.affiliation = affiliation;
   }

   /**
    * Returns the season starting date. Never null.
    *
    * @return
    */
   public String getStartsOn() {
      return startsOn;
   }

   /**
    * Sets the season starting date. Must not be null.
    *
    * @param startsOn
    */
   public void setStartsOn(String startsOn) {
      assert startsOn != null;
      this.startsOn = startsOn;
   }

   /**
    * Returns the season end date, or null.
    *
    * @return
    */
   public String getEndsOn() {
      return endsOn;
   }

   /**
    * Sets the season end date. Use null for none.
    *
    * @param endsOn
    */
   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn;
   }

   /**
    * Returns the team season name, or null to default to the team name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the team season name. Use null to default to the team name.
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Returns the team season status. Never null.
    *
    * @return
    */
   public TeamStatus getStatus() {
      return status;
   }

   /**
    * Sets the team season status. Must not be null.
    *
    * @param status
    */
   public void setStatus(TeamStatus status) {
      assert status != null;
      this.status = status;
   }

   /**
    * Returns the title of the team, or null or default to the default title.
    *
    * @return
    */
   public String getTeamTitle() {
      return teamTitle;
   }

   /**
    * Sets the title of the team. Use null to default to the team title.
    *
    * @param teamTitle
    */
   public void setTeamTitle(String teamTitle) {
      this.teamTitle = teamTitle;
   }
}
