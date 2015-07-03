package laxstats.api.games;

import java.io.Serializable;

import laxstats.api.players.Role;
import laxstats.query.games.GameEntry;
import laxstats.query.players.PlayerEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code AttendeeDTO} transfers information about an attendee between the application and domain
 * layers.
 */
public class AttendeeDTO implements Serializable {
   private static final long serialVersionUID = -6159149834654449174L;

   private final String id;
   private final GameEntry game;
   private final PlayerEntry player;
   private final TeamSeasonEntry teamSeason;
   private final Role role;
   private final AthleteStatus status;
   private final String name;
   private final String jerseyNumber;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates an {@code AttendeeDTO} with the given information.
    *
    * @param id
    * @param game
    * @param player
    * @param teamSeason
    * @param role
    * @param status
    * @param name
    * @param jerseyNumber
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public AttendeeDTO(String id, GameEntry game, PlayerEntry player, TeamSeasonEntry teamSeason,
      Role role, AthleteStatus status, String name, String jerseyNumber, LocalDateTime createdAt,
      UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.id = id;
      this.game = game;
      this.player = player;
      this.teamSeason = teamSeason;
      this.role = role;
      this.status = status;
      this.name = name;
      this.jerseyNumber = jerseyNumber;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates an {@code AttendeeDTO} with the given information.
    *
    * @param id
    * @param game
    * @param player
    * @param teamSeason
    * @param role
    * @param status
    * @param name
    * @param jerseyNumber
    * @param modifiedAt
    * @param modifiedBy
    */
   public AttendeeDTO(String id, GameEntry game, PlayerEntry player, TeamSeasonEntry teamSeason,
      Role role, AthleteStatus status, String name, String jerseyNumber, LocalDateTime modifiedAt,
      UserEntry modifiedBy) {
      this(id, game, player, teamSeason, role, status, name, jerseyNumber, null, null, modifiedAt,
         modifiedBy);
   }

   /**
    * Returns the attendee's unique identifier.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the associated game.
    *
    * @return
    */
   public GameEntry getGame() {
      return game;
   }

   /**
    * Returns the attendee's associated player.
    *
    * @return
    */
   public PlayerEntry getPlayer() {
      return player;
   }

   /**
    * Returns the attendee's associated team season.
    *
    * @return
    */
   public TeamSeasonEntry getTeamSeason() {
      return teamSeason;
   }

   /**
    * Returns the attendee's role.
    *
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Returns the attendee's playing status.
    *
    * @return
    */
   public AthleteStatus getStatus() {
      return status;
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
    * Returns the attendee's jersey number.
    *
    * @return
    */
   public String getJerseyNumber() {
      return jerseyNumber;
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
    * Returns the user who first persisted this attendee.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
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
    * Returns the user who last modified this attendee.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
