package laxstats.query.events;

import java.io.Serializable;

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

import laxstats.api.events.PlayRole;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "play_participants", indexes = {
   @Index(name = "play_participants_idx1", columnList = "role"),
   @Index(name = "play_participants_idx2", columnList = "pointCredit"),
   @Index(name = "play_participants_idx3", columnList = "team_season_id") },
   uniqueConstraints = { @UniqueConstraint(name = "play_participants_uk1", columnNames = {
      "id", "play_id", "attendee_id" }) })
public class PlayParticipantEntry implements Serializable {
   private static final long serialVersionUID = -6213837582683090644L;

   @Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   @JoinColumn(name = "play_id", nullable = false)
   private PlayEntry play;

   @ManyToOne
   @JoinColumn(name = "attendee_id", nullable = false)
   private AttendeeEntry attendee;

   @ManyToOne
   @JoinColumn(name = "team_season_id")
   private TeamSeasonEntry teamSeason;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private PlayRole role;

   private boolean pointCredit = false;

   private int cumulativeAssists;

   private int cumulativeGoals;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /*---------- Methods ----------*/

   public void clear() {
      play = null;
      attendee = null;
      teamSeason = null;
      createdAt = null;
      modifiedBy = null;
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public PlayEntry getPlay() {
      return play;
   }

   public void setPlay(PlayEntry play) {
      this.play = play;
   }

   public AttendeeEntry getAttendee() {
      return attendee;
   }

   public void setAttendee(AttendeeEntry attendee) {
      this.attendee = attendee;
   }

   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   public void setTeamSeason(TeamSeasonEntry teamSeason) {
      this.teamSeason = teamSeason;
   }

   public PlayRole getRole() {
      return role;
   }

   public void setRole(PlayRole role) {
      this.role = role;
   }

   public boolean isPointCredit() {
      return pointCredit;
   }

   public void setPointCredit(boolean pointCredit) {
      this.pointCredit = pointCredit;
   }

   public int getCumulativeAssists() {
      return cumulativeAssists;
   }

   public void setCumulativeAssists(int cumulativeAssists) {
      this.cumulativeAssists = cumulativeAssists;
   }

   public int getCumulativeGoals() {
      return cumulativeGoals;
   }

   public void setCumulativeGoals(int cumulativeGoals) {
      this.cumulativeGoals = cumulativeGoals;
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
