package laxstats.web.players;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

/**
 * {@code PlayerForm} contains user-defined information to create or update a player.
 */
public class PlayerForm implements Serializable {
   private static final long serialVersionUID = -1822868866195304483L;

   private String id;

   @NotNull
   private String person;

   @NotNull
   private String teamSeason;

   @NotNull
   private Role role = Role.ATHLETE;

   @NotNull
   private PlayerStatus status = PlayerStatus.ACTIVE;

   private String jerseyNumber;
   private Position position;
   private boolean captain;
   private int depth = 1;
   private int height;
   private int weight;

   private List<Role> roles;
   private List<PlayerStatus> statuses;
   private List<Position> positions;

   /**
    * Returns this player's primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets this player's primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the primary key of the associated person.
    *
    * @return
    */
   public String getPerson() {
      return person;
   }

   /**
    * Sets the primary key of the associated person.
    *
    * @param person
    */
   public void setPerson(String person) {
      this.person = person;
   }

   /**
    * Returns the primary key of the associated team season.
    *
    * @return
    */
   public String getTeamSeason() {
      return teamSeason;
   }

   /**
    * Sets the primary key of the associated team season.
    *
    * @param teamSeason
    */
   public void setTeamSeason(String teamSeason) {
      this.teamSeason = teamSeason;
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
    * Sets the player's role.
    *
    * @param role
    */
   public void setRole(Role role) {
      this.role = role;
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
    * Sets the player's playing status.
    *
    * @param status
    */
   public void setStatus(PlayerStatus status) {
      this.status = status;
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
    * Sets the player's jersey number.
    *
    * @param jerseyNumber
    */
   public void setJerseyNumber(String jerseyNumber) {
      this.jerseyNumber = jerseyNumber;
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
    * Sets the player's position.
    *
    * @param position
    */
   public void setPosition(Position position) {
      this.position = position;
   }

   /**
    * Returns true if the player is a team captain, false otherwise.
    *
    * @return
    */
   public boolean isCaptain() {
      return captain;
   }

   /**
    * Sets a flag to determine if the player is a team captain.
    *
    * @param captain
    */
   public void setCaptain(boolean captain) {
      this.captain = captain;
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
    * Sets the player's depth on the roster.
    *
    * @param depth
    */
   public void setDepth(int depth) {
      this.depth = depth;
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
    * Sets the player's height, in inches.
    *
    * @param height
    */
   public void setHeight(int height) {
      this.height = height;
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
    * Sets the player's weight, in pounds.
    *
    * @param weight
    */
   public void setWeight(int weight) {
      this.weight = weight;
   }

   /*---------- Select element option values ----------*/

   public List<Role> getRoles() {
      if (roles == null) {
         roles = Arrays.asList(Role.values());
      }
      return roles;
   }

   public List<PlayerStatus> getStatuses() {
      if (statuses == null) {
         statuses = Arrays.asList(PlayerStatus.values());
      }
      return statuses;
   }

   public List<Position> getPositions() {
      if (positions == null) {
         positions = Arrays.asList(Position.values());
      }
      return positions;
   }
}
