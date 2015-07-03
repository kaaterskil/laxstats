package laxstats.domain.games;

import laxstats.api.games.AthleteStatus;
import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.AttendeeUpdated;
import laxstats.api.players.Role;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

/**
 * {@code Attendee} represents the domain model object of a game attendee, primarily a player, for
 * the purpose of participating in plays.
 */
public class Attendee extends AbstractAnnotatedEntity {
   private String id;
   private String playerId;
   private String teamSeasonId;
   private String name;
   private String jerseyNumber;
   private Role role;
   private AthleteStatus status;

   /**
    * Creates an {@code Attendee} with the given information.
    *
    * @param id
    * @param playerId
    * @param teamSeasonId
    * @param name
    * @param jerseyNumber
    * @param role
    * @param status
    */
   public Attendee(String id, String playerId, String teamSeasonId, String name,
      String jerseyNumber, Role role, AthleteStatus status) {
      super();
      this.id = id;
      this.playerId = playerId;
      this.teamSeasonId = teamSeasonId;
      this.name = name;
      this.jerseyNumber = jerseyNumber;
      this.role = role;
      this.status = status;
   }

   /**
    * Creates an empty {@code Attendee}. Internal use only.
    */
   protected Attendee() {
   }

   /**
    * Updates and persists changes to the attendee with information contained in the given event.
    * This is a event handler is delegated by the aggregate.
    *
    * @param event
    */
   @EventHandler
   protected void handle(AttendeeUpdated event) {
      if (!id.equals(event.getAttendeeDTO().getId())) {
         return;
      }
      final AttendeeDTO dto = event.getAttendeeDTO();
      name = dto.getName();
      jerseyNumber = dto.getJerseyNumber();
      role = dto.getRole();
      status = dto.getStatus();
   }

   /**
    * Returns this unique identifier.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets this unique identifier.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated player.
    *
    * @return
    */
   public String getPlayerId() {
      return playerId;
   }

   /**
    * Sets the identifier of the associated player.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId) {
      assert playerId != null;
      this.playerId = playerId;
   }

   /**
    * Returns the identifier of the associated team season. Never null.
    *
    * @return
    */
   public String getTeamSeasonId() {
      return teamSeasonId;
   }

   /**
    * Sets the identifier of the associated team season.
    *
    * @param teamSeasonId
    */
   public void setTeamSeasonId(String teamSeasonId) {
      assert teamSeasonId != null;
      this.teamSeasonId = teamSeasonId;
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
    * Returns the attendee role.
    *
    * @return
    */
   public Role getRole() {
      return role;
   }

   /**
    * Sets the attendee role.
    *
    * @param role
    */
   public void setRole(Role role) {
      this.role = role;
   }

   /**
    * Returns the attendee status.
    *
    * @return
    */
   public AthleteStatus getStatus() {
      return status;
   }

   /**
    * Sets the attendee status.
    *
    * @param status
    */
   public void setStatus(AthleteStatus status) {
      this.status = status;
   }

}
