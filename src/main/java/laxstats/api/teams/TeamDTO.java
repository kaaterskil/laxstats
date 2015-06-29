package laxstats.api.teams;

import java.io.Serializable;

import laxstats.api.Region;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code TeamDTO} transfers team information between the application and domain layers.
 */
public class TeamDTO implements Serializable {
   private static final long serialVersionUID = -4744520171017929334L;

   private final TeamId teamId;
   private final String sponsor;
   private final String name;
   private final String abbreviation;
   private final TeamGender gender;
   private final Letter letter;
   private final Region region;
   private final LeagueEntry league;
   private final SiteEntry homeSite;
   private final String encodedPassword;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code TeamDTO} with the given information.
    * 
    * @param teamId
    * @param sponsor
    * @param name
    * @param abbreviation
    * @param gender
    * @param letter
    * @param region
    * @param league
    * @param homeSite
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public TeamDTO(TeamId teamId, String sponsor, String name, String abbreviation,
      TeamGender gender, Letter letter, Region region, LeagueEntry league, SiteEntry homeSite,
      LocalDateTime createdAt, UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(teamId, sponsor, name, abbreviation, gender, letter, region, league, homeSite, null,
         createdAt, createdBy, modifiedAt, modifiedBy);
   }

   /**
    * Creates a {@code TeamDTO} with the given information.
    * 
    * @param teamId
    * @param sponsor
    * @param name
    * @param abbreviation
    * @param gender
    * @param letter
    * @param region
    * @param league
    * @param homeSite
    * @param modifiedAt
    * @param modifiedBy
    */
   public TeamDTO(TeamId teamId, String sponsor, String name, String abbreviation,
      TeamGender gender, Letter letter, Region region, LeagueEntry league, SiteEntry homeSite,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(teamId, sponsor, name, abbreviation, gender, letter, region, league, homeSite, null,
         null, null, modifiedAt, modifiedBy);
   }

   /**
    * Creates a {@code TeamDTO} with the given information.
    * 
    * @param teamId
    * @param encodedPassword
    * @param modifiedAt
    * @param modifiedBy
    */
   public TeamDTO(TeamId teamId, String encodedPassword, LocalDateTime modifiedAt,
      UserEntry modifiedBy) {
      this(teamId, null, null, null, null, null, null, null, null, encodedPassword, null, null,
         modifiedAt, modifiedBy);
   }

   /**
    * Creates a {@code TeamDTO} with the given information.
    * 
    * @param teamId
    * @param sponsor
    * @param name
    * @param abbreviation
    * @param gender
    * @param letter
    * @param region
    * @param league
    * @param homeSite
    * @param encodedPassword
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   protected TeamDTO(TeamId teamId, String sponsor, String name, String abbreviation,
      TeamGender gender, Letter letter, Region region, LeagueEntry league, SiteEntry homeSite,
      String encodedPassword, LocalDateTime createdAt, UserEntry createdBy,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.teamId = teamId;
      this.sponsor = sponsor;
      this.name = name;
      this.abbreviation = abbreviation;
      this.gender = gender;
      this.letter = letter;
      this.region = region;
      this.league = league;
      this.homeSite = homeSite;
      this.encodedPassword = encodedPassword;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Returns the team aggregate identifier.
    * 
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }

   /**
    * Returns the team sponsor.
    * 
    * @return
    */
   public String getSponsor() {
      return sponsor;
   }

   /**
    * Returns the team name.
    * 
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Returns the team abbreviation.
    * 
    * @return
    */
   public String getAbbreviation() {
      return abbreviation;
   }

   /**
    * Returns this team's gender.
    * 
    * @return
    */
   public TeamGender getGender() {
      return gender;
   }

   /**
    * Returns this team's letter.
    * 
    * @return
    */
   public Letter getLetter() {
      return letter;
   }

   /**
    * Returns this team's region.
    * 
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Returns this team's associated league.
    * 
    * @return
    */
   public LeagueEntry getLeague() {
      return league;
   }

   /**
    * Returns this team's home site.
    * 
    * @return
    */
   public SiteEntry getHomeSite() {
      return homeSite;
   }

   /**
    * Returns this team's encoded password.
    * 
    * @return
    */
   public String getEncodedPassword() {
      return encodedPassword;
   }

   /**
    * Returns the date and time this team was first persisted.
    * 
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns the user who first persister this team.
    * 
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns the date and time this team was last modified.
    * 
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this team.
    * 
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
