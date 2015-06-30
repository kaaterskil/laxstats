package laxstats.domain.teamSeasons;

import java.io.Serializable;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

/**
 * {@code PlayerInfo} is a value object representing a player within the team season aggregate.
 */
public class PlayerInfo implements Serializable {
   private static final long serialVersionUID = -8363669733253962936L;

   private final String id;
   private final String personId;
   private final Role role;
   private final PlayerStatus status;
   private final String jerseyNumber;
   private final Position position;

   /**
    * Creates a {@code PlayerInfo} with the given information.
    *
    * @param id
    * @param personId
    * @param role
    * @param status
    * @param jerseyNumber
    * @param position
    */
   public PlayerInfo(String id, String personId, Role role, PlayerStatus status,
      String jerseyNumber, Position position) {
      this.id = id;
      this.personId = personId;
      this.role = role;
      this.status = status;
      this.jerseyNumber = jerseyNumber;
      this.position = position;
   }

   /**
    * Returns the player's unique identifier.
    * 
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the identifier of the person aggregate represented by this player.
    * 
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Returns the player's role (e.g. Role.ATHLETE)
    * 
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Returns the player's status this season.
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
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return id.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof PlayerInfo) {
         final PlayerInfo that = (PlayerInfo)obj;
         return id.equals(that.id);
      }
      return false;
   }
}
