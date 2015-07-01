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

@Entity
@Table(name = "team_events",
         indexes = { @Index(name = "team_events_idx1", columnList = "outcome"),
            @Index(name = "team_events_idx2", columnList = "alignment") },
         uniqueConstraints = { @UniqueConstraint(name = "team_events_uk1", columnNames = {
            "team_season_id", "game_id" }) })
public class TeamEvent {

   @Id
   @Column(length = 36)
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

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /*---------- Constructor ----------*/

   public TeamEvent(String id, TeamSeasonEntry teamSeason, GameEntry event, int eventIndex) {
      this.id = id;
      this.teamSeason = teamSeason;
      this.event = event;

      teamSeason.getEvents().put(event.getStartsAt(), this);
      event.getTeams().add(eventIndex, this);
   }

   protected TeamEvent() {
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   public void setTeamSeason(TeamSeasonEntry teamSeason) {
      this.teamSeason = teamSeason;
   }

   public GameEntry getEvent() {
      return event;
   }

   public void setEvent(GameEntry event) {
      this.event = event;
   }

   public Alignment getAlignment() {
      return alignment;
   }

   public void setAlignment(Alignment alignment) {
      this.alignment = alignment;
   }

   public int getScore() {
      return score;
   }

   public void setScore(int score) {
      this.score = score;
   }

   public Outcome getOutcome() {
      return outcome;
   }

   public void setOutcome(Outcome outcome) {
      this.outcome = outcome;
   }

   public String getScorekeeper() {
      return scorekeeper;
   }

   public void setScorekeeper(String scorekeeper) {
      this.scorekeeper = scorekeeper;
   }

   public String getTimekeeper() {
      return timekeeper;
   }

   public void setTimekeeper(String timekeeper) {
      this.timekeeper = timekeeper;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   public UserEntry getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
