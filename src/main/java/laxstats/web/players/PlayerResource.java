package laxstats.web.players;

import javax.validation.constraints.NotNull;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

/**
 * {@code PlayerResource} represents a player resource for remote clients.
 */
public class PlayerResource {

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
   private boolean captain = false;
   private int depth;
   private int height;
   private int weight;

   /**
    * Creates a {@code PlayerResource} with the given information.
    * 
    * @param id
    * @param person
    * @param teamSeason
    * @param role
    * @param status
    * @param jerseyNumber
    * @param position
    * @param captain
    * @param depth
    * @param height
    * @param weight
    */
   public PlayerResource(String id, String person, String teamSeason, Role role,
      PlayerStatus status, String jerseyNumber, Position position, boolean captain, int depth,
      int height, int weight) {
      this.id = id;
      this.person = person;
      this.teamSeason = teamSeason;
      this.role = role;
      this.status = status;
      this.jerseyNumber = jerseyNumber;
      this.position = position;
      this.captain = captain;
      this.depth = depth;
      this.height = height;
      this.weight = weight;
   }

   /**
    * Creates an empty {@code PlayerResource} for internal use.
    */
   public PlayerResource() {
   }

   /**
    * Returns this player's unique identifier, or null if the player has not been persisted.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets this player's unique identifier. Use null if the player has not been persisted.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated person. Never null.
    *
    * @return
    */
   public String getPerson() {
      return person;
   }

   /**
    * Sets the identifier of the associated person. Must not be null.
    *
    * @param person
    */
   public void setPerson(String person) {
      assert person != null;
      this.person = person;
   }

   /**
    * Returns the identifier of the associated team season. Never null.
    *
    * @return
    */
   public String getTeamSeason() {
      return teamSeason;
   }

   /**
    * Sets the identifier of the associated team season. Must not be null.
    *
    * @param teamSeason
    */
   public void setTeamSeason(String teamSeason) {
      assert teamSeason != null;
      this.teamSeason = teamSeason;
   }

   /**
    * Returns the player's role. Never null.
    *
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Sets the player's role. Must not be null.
    *
    * @param role
    */
   public void setRole(Role role) {
      assert role != null;
      this.role = role;
   }

   /**
    * Returns the player's playing status. Never null.
    *
    * @return
    */
   public PlayerStatus getStatus() {
      return status;
   }

   /**
    * Sets the player's playing status. Must not be null.
    *
    * @param status
    */
   public void setStatus(PlayerStatus status) {
      assert status != null;
      this.status = status;
   }

   /**
    * Returns the player's jersey number or null.
    *
    * @return
    */
   public String getJerseyNumber() {
      return jerseyNumber;
   }

   /**
    * Sets the player's jersey number. Use null if unknown.
    *
    * @param jerseyNumber
    */
   public void setJerseyNumber(String jerseyNumber) {
      this.jerseyNumber = jerseyNumber;
   }

   /**
    * Returns the player's position, or null.
    *
    * @return
    */
   public Position getPosition() {
      return position;
   }

   /**
    * Sets the player's position. Use null if unknown.
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
    * Sets a flag to determine if the player is a team captain. Defaults to false.
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
    * Sets the player's depth on the roster. Defaults to zero.
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
    * Sets the player's height, in inches. Defaults to zero.
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
    * Sets the player's weight, in pounds. Defaults to zero.
    *
    * @param weight
    */
   public void setWeight(int weight) {
      this.weight = weight;
   }
}
