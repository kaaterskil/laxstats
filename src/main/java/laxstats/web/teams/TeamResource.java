package laxstats.web.teams;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.utils.Constants;

/**
 * {@code TeamResource} represents a team resource for remote clients.
 */
public class TeamResource {

   String id;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_TITLE)
   private String sponsor;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_TITLE)
   private String name;

   @Size(max = 5)
   private String abbreviation;

   @NotNull
   private TeamGender gender;

   @NotNull
   private Letter letter;

   @NotNull
   private Region region;

   private String homeSite;
   private String league;

   /**
    * Creates a {@code TeamResource} with the given information.
    * 
    * @param id
    * @param sponsor
    * @param name
    * @param abbreviation
    * @param gender
    * @param letter
    * @param region
    * @param homeSite
    * @param league
    */
   public TeamResource(String id, String sponsor, String name, String abbreviation,
      TeamGender gender, Letter letter, Region region, String homeSite, String league) {
      this.id = id;
      this.sponsor = sponsor;
      this.name = name;
      this.abbreviation = abbreviation;
      this.gender = gender;
      this.letter = letter;
      this.region = region;
      this.homeSite = homeSite;
      this.league = league;
   }

   /**
    * Creates an empty {@code TeamResource} for internal use.
    */
   public TeamResource() {
   }

   /**
    * Returns the team's unique identifier, or null if the team has not been persisted.
    * 
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the team's unique identifier. Use null if the team has not been persisted.
    * 
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the team sponsor's name. Never null.
    * 
    * @return
    */
   public String getSponsor() {
      return sponsor;
   }

   /**
    * Sets the team sponsor name. Must not be null.
    * 
    * @param sponsor
    */
   public void setSponsor(String sponsor) {
      assert sponsor != null;
      this.sponsor = sponsor;
   }

   /**
    * Returns the team name. Never null.
    * 
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the team name. Must not be null.
    * 
    * @param name
    */
   public void setName(String name) {
      assert name != null;
      this.name = name;
   }

   /**
    * Returns the team abbreviation, or null.
    * 
    * @return
    */
   public String getAbbreviation() {
      return abbreviation;
   }

   /**
    * Sets the team abbreviation. Use null for none or unknown.
    * 
    * @param abbreviation
    */
   public void setAbbreviation(String abbreviation) {
      this.abbreviation = abbreviation;
   }

   /**
    * Returns the team's gender. Never null.
    * 
    * @return
    */
   public TeamGender getGender() {
      return gender;
   }

   /**
    * Sets the team gender. Must not be null.
    * 
    * @param gender
    */
   public void setGender(TeamGender gender) {
      assert gender != null;
      this.gender = gender;
   }

   /**
    * Retrns the team's letter. Never null.
    * 
    * @return
    */
   public Letter getLetter() {
      return letter;
   }

   /**
    * Sets the team letter. Must not be null.
    * 
    * @param letter
    */
   public void setLetter(Letter letter) {
      assert letter != null;
      this.letter = letter;
   }

   /**
    * Returns the team's region. Never null.
    * 
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Sets the team region. Must not be null.
    * 
    * @param region
    */
   public void setRegion(Region region) {
      assert region != null;
      this.region = region;
   }

   /**
    * Returns the identifier of the team's associated playing field, or null.
    * 
    * @return
    */
   public String getHomeSite() {
      return homeSite;
   }

   /**
    * Sets the identifier of the team's associated playing field. Use null for none or unknown.
    * 
    * @param homeSite
    */
   public void setHomeSite(String homeSite) {
      this.homeSite = homeSite;
   }

   /**
    * Returns the identifier of the team's associated league, or null.
    * 
    * @return
    */
   public String getLeague() {
      return league;
   }

   /**
    * Sets the identifier of the team's associated league. Use null for none or unknown.
    * 
    * @param league
    */
   public void setLeague(String league) {
      this.league = league;
   }
}
