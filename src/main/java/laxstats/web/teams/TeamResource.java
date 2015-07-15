package laxstats.web.teams;

import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;

public interface TeamResource {

   /**
    * Returns the team's unique identifier, or null if the team has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the team's unique identifier. Use null if the team has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the team sponsor's name. Never null.
    *
    * @return
    */
   public String getSponsor();

   /**
    * Sets the team sponsor name. Must not be null.
    *
    * @param sponsor
    */
   public void setSponsor(String sponsor);

   /**
    * Returns the team name. Never null.
    *
    * @return
    */
   public String getName();

   /**
    * Sets the team name. Must not be null.
    *
    * @param name
    */
   public void setName(String name);

   /**
    * Returns the team abbreviation, or null.
    *
    * @return
    */
   public String getAbbreviation();

   /**
    * Sets the team abbreviation. Use null for none or unknown.
    *
    * @param abbreviation
    */
   public void setAbbreviation(String abbreviation);

   /**
    * Returns the team's gender. Never null.
    *
    * @return
    */
   public TeamGender getGender();

   /**
    * Sets the team gender. Must not be null.
    *
    * @param gender
    */
   public void setGender(TeamGender gender);

   /**
    * Retrns the team's letter. Never null.
    *
    * @return
    */
   public Letter getLetter();

   /**
    * Sets the team letter. Must not be null.
    *
    * @param letter
    */
   public void setLetter(Letter letter);

   /**
    * Returns the team's region. Never null.
    *
    * @return
    */
   public Region getRegion();

   /**
    * Sets the team region. Must not be null.
    *
    * @param region
    */
   public void setRegion(Region region);

   /**
    * Returns the identifier of the team's associated playing field, or null.
    *
    * @return
    */
   public String getHomeSite();

   /**
    * Sets the identifier of the team's associated playing field. Use null for none or unknown.
    *
    * @param homeSite
    */
   public void setHomeSite(String homeSite);

   /**
    * Returns the identifier of the team's associated league, or null.
    *
    * @return
    */
   public String getLeague();

   /**
    * Sets the identifier of the team's associated league. Use null for none or unknown.
    *
    * @param league
    */
   public void setLeague(String league);

}
