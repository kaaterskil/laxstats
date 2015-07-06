package laxstats.web.games;

import laxstats.api.games.Conditions;
import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.sites.SiteAlignment;

import org.joda.time.LocalDateTime;

public interface GameResource {

   /**
    * Returns the game's unique identifier, or null if the game has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the game's unique identifier. Use null if the game has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the scheduled date and time of the start of the game. Never null.
    *
    * @return
    */
   public String getStartsAt();

   /**
    * Sets the scheduled date and time of the start of the game. Must not be null.
    *
    * @param startsAt
    */
   public void setStartsAt(String startsAt);

   /**
    * Returns the scheduled date and time of the start of the game. Never null.
    *
    * @return
    */
   public LocalDateTime getStartsAtAsDateTime();

   /**
    * Sets the scheduled date and time of the start of the game. Must not be null.
    *
    * @param startsAt
    */
   public void setStartsAt(LocalDateTime startsAt);

   /**
    * Returns the game status. Never null.
    *
    * @return
    */
   public Status getStatus();

   /**
    * Sets the game status. Must not be null.
    *
    * @param status
    */
   public void setStatus(Status status);

   /**
    * Returns the identifier of the associated playing field, or null.
    *
    * @return
    */
   public String getSite();

   /**
    * Sets the identifier of the associated playing field. Use null if not known.
    *
    * @param site
    */
   public void setSite(String site);

   /**
    * Returns the identifier of the associated Team One.
    *
    * @return
    */
   public String getTeamOne();

   /**
    * Sets the identifier of the associated Team One.
    *
    * @param teamOne
    */
   public void setTeamOne(String teamOne);

   /**
    * Returns the identifier of the associated Team Two.
    *
    * @return
    */
   public String getTeamTwo();

   /**
    * Sets the identifier of the associated Team Two.
    *
    * @param teamTwo
    */
   public void setTeamTwo(String teamTwo);

   /**
    * Returns true if Team One is playing as the home team, false otherwise.
    *
    * @return
    */
   public boolean isTeamOneHome();

   /**
    * Sets a flag whether Team One is playing as the home team.
    *
    * @param teamOneHome
    */
   public void setTeamOneHome(boolean teamOneHome);

   /**
    * Returns true if Team Two is playing as the home team, false otherwise.
    *
    * @return
    */
   public boolean isTeamTwoHome();

   /**
    * Sets a flag whether Team Two is playing as the home team.
    *
    * @param teamTwoHome
    */
   public void setTeamTwoHome(boolean teamTwoHome);

   /**
    * Returns the playing field alignment.
    *
    * @return
    */
   public SiteAlignment getAlignment();

   /**
    * Sets the playing field alignment. Defaults to SiteAlignment.HOME as most regular season games
    * are not played on neutral territory.
    *
    * @param alignment
    */
   public void setAlignment(SiteAlignment alignment);

   /**
    * Returns a description of the game, or null.
    *
    * @return
    */
   public String getDescription();

   /**
    * Sets a description of the game. Use null for none.
    *
    * @param description
    */
   public void setDescription(String description);

   /**
    * Returns game time weather conditions, or null.
    *
    * @return
    */
   public Conditions getWeather();

   /**
    * Sets the game time weather conditions. Use null for unknown.
    *
    * @param weather
    */
   public void setWeather(Conditions weather);

   /**
    * Returns the game schedule. Never null.
    *
    * @return
    */
   public Schedule getSchedule();

   /**
    * Sets the game schedule. Must not be null. Defaults to Schedule.REGULAR for a regular season
    * game.
    *
    * @param schedule
    */
   public void setSchedule(Schedule schedule);

}
