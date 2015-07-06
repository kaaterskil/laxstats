package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.AthleteStatus;
import laxstats.api.players.Role;

/**
 * {@code AttendeeResource} represents a game attendee resource for remote clients.
 */
public class AttendeeResourceImpl implements AttendeeResource {
   private String id;

   @NotNull
   private String playerId;

   @NotNull
   private String gameId;

   @NotNull
   private Role role;

   private AthleteStatus status;

   /**
    * Creates an {@code AttendeeResource} with the given information.
    *
    * @param id
    * @param playerId
    * @param gameId
    * @param role
    * @param status
    */
   public AttendeeResourceImpl(String id, String playerId, String gameId, Role role,
      AthleteStatus status) {
      this.id = id;
      this.playerId = playerId;
      this.gameId = gameId;
      this.role = role;
      this.status = status;
   }

   /**
    * Creates an empty {@code AttendeeResource} for internal use.
    */
   public AttendeeResourceImpl() {
   }

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
      assert playerId != null;
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
      assert gameId != null;
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
      assert role != null;
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
}
