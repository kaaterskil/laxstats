package laxstats.web.games;

import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.games.AthleteStatus;
import laxstats.api.players.Role;
import laxstats.query.players.PlayerEntry;

/**
 * {@code AttendeeForm} contains information with which to create and update a game attendee.
 */
public class AttendeeForm {
   private String id;
   @NotNull
   private String playerId;
   @NotNull
   private Role role;
   private AthleteStatus status;

   private Map<String, PlayerEntry> roster;

   /**
    * Returns the attendee's unique identifier.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the attendee's unique identifier.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated player. Never null.
    *
    * @return
    */
   public String getPlayerId() {
      return playerId;
   }

   /**
    * Sets the identifier of the associated player. Must not be null.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   /**
    * Returns the game attendee's role. Never null.
    *
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Sets the game attendee's role. Must not be null.
    *
    * @param role
    */
   public void setRole(Role role) {
      this.role = role;
   }

   /**
    * Returns the game attendee's playing status, or null if not an athlete.
    *
    * @return
    */
   public AthleteStatus getStatus() {
      return status;
   }

   /**
    * Sets the game attendee's playing status. Use null if not an athlete.
    *
    * @param status
    */
   public void setStatus(AthleteStatus status) {
      this.status = status;
   }

   /*---------- Drop-down menu methods ----------*/

   public Map<String, PlayerEntry> getRoster() {
      return roster;
   }

   public void setRoster(Map<String, PlayerEntry> roster) {
      this.roster = roster;
   }
}
