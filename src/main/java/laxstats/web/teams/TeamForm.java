package laxstats.web.teams;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.utils.Constants;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.sites.SiteEntry;

public class TeamForm implements TeamResource {

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

   private List<TeamGender> genders;
   private List<Letter> letters;
   private List<SiteEntry> sites;
   private List<Region> regions;
   private List<LeagueEntry> affiliations;

   /*---------- Getter/Setters ----------*/

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

   /*---------- Drop down lists ----------*/

   public List<TeamGender> getGenders() {
      if (genders == null) {
         genders = Arrays.asList(TeamGender.values());
      }
      return genders;
   }

   public List<Letter> getLetters() {
      if (letters == null) {
         letters = Arrays.asList(Letter.values());
      }
      return letters;
   }

   public List<Region> getRegions() {
      if (regions == null) {
         regions = Arrays.asList(Region.values());
      }
      return regions;
   }

   public void setSites(List<SiteEntry> sites) {
      this.sites = sites;
   }

   public List<SiteEntry> getSites() {
      return sites;
   }

   public List<LeagueEntry> getAffiliations() {
      return affiliations;
   }

   public void setAffiliations(List<LeagueEntry> affiliations) {
      this.affiliations = affiliations;
   }
}
