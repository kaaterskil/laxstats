package laxstats.api.games;

import java.io.Serializable;

import laxstats.api.sites.SiteAlignment;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code GameDTO} transfers information about a game between the application and domain layers.
 */
public class GameDTO implements Serializable {
   private static final long serialVersionUID = 4780995358462203737L;

   private final String id;
   private final SiteEntry site;
   private final TeamSeasonEntry teamOne;
   private final TeamSeasonEntry teamTwo;
   private final Alignment teamOneAlignment;
   private final Alignment teamTwoAlignment;
   private final SiteAlignment alignment;
   private final LocalDateTime startsAt;
   private final Schedule schedule;
   private final Status status;
   private final Conditions conditions;
   private final String description;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code GameDTO} with the given information.
    *
    * @param id
    * @param site
    * @param teamOne
    * @param teamTwo
    * @param teamOneAlignment
    * @param teamTwoAlignment
    * @param alignment
    * @param startsAt
    * @param schedule
    * @param status
    * @param conditions
    * @param description
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public GameDTO(String id, SiteEntry site, TeamSeasonEntry teamOne, TeamSeasonEntry teamTwo,
      Alignment teamOneAlignment, Alignment teamTwoAlignment, SiteAlignment alignment,
      LocalDateTime startsAt, Schedule schedule, Status status, Conditions conditions,
      String description, LocalDateTime createdAt, UserEntry createdBy, LocalDateTime modifiedAt,
      UserEntry modifiedBy) {
      this.id = id;
      this.site = site;
      this.teamOne = teamOne;
      this.teamTwo = teamTwo;
      this.teamOneAlignment = teamOneAlignment;
      this.teamTwoAlignment = teamTwoAlignment;
      this.alignment = alignment;
      this.startsAt = startsAt;
      this.schedule = schedule;
      this.status = status;
      this.conditions = conditions;
      this.description = description;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code GameDTO} with the given information.
    *
    * @param id
    * @param site
    * @param teamOne
    * @param teamTwo
    * @param teamOneAlignment
    * @param teamTwoAlignment
    * @param alignment
    * @param startsAt
    * @param schedule
    * @param status
    * @param conditions
    * @param description
    * @param modifiedAt
    * @param modifiedBy
    */
   public GameDTO(String id, SiteEntry site, TeamSeasonEntry teamOne, TeamSeasonEntry teamTwo,
      Alignment teamOneAlignment, Alignment teamTwoAlignment, SiteAlignment alignment,
      LocalDateTime startsAt, Schedule schedule, Status status, Conditions conditions,
      String description, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(id, site, teamOne, teamTwo, teamOneAlignment, teamTwoAlignment, alignment, startsAt,
         schedule, status, conditions, description, null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns the unqiue identifier of the game.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the associated playing field.
    *
    * @return
    */
   public SiteEntry getSite() {
      return site;
   }

   /**
    * Returns the associated first team.
    *
    * @return
    */
   public TeamSeasonEntry getTeamOne() {
      return teamOne;
   }

   /**
    * Returns the associated second team.
    *
    * @return
    */
   public TeamSeasonEntry getTeamTwo() {
      return teamTwo;
   }

   /**
    * Returns the alignment of the first team.
    *
    * @return
    */
   public Alignment getTeamOneAlignment() {
      return teamOneAlignment;
   }

   /**
    * Returns the alignment of the second team.
    *
    * @return
    */
   public Alignment getTeamTwoAlignment() {
      return teamTwoAlignment;
   }

   /**
    * Returns the game alignment.
    *
    * @return
    */
   public SiteAlignment getAlignment() {
      return alignment;
   }

   /**
    * Returns the date and starting time of the game.
    *
    * @return
    */
   public LocalDateTime getStartsAt() {
      return startsAt;
   }

   /**
    * Returns the game schedule (e.g. regular, pre-season, etc.)
    *
    * @return
    */
   public Schedule getSchedule() {
      return schedule;
   }

   /**
    * Returns the game status.
    *
    * @return
    */
   public Status getStatus() {
      return status;
   }

   /**
    * Returns the game weather conditions.
    *
    * @return
    */
   public Conditions getConditions() {
      return conditions;
   }

   /**
    * Returns the game description.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Returs the date and time this game was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns the user who first persisted this game.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns the date and time this game was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this game.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
