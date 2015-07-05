package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.Conditions;
import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;

/**
 * {@code GameResource} represents a game resource for remote clients.
 */
public class GameResource {
   private String id;

   @NotNull
   private String startsAt;

   @NotNull
   private Schedule schedule = Schedule.REGULAR;

   @NotNull
   private Status status;

   private String site;
   private String teamOne;
   private String teamTwo;
   private boolean teamOneHome = false;
   private boolean teamTwoHome = false;
   private SiteAlignment alignment = SiteAlignment.HOME;
   private String description;
   private Conditions weather;

   /**
    * Creates a {@code GameResource} with the given information.
    * 
    * @param id
    * @param startsAt
    * @param schedule
    * @param status
    * @param site
    * @param teamOne
    * @param teamTwo
    * @param teamOneHome
    * @param teamTwoHome
    * @param alignment
    * @param description
    * @param weather
    */
   public GameResource(String id, String startsAt, Schedule schedule, Status status, String site,
      String teamOne, String teamTwo, boolean teamOneHome, boolean teamTwoHome,
      SiteAlignment alignment, String description, Conditions weather) {
      this.id = id;
      this.startsAt = startsAt;
      this.schedule = schedule;
      this.status = status;
      this.site = site;
      this.teamOne = teamOne;
      this.teamTwo = teamTwo;
      this.teamOneHome = teamOneHome;
      this.teamTwoHome = teamTwoHome;
      this.alignment = alignment;
      this.description = description;
      this.weather = weather;
   }

   /**
    * Creates an empty {@code GameResource} for internal use.
    */
   public GameResource() {
   }

   /**
    * Returns the game's unique identifier, or null if the game has not been persisted.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the game's unique identifier. Use null if the game has not been persisted.
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
   public String getStartsAt() {
      return startsAt;
   }

   /**
    * Sets the scheduled date and time of the start of the game. Must not be null.
    *
    * @param startsAt
    */
   public void setStartsAt(String startsAt) {
      assert startsAt != null;
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
      assert status != null;
      this.status = status;
   }

   /**
    * Returns the identifier of the associated playing field, or null.
    *
    * @return
    */
   public String getSite() {
      return site;
   }

   /**
    * Sets the identifier of the associated playing field. Use null if not known.
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
      assert schedule != null;
      this.schedule = schedule;
   }

}
