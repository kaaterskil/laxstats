package laxstats.api.games;

import java.io.Serializable;

import laxstats.query.games.AttendeeEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code PlayParticipantDTO} transfers information about a play participant between the application
 * and domain layers.
 */
public class PlayParticipantDTO implements Serializable {
   private static final long serialVersionUID = 260256944212826012L;

   private final String id;
   private final String playId;
   private final AttendeeEntry attendee;
   private final TeamSeasonEntry teamSeason;
   private final PlayRole role;
   private final boolean pointCredit;
   private int cumulativeAssists;
   private int cumulativeGoals;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code PlayParticipantDTO} with the given information.
    *
    * @param id
    * @param playId
    * @param attendee
    * @param teamSeason
    * @param role
    * @param pointCredit
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public PlayParticipantDTO(String id, String playId, AttendeeEntry attendee,
      TeamSeasonEntry teamSeason, PlayRole role, boolean pointCredit, LocalDateTime createdAt,
      UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.id = id;
      this.playId = playId;
      this.attendee = attendee;
      this.teamSeason = teamSeason;
      this.role = role;
      this.pointCredit = pointCredit;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code PlayParticipantDTO} with the given iformation.
    *
    * @param id
    * @param playId
    * @param attendee
    * @param teamSeason
    * @param role
    * @param pointCredit
    * @param modifiedAt
    * @param modifiedBy
    */
   public PlayParticipantDTO(String id, String playId, AttendeeEntry attendee,
      TeamSeasonEntry teamSeason, PlayRole role, boolean pointCredit, LocalDateTime modifiedAt,
      UserEntry modifiedBy) {
      this(id, playId, attendee, teamSeason, role, pointCredit, null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns this play participant's unique identifier.
    * 
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the unique identifier of the play.
    * 
    * @return
    */
   public String getPlayId() {
      return playId;
   }

   /**
    * Returns the play participant's associated game attendee.
    * 
    * @return
    */
   public AttendeeEntry getAttendee() {
      return attendee;
   }

   /**
    * Returns the play participant's associated team season.
    *
    * @return
    */
   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   /**
    * Returns the role the play participant had in this play.
    *
    * @return
    */
   public PlayRole getRole() {
      return role;
   }

   /**
    * Returns true if this play results in a point credit for this play participant, false
    * otherwise.
    *
    * @return
    */
   public boolean isPointCredit() {
      return pointCredit;
   }

   /**
    * Returns the cumulative assists by this play participant.
    *
    * @return
    */
   public int getCumulativeAssists() {
      return cumulativeAssists;
   }

   /**
    * Sets the cumulative assists by this play participant.
    *
    * @param cumulativeAssists
    */
   public void setCumulativeAssists(int cumulativeAssists) {
      this.cumulativeAssists = cumulativeAssists;
   }

   /**
    * Returns the cumulative goals scored by this play participant.
    *
    * @return
    */
   public int getCumulativeGoals() {
      return cumulativeGoals;
   }

   /**
    * Sets the cumulative goals scored by this play participant.
    *
    * @param cumulativeGoals
    */
   public void setCumulativeGoals(int cumulativeGoals) {
      this.cumulativeGoals = cumulativeGoals;
   }

   /**
    * Returns the date and tim ehtis play participant was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
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
    * Returns the date and time this play particpnt was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this play participant.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
