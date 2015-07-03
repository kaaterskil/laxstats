package laxstats.query.games;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayRole;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.games.Strength;
import laxstats.api.utils.Constants;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;
import laxstats.query.violations.ViolationEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * {@code PlayEntry} represents the abstract base query object model of a play.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "play_type", length = 20, discriminatorType = DiscriminatorType.STRING)
@Table(name = "plays",
         indexes = { @Index(name = "play_idx1", columnList = "play_type"),
            @Index(name = "play_idx2", columnList = "game_id, play_type"),
            @Index(name = "play_idx3", columnList = "period"),
            @Index(name = "play_idx4", columnList = "strength"),
            @Index(name = "play_idx5", columnList = "playKey"),
            @Index(name = "play_idx6", columnList = "result"),
            @Index(name = "play_idx7", columnList = "playKey, result"),
            @Index(name = "play_idx8", columnList = "period, elapsedTime") },
         uniqueConstraints = { @UniqueConstraint(name = "play_uk1", columnNames = { "game_id",
            "sequenceNumber" }) })
abstract public class PlayEntry implements Serializable {
   private static final long serialVersionUID = -9074132185978497348L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   protected String id;

   @ManyToOne
   @JoinColumn(name = "game_id")
   protected GameEntry event;

   @Column(name = "play_type", insertable = false, updatable = false)
   protected String playType;

   protected int sequenceNumber;

   @ManyToOne
   @JoinColumn(name = "team_season_id")
   protected TeamSeasonEntry team;

   protected int period;

   @Type(type = Constants.TIME_PERIOD_DATABASE_TYPE)
   protected Period elapsedTime;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   protected PlayKey playKey;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   protected ScoreAttemptType scoreAttemptType;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   protected PlayResult result;

   @Column(columnDefinition = "text")
   protected String comment;

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "play")
   protected final List<PlayParticipantEntry> participants = new ArrayList<>();

   /*----- Goal properties -----*/

   protected int teamScore;

   protected int opponentScore;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   protected Strength strength = Strength.EVEN_STRENGTH;

   protected int manUpAdvantage;

   @ManyToOne
   @JoinColumn(name = "man_up_team_id")
   protected TeamSeasonEntry manUpTeam;

   /*----- Penalty properties -----*/

   @ManyToOne
   @JoinColumn(name = "violation_id")
   protected ViolationEntry violation;

   @Type(type = Constants.TIME_PERIOD_DATABASE_TYPE)
   protected Period duration;

   /*----- Audit properties -----*/

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   protected LocalDateTime createdAt;

   @ManyToOne
   protected UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   protected LocalDateTime modifiedAt;

   @ManyToOne
   protected UserEntry modifiedBy;

   /**
    * Returns a portion of the url string specific to the play class.
    *
    * @return
    */
   public String getUrlType() {
      String result = null;

      if (this instanceof ClearEntry) {
         result = "clears";
      }
      else if (this instanceof FaceOffEntry) {
         result = "faceOffs";
      }
      else if (this instanceof GoalEntry) {
         result = "goals";
      }
      else if (this instanceof GroundBallEntry) {
         result = "groundBalls";
      }
      else if (this instanceof PenaltyEntry) {
         result = "penalties";
      }
      else if (this instanceof ShotEntry) {
         result = "shots";
      }
      else {
         throw new IllegalStateException("no playType defined");
      }
      return result;
   }

   /**
    * Adds the given play participant to this play.
    *
    * @param participant
    */
   public void addParticipant(PlayParticipantEntry participant) {
      participant.setPlay(this);
      participants.add(participant);
   }

   /**
    * Removes the given play participant from this play.
    *
    * @param participant
    */
   public void removeParticipant(PlayParticipantEntry participant) {
      participant.clear();
      participants.remove(participant);
   }

   /**
    * Returns the play participant if and only if the participant is either a scorer, face-off
    * winner or shooter, otherwise null.
    *
    * @return
    */
   public PlayParticipantEntry getListedPlayer() {
      PlayParticipantEntry result = null;
      for (final PlayParticipantEntry each : participants) {
         if (each.getRole().equals(PlayRole.SCORER) ||
            each.getRole().equals(PlayRole.FACEOFF_WINNER) ||
            each.getRole().equals(PlayRole.SHOOTER)) {
            result = each;
         }
      }
      return result;
   }

   /**
    * Clears the current play.
    */
   public void clear() {
      event = null;
      team = null;
      manUpTeam = null;
      createdBy = null;
      modifiedBy = null;

      for (final PlayParticipantEntry each : participants) {
         each.clear();
      }
      participants.clear();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return "Play[id=" + id + ", event=" + event + ", playTye=" + playType + ", sequenceNumber=" +
         sequenceNumber + ", team=" + team + ", elapsedtime=" + elapsedTime + ", playKey=" +
         playKey + ", scoreAttemptType=" + scoreAttemptType + ", result=" + result + ", comment=" +
         comment + ", teamScore=" + teamScore + ", opponentScore=" + opponentScore + ", strenght=" +
         strength + ", manUpAdvantage=" + manUpAdvantage + ", manUpTeam=" + manUpTeam +
         ", violation=" + violation + ", duration=" + duration + "]";
   }

   /**
    * Returns the play's primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the play's primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the class type of the play.
    *
    * @return
    */
   public String getPlayType() {
      return playType;
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
    * Returns the play sequence number.
    *
    * @return
    */
   public int getSequenceNumber() {
      return sequenceNumber;
   }

   /**
    * Sets the play sequence number.
    *
    * @param sequenceNumber
    */
   public void setSequenceNumber(int sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
   }

   /**
    * Returns the team season responsible for the play.
    *
    * @return
    */
   public TeamSeasonEntry getTeam() {
      return team;
   }

   /**
    * Sets the team season responsible for the play.
    *
    * @param team
    */
   public void setTeam(TeamSeasonEntry team) {
      this.team = team;
   }

   /**
    * Returns the play period.
    *
    * @return
    */
   public int getPeriod() {
      return period;
   }

   /**
    * Sets the play period.
    *
    * @param period
    */
   public void setPeriod(int period) {
      this.period = period;
   }

   /**
    * Returns the elapsed time between the play and the beginning of the period.
    *
    * @return
    */
   public Period getElapsedTime() {
      return elapsedTime;
   }

   /**
    * Sets the elapsed time between the beginning of the period and the play.
    *
    * @param elapsedTime
    */
   public void setElapsedTime(Period elapsedTime) {
      this.elapsedTime = elapsedTime;
   }

   /**
    * Returns the play key. Never null.
    *
    * @return
    */
   public PlayKey getPlayKey() {
      return playKey;
   }

   /**
    * Sets the play key. Must not be null.
    *
    * @param playKey
    */
   public void setPlayKey(PlayKey playKey) {
      assert playKey != null;
      this.playKey = playKey;
   }

   /**
    * Returns the score attempt type, or null if not a shot or goal.
    *
    * @return
    */
   public ScoreAttemptType getScoreAttemptType() {
      return scoreAttemptType;
   }

   /**
    * Sets the score attempt type. Use null if not a shot or goal.
    *
    * @param scoreAttemptType
    */
   public void setScoreAttemptType(ScoreAttemptType scoreAttemptType) {
      this.scoreAttemptType = scoreAttemptType;
   }

   /**
    * Returns the play outcome.
    *
    * @return
    */
   public PlayResult getResult() {
      return result;
   }

   /**
    * Sets the play outcome.
    *
    * @param result
    */
   public void setResult(PlayResult result) {
      this.result = result;
   }

   /**
    * Returns the team score at the time of this play.
    *
    * @return
    */
   public int getTeamScore() {
      return teamScore;
   }

   /**
    * Sets the team score at the time of this play.
    *
    * @param teamScore
    */
   public void setTeamScore(int teamScore) {
      this.teamScore = teamScore;
   }

   /**
    * Returns the opponent score at the time of this play.
    *
    * @return
    */
   public int getOpponentScore() {
      return opponentScore;
   }

   /**
    * Sets the opponent score at the time of this play.
    *
    * @param opponentScore
    */
   public void setOpponentScore(int opponentScore) {
      this.opponentScore = opponentScore;
   }

   /**
    * Returns the team strength at the time of this play.
    *
    * @return
    */
   public Strength getStrength() {
      return strength;
   }

   /**
    * Sets the team strength at the time of this play. Defaults to Stength.EVEN_STRENGTH.
    *
    * @param strength
    */
   public void setStrength(Strength strength) {
      this.strength = strength;
   }

   /**
    * Returns the amn-up advantage at the time of this play.
    *
    * @return
    */
   public int getManUpAdvantage() {
      return manUpAdvantage;
   }

   /**
    * Sets the man-up advantage at the time of this play.
    *
    * @param manUpAdvantage
    */
   public void setManUpAdvantage(int manUpAdvantage) {
      this.manUpAdvantage = manUpAdvantage;
   }

   /**
    * Returns the man-up team at the time of this play, or null if unknwon or the teams are at even
    * strength.
    *
    * @return
    */
   public TeamSeasonEntry getManUpTeam() {
      return manUpTeam;
   }

   /**
    * Sets the man-up team at the time of this play. Use null if unknown or the two teams are at
    * even strength.
    *
    * @param manUpTeam
    */
   public void setManUpTeam(TeamSeasonEntry manUpTeam) {
      this.manUpTeam = manUpTeam;
   }

   /**
    * Returns the associated violation, or null if this play is not a penalty.
    *
    * @return
    */
   public ViolationEntry getViolation() {
      return violation;
   }

   /**
    * Sets the associated violation. Use null if this play is not a penalty.
    *
    * @param violation
    */
   public void setViolation(ViolationEntry violation) {
      this.violation = violation;
   }

   /**
    * Returns the period of time for this play, or null if not a penalty.
    *
    * @return
    */
   public Period getDuration() {
      return duration;
   }

   /**
    * Sets the period of time for this play. Use null if the play is not a penalty.
    *
    * @param duration
    */
   public void setDuration(Period duration) {
      this.duration = duration;
   }

   /**
    * Returns comments, or null.
    *
    * @return
    */
   public String getComment() {
      return comment;
   }

   /**
    * Sets comments. Use null for none.
    *
    * @param comment
    */
   public void setComment(String comment) {
      this.comment = comment;
   }

   /**
    * Returns the date and time this play was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this play was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this play.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the use who first persisted this play.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this play was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this play aws last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modifie this play.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this play.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   /**
    * Returns a list of play participants.
    *
    * @return
    */
   public List<PlayParticipantEntry> getParticipants() {
      return participants;
   }
}
