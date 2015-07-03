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

import laxstats.api.games.AthleteStatus;
import laxstats.api.players.Role;
import laxstats.api.utils.Constants;
import laxstats.query.players.PlayerEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code AttendeeEntry} represents the query object model of a game attendee, primarily a player,
 * for the purpose of participating in plays.
 */
@Entity
@Table(name = "event_attendees",
         indexes = {
            @Index(name = "event_attendees_idx1", columnList = "game_id, player_id, team_season_id"),
            @Index(name = "event_attendees_idx2", columnList = "team_season_id, game_id"),
            @Index(name = "event_attendees_idx3", columnList = "player_id, game_id"),
            @Index(name = "event_attendees_idx4", columnList = "game_id, player_id"),
            @Index(name = "event_attendees_idx5", columnList = "game_id, team_season_id"),
            @Index(name = "event_attendees_idx6", columnList = "role"),
            @Index(name = "event_attendees_idx7", columnList = "status") },
         uniqueConstraints = { @UniqueConstraint(name = "event_attendees_uk1", columnNames = {
            "player_id", "game_id", "team_season_id" }) })
public class AttendeeEntry implements Serializable {
   private static final long serialVersionUID = -1637974392983471162L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @ManyToOne
   @JoinColumn(name = "game_id", nullable = false)
   private GameEntry event;

   @ManyToOne
   @JoinColumn(name = "player_id")
   private PlayerEntry player;

   @ManyToOne
   @JoinColumn(name = "team_season_id")
   private TeamSeasonEntry teamSeason;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING, nullable = false)
   private Role role;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private AthleteStatus status;

   @Column(length = 100)
   private String name;

   @Column(length = 4)
   private String jerseyNumber;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Creates an {@code AttendeeEntry} with the given information.
    *
    * @param event
    * @param player
    * @param teamSeason
    */
   public AttendeeEntry(GameEntry event, PlayerEntry player, TeamSeasonEntry teamSeason) {
      this.event = event;
      this.player = player;
      this.teamSeason = teamSeason;
   }

   /**
    * Creates an empty {@code AttendeeEntry} for internal use.
    */
   protected AttendeeEntry() {
   }

   /**
    * Returns a concatenation of the attendee's jersey number and name suitable for use in a
    * drop-down menu.
    *
    * @return
    */
   public String label() {
      final StringBuilder sb = new StringBuilder();
      boolean concat = false;
      if (jerseyNumber != null) {
         sb.append(jerseyNumber);
         concat = true;
      }
      if (name != null) {
         if (concat) {
            sb.append(" ");
         }
         sb.append(name);
      }
      final String result = sb.toString();
      return result.length() > 0 ? result : "Unknown player";
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
    * Returns the attendee's associated player. Never null.
    *
    * @return
    */
   public PlayerEntry getPlayer() {
      return player;
   }

   /**
    * Sets the attendee's associated player.
    *
    * @param player
    */
   public void setPlayer(PlayerEntry player) {
      assert player != null;
      this.player = player;
   }

   /**
    * Returns the attendee's associated team season. Never null.
    *
    * @return
    */
   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   /**
    * Sets the attendee's associated team season.
    *
    * @param teamSeason
    */
   public void setTeamSeason(TeamSeasonEntry teamSeason) {
      assert teamSeason != null;
      this.teamSeason = teamSeason;
   }

   /**
    * Returns the attendee's name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the attendee's name.
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Returns the attendee's jersey number.
    *
    * @return
    */
   public String getJerseyNumber() {
      return jerseyNumber;
   }

   /**
    * Sets the attendee's jersey number.
    *
    * @param jerseyNumber
    */
   public void setJerseyNumber(String jerseyNumber) {
      this.jerseyNumber = jerseyNumber;
   }

   /**
    * Returs the attendee's role. Never null.
    *
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Sets the attendee's role. Must not be null.
    *
    * @param role
    */
   public void setRole(Role role) {
      assert role != null;
      this.role = role;
   }

   /**
    * Returns the attendee's playing status, or null if not an athlete.
    *
    * @return
    */
   public AthleteStatus getStatus() {
      return status;
   }

   /**
    * Sets the attendee's playing status. Use null if not an athlete.
    *
    * @param status
    */
   public void setStatus(AthleteStatus status) {
      this.status = status;
   }

   /**
    * Returns the date and time this attendee was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this attendee was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this attendee.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this attendee.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this attendee was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this attendee was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this attendee.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this attendee.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
