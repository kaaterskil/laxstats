package laxstats.api.games;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import laxstats.query.games.GameEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;
import laxstats.query.violations.ViolationEntry;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * {@code PlayDTO} transfers information about a play between the application and domain layers.
 */
public class PlayDTO implements Serializable {
   private static final long serialVersionUID = 5695289556868237567L;

   private final String identifier;
   private final String discriminator;
   private final PlayKey playKey;
   private final GameEntry event;
   private final TeamSeasonEntry team;
   private final int period;
   private final Period elapsedTime;
   private final ScoreAttemptType attemptType;
   private final PlayResult result;
   private final ViolationEntry violation;
   private final Period penaltyDuration;
   private final String comment;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;
   private List<PlayParticipantDTO> participants = new ArrayList<>();

   // Computed values
   private int sequence = -1;
   private int teamScore = 0;
   private int opponentScore = 0;
   private Strength strength = Strength.EVEN_STRENGTH;
   private int manUpAdvantage = 0;
   private TeamSeasonEntry manUpTeam;

   /**
    * Creates a {@code PlayDTO} with the given information.
    *
    * @param identifier
    * @param discriminator
    * @param playKey
    * @param event
    * @param team
    * @param period
    * @param elapsedTime
    * @param attemptType
    * @param result
    * @param violation
    * @param duration
    * @param comment
    * @param sequence
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    * @param participants
    */
   public PlayDTO(String identifier, String discriminator, PlayKey playKey, GameEntry event,
      TeamSeasonEntry team, int period, Period elapsedTime, ScoreAttemptType attemptType,
      PlayResult result, ViolationEntry violation, Period duration, String comment, int sequence,
      LocalDateTime createdAt, UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy,
      List<PlayParticipantDTO> participants) {
      this.identifier = identifier;
      this.discriminator = discriminator;
      this.playKey = playKey;
      this.event = event;
      this.team = team;
      this.period = period;
      this.elapsedTime = elapsedTime;
      this.attemptType = attemptType;
      this.result = result;
      this.violation = violation;
      penaltyDuration = duration;
      this.comment = comment;
      this.sequence = sequence;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
      this.participants = participants;
   }

   /**
    * Creates a {@code PlayDTO} with the given information.
    *
    * @param identifier
    * @param discriminator
    * @param playKey
    * @param event
    * @param team
    * @param period
    * @param elapsedTime
    * @param attemptType
    * @param result
    * @param violation
    * @param duration
    * @param comment
    * @param sequence
    * @param modifiedAt
    * @param modifiedBy
    * @param participants
    */
   public PlayDTO(String identifier, String discriminator, PlayKey playKey, GameEntry event,
      TeamSeasonEntry team, int period, Period elapsedTime, ScoreAttemptType attemptType,
      PlayResult result, ViolationEntry violation, Period duration, String comment, int sequence,
      LocalDateTime modifiedAt, UserEntry modifiedBy, List<PlayParticipantDTO> participants) {
      this(identifier, discriminator, playKey, event, team, period, elapsedTime, attemptType,
         result, violation, duration, comment, sequence, null, null, modifiedAt, modifiedBy,
         participants);
   }

   /**
    * Returns the period of time between the play and the beginning of the game.
    *
    * @return
    */
   public Period getTotalElapsedTime() {
      return PlayUtils.getTotalElapsedTime(period, elapsedTime);
   }

   /**
    * Returns the instant of time of the play.
    *
    * @return
    */
   public DateTime getInstant() {
      return PlayUtils.getInstant(event.getStartsAt(), period, elapsedTime);
   }

   /**
    * Returns the unique identifier of the play.
    *
    * @return
    */
   public String getIdentifier() {
      return identifier;
   }

   /**
    * Returns the type of play.
    *
    * @return
    */
   public String getDiscriminator() {
      return discriminator;
   }

   /**
    * Returns more information about the type of play.
    *
    * @return
    */
   public PlayKey getPlayKey() {
      return playKey;
   }

   /**
    * Returns the gae associated with the play.
    *
    * @return
    */
   public GameEntry getEvent() {
      return event;
   }

   /**
    * Returns the associated team making the play.
    *
    * @return
    */
   public TeamSeasonEntry getTeam() {
      return team;
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
    * Returns the elapsed time beyween the play and the beginning of the play period.
    *
    * @return
    */
   public Period getElapsedTime() {
      return elapsedTime;
   }

   /**
    * Returns the type of score attempt, if the play is a shot or goal.
    *
    * @return
    */
   public ScoreAttemptType getAttemptType() {
      return attemptType;
   }

   /**
    * Returns the result of the play, or null if none.
    *
    * @return
    */
   public PlayResult getResult() {
      return result;
   }

   /**
    * Returns the associated violation, or null if the play is not a penalty.
    *
    * @return
    */
   public ViolationEntry getViolation() {
      return violation;
   }

   /**
    * Returns the penalty duration as a time period, or null if the play is not a penalty.
    *
    * @return
    */
   public Period getPenaltyDuration() {
      return penaltyDuration;
   }

   /**
    * Returns play comments.
    *
    * @return
    */
   public String getComment() {
      return comment;
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
    * Returns the user who first persisted this play.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
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
    * Returns the user who last odified this play.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Returns the list of play participants.
    *
    * @return
    */
   public List<PlayParticipantDTO> getParticipants() {
      return participants;
   }

   /*---------- Getter/Setters for computed values ----------*/

   /**
    * Returns this plays sequence in the game.
    *
    * @return
    */
   public int getSequence() {
      return sequence;
   }

   /**
    * Sets the play sequence for this play in the game.
    *
    * @param sequence
    */
   public void setSequence(int sequence) {
      this.sequence = sequence;
   }

   /**
    * Returns the team score at the time of the play.
    *
    * @return
    */
   public int getTeamScore() {
      return teamScore;
   }

   /**
    * Sets the team score at the time of the play.
    *
    * @param teamScore
    */
   public void setTeamScore(int teamScore) {
      this.teamScore = teamScore;
   }

   /**
    * Returns the opponent score at the time of the play.
    *
    * @return
    */
   public int getOpponentScore() {
      return opponentScore;
   }

   /**
    * Sets the opponent score at the time of the play.
    *
    * @param opponentScore
    */
   public void setOpponentScore(int opponentScore) {
      this.opponentScore = opponentScore;
   }

   /**
    * Returns the strength of the team making the play.
    *
    * @return
    */
   public Strength getStrength() {
      return strength;
   }

   /**
    * Sets the strength of the team making the play.
    *
    * @param strength
    */
   public void setStrength(Strength strength) {
      this.strength = strength;
   }

   /**
    * Returns the man-up advantage at the time of the play, or zero if equal stength.
    *
    * @return
    */
   public int getManUpAdvantage() {
      return manUpAdvantage;
   }

   /**
    * Sets the man-up advantage at the time of the play, or zero if equal strength.
    *
    * @param manUpAdvantage
    */
   public void setManUpAdvantage(int manUpAdvantage) {
      this.manUpAdvantage = manUpAdvantage;
   }

   /**
    * Returns the man-up team at the time of the play, or null if none.
    *
    * @return
    */
   public TeamSeasonEntry getManUpTeam() {
      return manUpTeam;
   }

   /**
    * Sets the man-up team at the time of the play. Use null if none.
    *
    * @param manUpTeam
    */
   public void setManUpTeam(TeamSeasonEntry manUpTeam) {
      this.manUpTeam = manUpTeam;
   }
}
