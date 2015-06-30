package laxstats.api.players;

import java.io.Serializable;

import laxstats.query.people.PersonEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code PlayerDTO} transfers information between the application and data layers.
 */
public class PlayerDTO implements Serializable {
   private static final long serialVersionUID = -1196079035339826618L;

   private final PlayerId id;
   private final PersonEntry person;
   private final TeamSeasonEntry team;
   private final String fullName;
   private final Role role;
   private final PlayerStatus status;
   private final String jerseyNumber;
   private final Position position;
   private final boolean isCaptain;
   private final int depth;
   private final int height;
   private final int weight;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code PlayerDTO} with the given information.
    *
    * @param id
    * @param person
    * @param team
    * @param fullName
    * @param role
    * @param status
    * @param jerseyNumber
    * @param position
    * @param isCaptain
    * @param depth
    * @param height
    * @param weight
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public PlayerDTO(PlayerId id, PersonEntry person, TeamSeasonEntry team, String fullName,
      Role role, PlayerStatus status, String jerseyNumber, Position position, boolean isCaptain,
      int depth, int height, int weight, LocalDateTime createdAt, UserEntry createdBy,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.id = id;
      this.person = person;
      this.team = team;
      this.fullName = fullName;
      this.role = role;
      this.status = status;
      this.jerseyNumber = jerseyNumber;
      this.position = position;
      this.isCaptain = isCaptain;
      this.depth = depth;
      this.height = height;
      this.weight = weight;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code PlayerDTO} with the given information.
    *
    * @param id
    * @param person
    * @param team
    * @param fullName
    * @param role
    * @param status
    * @param jerseyNumber
    * @param position
    * @param isCaptain
    * @param depth
    * @param height
    * @param weight
    * @param modifiedAt
    * @param modifiedBy
    */
   public PlayerDTO(PlayerId id, PersonEntry person, TeamSeasonEntry team, String fullName,
      Role role, PlayerStatus status, String jerseyNumber, Position position, boolean isCaptain,
      int depth, int height, int weight, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(id, person, team, fullName, role, status, jerseyNumber, position, isCaptain, depth,
         height, weight, null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns the aggregate identifier of this player.
    *
    * @return
    */
   public PlayerId getId() {
      return id;
   }

   /**
    * Returns the parent person.
    *
    * @return
    */
   public PersonEntry getPerson() {
      return person;
   }

   /**
    * Returns the associated team season.
    *
    * @return
    */
   public TeamSeasonEntry getTeam() {
      return team;
   }

   /**
    * Returns a concatenation of the player's full name.
    *
    * @return
    */
   public String getFullName() {
      return fullName;
   }

   /**
    * Returns the player's role.
    *
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Returns the player's playing status.
    *
    * @return
    */
   public PlayerStatus getStatus() {
      return status;
   }

   /**
    * Returns the player's jersey number.
    *
    * @return
    */
   public String getJerseyNumber() {
      return jerseyNumber;
   }

   /**
    * Returns the player's position.
    *
    * @return
    */
   public Position getPosition() {
      return position;
   }

   /**
    * Returns true if the player is a team captain, false otherwise.
    *
    * @return
    */
   public boolean isCaptain() {
      return isCaptain;
   }

   /**
    * Returns the player's depth on the roster, or zero if not known.
    *
    * @return
    */
   public int getDepth() {
      return depth;
   }

   /**
    * Returns the player's height, in inches, or zero if not known.
    *
    * @return
    */
   public int getHeight() {
      return height;
   }

   /**
    * Returns the player's weight, in pounds, or zero if not known.
    *
    * @return
    */
   public int getWeight() {
      return weight;
   }

   /**
    * Returns the date and time this player was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns the user who first persisted ths player.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns the date and time this player awas last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this player.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

}
