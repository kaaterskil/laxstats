package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.Conditions;
import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;

import org.joda.time.LocalDateTime;

/**
 * {@code GameResource} represents a game resource for remote clients.
 */
public class GameResourceImpl implements GameResource {
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
   public GameResourceImpl(String id, String startsAt, Schedule schedule, Status status,
      String site, String teamOne, String teamTwo, boolean teamOneHome, boolean teamTwoHome,
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
   public GameResourceImpl() {
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
   public String getStartsAt() {
      return startsAt;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsAt(String startsAt) {
      assert startsAt != null;
      this.startsAt = startsAt;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDateTime getStartsAtAsDateTime() {
      return LocalDateTime.parse(startsAt);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsAt(LocalDateTime startsAt) {
      assert startsAt != null;
      this.startsAt = startsAt.toString();
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
      assert status != null;
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
      assert schedule != null;
      this.schedule = schedule;
   }

}
