package laxstats.query.games;

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

import laxstats.api.games.PlayRole;
import laxstats.api.utils.Constants;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code PlayParticipantEntry} represents the query object model of a participant in a play.
 */
@Entity
@Table(name = "play_participants",
         indexes = { @Index(name = "play_participants_idx1", columnList = "role"),
            @Index(name = "play_participants_idx2", columnList = "pointCredit"),
            @Index(name = "play_participants_idx3", columnList = "team_season_id") },
         uniqueConstraints = { @UniqueConstraint(name = "play_participants_uk1", columnNames = {
            "id", "play_id", "attendee_id" }) })
public class PlayParticipantEntry implements Serializable {
   private static final long serialVersionUID = -6213837582683090644L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
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
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private PlayRole role;

   private boolean pointCredit = false;

   private int cumulativeAssists;

   private int cumulativeGoals;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Clears the values of this {@code PlayParticipantEntry}.
    */
   public void clear() {
      play = null;
      attendee = null;
      teamSeason = null;
      createdAt = null;
      modifiedBy = null;
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
    * Returns the associated play. Never null.
    * 
    * @return
    */
   public PlayEntry getPlay() {
      return play;
   }

   /**
    * Sets the associated play. Must not be null.
    * 
    * @param play
    */
   public void setPlay(PlayEntry play) {
      assert play != null;
      this.play = play;
   }

   /**
    * Returns the participant's associated game attendee. Never null.
    * 
    * @return
    */
   public AttendeeEntry getAttendee() {
      return attendee;
   }

   /**
    * Sets the participant's associated game attendee. Must not be null.
    * 
    * @param attendee
    */
   public void setAttendee(AttendeeEntry attendee) {
      assert attendee != null;
      this.attendee = attendee;
   }

   /**
    * Returns the participant's associated team season.
    * 
    * @return
    */
   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   /**
    * Sets the participant's associated team season.
    * 
    * @param teamSeason
    */
   public void setTeamSeason(TeamSeasonEntry teamSeason) {
      this.teamSeason = teamSeason;
   }

   /**
    * Returns the participants role in the play. Never null.
    * 
    * @return
    */
   public PlayRole getRole() {
      return role;
   }

   /**
    * Sets the participant's role in the play. Must not be null.
    * 
    * @param role
    */
   public void setRole(PlayRole role) {
      assert role != null;
      this.role = role;
   }

   /**
    * Returns true if this play results in a point credit for the participant.
    * 
    * @return
    */
   public boolean isPointCredit() {
      return pointCredit;
   }

   /**
    * Sets whether this play results in a point credit for the participant.
    * 
    * @param pointCredit
    */
   public void setPointCredit(boolean pointCredit) {
      this.pointCredit = pointCredit;
   }

   /**
    * Returns this participant's cumulative assists at the time of this play.
    * 
    * @return
    */
   public int getCumulativeAssists() {
      return cumulativeAssists;
   }

   /**
    * Sets this participant's cumulative assists at the time of this play.
    * 
    * @param cumulativeAssists
    */
   public void setCumulativeAssists(int cumulativeAssists) {
      this.cumulativeAssists = cumulativeAssists;
   }

   /**
    * Returns this participant's cumulative goals at the time of this play.
    * 
    * @return
    */
   public int getCumulativeGoals() {
      return cumulativeGoals;
   }

   /**
    * Sets this participant's cumulative goals at the time of this play.
    * 
    * @param cumulativeGoals
    */
   public void setCumulativeGoals(int cumulativeGoals) {
      this.cumulativeGoals = cumulativeGoals;
   }

   /**
    * Returns the date and time this play participant was first persisted.
    * 
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this play participant was first persisted.
    * 
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this play participant.
    * 
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this play participant.
    * 
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this play participant was last modified.
    * 
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this play participant was last modified.
    * 
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this play participant.
    * 
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this play participant.
    * 
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
