package laxstats.query.games;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.games.Alignment;
import laxstats.api.games.Outcome;
import laxstats.api.utils.Constants;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code TeamEvent} represents a query object model of the many-to-many association of a team
 * season and a game.
 */
@Entity
@Table(name = "team_events",
         indexes = { @Index(name = "team_events_idx1", columnList = "outcome"),
            @Index(name = "team_events_idx2", columnList = "alignment") },
         uniqueConstraints = { @UniqueConstraint(name = "team_events_uk1", columnNames = {
            "team_season_id", "game_id" }) })
public class TeamEvent {

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @ManyToOne
   @JoinColumn(name = "team_season_id", nullable = false)
   private TeamSeasonEntry teamSeason;

   @ManyToOne
   @JoinColumn(name = "game_id", nullable = false)
   private GameEntry event;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Alignment alignment;

   private int score;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private Outcome outcome;

   @Column(length = 100)
   private String scorekeeper;

   @Column(length = 100)
   private String timekeeper;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Creates a {@code TeamEvent} with the given information.
    *
    * @param id
    * @param teamSeason
    * @param event
    * @param eventIndex
    */
   public TeamEvent(String id, TeamSeasonEntry teamSeason, GameEntry event, int eventIndex) {
      this.id = id;
      this.teamSeason = teamSeason;
      this.event = event;

      teamSeason.getEvents().put(event.getStartsAt(), this);
      event.getTeams().add(eventIndex, this);
   }

   /**
    * Creates an empty {@code TeamEvent}. Internal use only.
    */
   protected TeamEvent() {
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
   public void SetId(String id) {
      this.id = id;
   }

   /**
    * Returns the assciated team season. Never null.
    *
    * @return
    */
   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   /**
    * Sets the associated team season. Must not be null.
    *
    * @param teamSeason
    */
   public void setTeamSeason(TeamSeasonEntry teamSeason) {
      assert teamSeason != null;
      this.teamSeason = teamSeason;
   }

   /**
    * Returns the associated game. Never null.
    *
    * @return
    */
   public GameEntry getEvent() {
      return event;
   }

   /**
    * Sets the associated game. Must not be null.
    *
    * @param event
    */
   public void setEvent(GameEntry event) {
      assert event != null;
      this.event = event;
   }

   /**
    * Returns the team alignment. Never null.
    *
    * @return
    */
   public Alignment getAlignment() {
      return alignment;
   }

   /**
    * Sets the team alignment. Must not be null.
    *
    * @param alignment
    */
   public void setAlignment(Alignment alignment) {
      assert alignment != null;
      this.alignment = alignment;
   }

   /**
    * Returns the team score.
    *
    * @return
    */
   public int getScore() {
      return score;
   }

   /**
    * Sets the team score.
    *
    * @param score
    */
   public void setScore(int score) {
      this.score = score;
   }

   /**
    * Returns the game outcome.
    *
    * @return
    */
   public Outcome getOutcome() {
      return outcome;
   }

   /**
    * Sets the game outcome.
    *
    * @param outcome
    */
   public void setOutcome(Outcome outcome) {
      this.outcome = outcome;
   }

   /**
    * Returns the score keeper's name, or null.
    *
    * @return
    */
   public String getScorekeeper() {
      return scorekeeper;
   }

   /**
    * Sets the score keeper name. Use null if unknown.
    *
    * @param scorekeeper
    */
   public void setScorekeeper(String scorekeeper) {
      this.scorekeeper = scorekeeper;
   }

   /**
    * Returns the penalty timekeeper, or null.
    *
    * @return
    */
   public String getTimekeeper() {
      return timekeeper;
   }

   /**
    * Sets the penalty timekeeper name. Use null if none or unknown.
    *
    * @param timekeeper
    */
   public void setTimekeeper(String timekeeper) {
      this.timekeeper = timekeeper;
   }

   /**
    * Returns the date and time this team event was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this team event was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this team event.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this team event.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this team event was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this team event was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modifie this team event.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modifie this team event.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
