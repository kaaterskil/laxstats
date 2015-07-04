package laxstats.query.teams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.Region;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.utils.Constants;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code TeamEntry} represents the query object model of a team.
 */
@Entity
@Table(name = "teams",
         indexes = { @Index(name = "team_idx1", columnList = "sponsor"),
            @Index(name = "team_idx2", columnList = "name"),
            @Index(name = "team_idx3", columnList = "abbreviation"),
            @Index(name = "team_idx4", columnList = "gender"),
            @Index(name = "team_idx5", columnList = "letter"),
            @Index(name = "team_idx6", columnList = "region") },
         uniqueConstraints = { @UniqueConstraint(name = "team_uk1", columnNames = { "sponsor",
            "name", "gender", "letter" }) })
public class TeamEntry implements Serializable {
   private static final long serialVersionUID = 5796046343588141092L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @Column(length = Constants.MAX_LENGTH_TITLE, nullable = false)
   private String sponsor;

   @Column(length = Constants.MAX_LENGTH_TITLE, nullable = false)
   private String name;

   @Column(length = 5)
   private String abbreviation;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private TeamGender gender;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Letter letter;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Region region;

   @ManyToOne
   @JoinColumn(name = "league_id")
   private LeagueEntry league;

   @ManyToOne
   @JoinColumn(name = "home_site_id")
   private SiteEntry homeSite;

   @Column(length = 50)
   private String encodedPassword;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
   private final List<TeamSeasonEntry> seasons = new ArrayList<>();

   /**
    * Returns a concatenation of the sponsor and team names.
    *
    * @return
    */
   public String getFullName() {
      final StringBuilder sb = new StringBuilder();

      sb.append(sponsor);
      if (name != null) {
         sb.append(" ").append(name);
      }
      return sb.toString();
   }

   /**
    * Returns a concatenation of the sponsor name, gender and team letter. For example, 'Wellesley
    * Boys Varsity'.
    *
    * @return
    */
   public String getTitle() {
      final StringBuilder sb = new StringBuilder();
      sb.append(sponsor).append(" ").append(gender.getLabel()).append(" ").append(letter.getLabel());
      return sb.toString();
   }

   /**
    * Returns the team season with the given primary key, or null if none found.
    *
    * @param id
    * @return
    */
   public TeamSeasonEntry getSeason(String id) {
      TeamSeasonEntry result = null;
      for (final TeamSeasonEntry each : seasons) {
         if (each.getId().equals(id)) {
            result = each;
            break;
         }
      }
      return result;
   }

   /**
    * Returns the team season whose time interval contains the given date and time, or null if none.
    *
    * @param datetime
    * @return
    */
   public TeamSeasonEntry getSeason(LocalDateTime datetime) {
      TeamSeasonEntry result = null;
      for (final TeamSeasonEntry each : seasons) {
         if (each.includes(datetime)) {
            result = each;
            break;
         }
      }
      return result;
   }

   /**
    * Appends the given {@code TeamSeason} to the team's list of seasons. Returns true if the given
    * team season does not already exist in the collection, false otherwise.
    *
    * @param season
    * @return
    */
   public boolean addSeason(TeamSeasonEntry season) {
      if (!seasons.contains(season)) {
         season.setTeam(this);
         return seasons.add(season);
      }
      return false;
   }

   /**
    * Returns the primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the team sponsor. Never null.
    *
    * @return
    */
   public String getSponsor() {
      return sponsor;
   }

   /**
    * Sets the team sponsor.
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
    * Sets the team name.
    *
    * @param name
    */
   public void setName(String name) {
      assert name != null;
      this.name = name;
   }

   /**
    * Returns the team abbreviation, or null if none.
    *
    * @return
    */
   public String getAbbreviation() {
      return abbreviation;
   }

   /**
    * Sets the team abbreviation.
    *
    * @param abbreviation
    */
   public void setAbbreviation(String abbreviation) {
      this.abbreviation = abbreviation;
   }

   /**
    * Returns the team gender. Never null.
    *
    * @return
    */
   public TeamGender getGender() {
      return gender;
   }

   /**
    * Sets the team gender.
    *
    * @param gender
    */
   public void setGender(TeamGender gender) {
      assert gender != null;
      this.gender = gender;
   }

   /**
    * Returns the team letter. Never null.
    *
    * @return
    */
   public Letter getLetter() {
      return letter;
   }

   /**
    * Sets the team letter.
    *
    * @param letter
    */
   public void setLetter(Letter letter) {
      assert letter != null;
      this.letter = letter;
   }

   /**
    * Returns the region. Never null.
    *
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Sets the region.
    *
    * @param region
    */
   public void setRegion(Region region) {
      assert region != null;
      this.region = region;
   }

   /**
    * Returns the league association.
    *
    * @return
    */
   public LeagueEntry getLeague() {
      return league;
   }

   /**
    * Sets the league association.
    *
    * @param league
    */
   public void setLeague(LeagueEntry league) {
      this.league = league;
   }

   /**
    * Returns the home site, or null if none.
    *
    * @return
    */
   public SiteEntry getHomeSite() {
      return homeSite;
   }

   /**
    * Sets the home site.
    *
    * @param homeSite
    */
   public void setHomeSite(SiteEntry homeSite) {
      this.homeSite = homeSite;
   }

   /**
    * Returns an encoded password, or null if none.
    *
    * @return
    */
   public String getEncodedPassword() {
      return encodedPassword;
   }

   /**
    * Sets an encoded password.
    *
    * @param encodedPassword
    */
   public void setEncodedPassword(String encodedPassword) {
      this.encodedPassword = encodedPassword;
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
    * Sets the date and time this team was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this team.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this team.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
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
    * Sets the date and time that this team was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this team.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this team.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   /**
    * Returns the collection of team seasons.
    *
    * @return
    */
   public List<TeamSeasonEntry> getSeasons() {
      return seasons;
   }
}
