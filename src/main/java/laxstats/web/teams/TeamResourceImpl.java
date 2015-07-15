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
public class TeamResourceImpl implements TeamResource {

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
   public TeamResourceImpl(String id, String sponsor, String name, String abbreviation,
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
   public TeamResourceImpl() {
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
   public String getSponsor() {
      return sponsor;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setSponsor(String sponsor) {
      assert sponsor != null;
      this.sponsor = sponsor;
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
      assert name != null;
      this.name = name;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getAbbreviation() {
      return abbreviation;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAbbreviation(String abbreviation) {
      this.abbreviation = abbreviation;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public TeamGender getGender() {
      return gender;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setGender(TeamGender gender) {
      assert gender != null;
      this.gender = gender;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Letter getLetter() {
      return letter;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLetter(Letter letter) {
      assert letter != null;
      this.letter = letter;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Region getRegion() {
      return region;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setRegion(Region region) {
      assert region != null;
      this.region = region;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getHomeSite() {
      return homeSite;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setHomeSite(String homeSite) {
      this.homeSite = homeSite;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getLeague() {
      return league;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLeague(String league) {
      this.league = league;
   }
}
