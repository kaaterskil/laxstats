package laxstats.web.teamSeasons;

import laxstats.api.teamSeasons.TeamStatus;

import org.joda.time.LocalDate;

public interface TeamSeasonResource {

   /**
    * Returns the team season primary key, or null if the team season has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the team season primary key. Use null if the team season has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the parent team's unique identifier. Never null.
    *
    * @return
    */
   public String getTeam();

   /**
    * Sets the parent team's unique identifier. Must not be null.
    *
    * @param team
    */
   public void setTeam(String team);

   /**
    * Returns the associated season's unique identifier. Never null.
    *
    * @return
    */
   public String getSeason();

   /**
    * Sets the associated season's unique identifier. Must not be null.
    *
    * @param season
    */
   public void setSeason(String season);

   /**
    * Returns the associated league's unique identifier, or null.
    *
    * @return
    */
   public String getLeague();

   /**
    * Sets the associated league's unique identifier. Use null for none or unknown.
    *
    * @param affiliation
    */
   public void setLeague(String affiliation);

   /**
    * Returns the season starting date. Never null.
    *
    * @return
    */
   public String getStartsOn();

   /**
    * Sets the season starting date. Must not be null.
    *
    * @param startsOn
    */
   public void setStartsOn(String startsOn);

   /**
    * Returns the season starting date as a LocalDate. Never null.
    *
    * @return
    */
   public LocalDate getStartsOnAsLocalDate();

   /**
    * Sets the season starting date from a LocalDate. Must not be null.
    *
    * @param startsOn
    */
   public void setStartsOn(LocalDate startsOn);

   /**
    * Returns the season end date, or null.
    *
    * @return
    */
   public String getEndsOn();

   /**
    * Sets the season end date. Use null for none.
    *
    * @param endsOn
    */
   public void setEndsOn(String endsOn);

   /**
    * Returns the season end date as a LocalDate, or null.
    *
    * @return
    */
   public LocalDate getEndsOnAsLocalDate();

   /**
    * Sets the season end date from a LocalDate. Use null for none.
    *
    * @param endsOn
    */
   public void setEndsOn(LocalDate endsOn);

   /**
    * Returns the team season name, or null to default to the team name.
    *
    * @return
    */
   public String getName();

   /**
    * Sets the team season name. Use null to default to the team name.
    *
    * @param name
    */
   public void setName(String name);

   /**
    * Returns the team season status. Never null.
    *
    * @return
    */
   public TeamStatus getStatus();

   /**
    * Sets the team season status. Must not be null.
    *
    * @param status
    */
   public void setStatus(TeamStatus status);

   /**
    * Returns the title of the team, or null or default to the default title.
    *
    * @return
    */
   public String getTeamTitle();

   /**
    * Sets the title of the team. Use null to default to the team title.
    *
    * @param teamTitle
    */
   public void setTeamTitle(String teamTitle);

}
