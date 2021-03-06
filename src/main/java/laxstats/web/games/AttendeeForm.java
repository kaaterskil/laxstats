package laxstats.web.games;

import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.games.AthleteStatus;
import laxstats.api.players.Role;
import laxstats.query.players.PlayerEntry;

/**
 * {@code AttendeeForm} contains information with which to create and update a game attendee.
 */
public class AttendeeForm implements AttendeeResource {
   private String id;

   @NotNull
   private String playerId;

   @NotNull
   private String gameId;

   @NotNull
   private Role role;

   private AthleteStatus status;

   private Map<String, PlayerEntry> roster;

   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPlayerId() {
      return playerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getGameId() {
      return gameId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setGameId(String gameId) {
      this.gameId = gameId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Role getRole() {
      return role;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setRole(Role role) {
      this.role = role;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public AthleteStatus getStatus() {
      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
