package laxstats.domain.players;

import laxstats.api.players.PlayerCreated;
import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerDeleted;
import laxstats.api.players.PlayerId;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.PlayerUpdated;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * {@code Player} represents a domain object model of a person assigned to a particular team season
 * and roster. A player may have a role other than athlete, such a coach, manager, etc. A person may
 * be associated with several seasons on a given team, and may be associated with several teams,
 * although not simultaneously.
 */
public class Player extends AbstractAnnotatedAggregateRoot<PlayerId> {
   private static final long serialVersionUID = 1754082091210983057L;

   @AggregateIdentifier
   private PlayerId id;

   private String personId;
   private String teamSeasonId;
   private Role role;
   private PlayerStatus status;
   private String jerseyNumber;
   private Position position;
   private boolean isCaptain;
   private int depth;
   private int height;
   private int weight;

   /**
    * Creates a {@code Player} and applies the given aggregate identifier and player information.
    *
    * @param playerId
    * @param playerDTO
    */
   public Player(PlayerId playerId, PlayerDTO playerDTO) {
      apply(new PlayerCreated(playerId, playerDTO));
   }

   /**
    * Creates a {@code Player}. Internal user only.
    */
   protected Player() {
   }

   /**
    * Instructs the framework to change the state of the player.
    *
    * @param playerDTO
    */
   public void update(PlayerDTO playerDTO) {
      apply(new PlayerUpdated(id, playerDTO));
   }

   /**
    * Instructs the framework to delete the player.
    */
   public void delete() {
      apply(new PlayerDeleted(id));
   }

   /**
    * Stores and persists the player with information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PlayerCreated event) {
      final PlayerId identifier = event.getPlayerId();
      final PlayerDTO dto = event.getPlayerDTO();

      id = identifier;
      personId = dto.getPerson().getId();
      teamSeasonId = dto.getTeam().getId();
      role = dto.getRole();
      status = dto.getStatus();
      jerseyNumber = dto.getJerseyNumber();
      position = dto.getPosition();
      isCaptain = dto.isCaptain();
      depth = dto.getDepth();
      height = dto.getHeight();
      weight = dto.getWeight();
   }

   /**
    * Updates and persists the player with information contained in the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PlayerUpdated event) {
      final PlayerDTO dto = event.getPlayerDTO();

      personId = dto.getPerson().getId();
      teamSeasonId = dto.getTeam().getId();
      role = dto.getRole();
      status = dto.getStatus();
      jerseyNumber = dto.getJerseyNumber();
      position = dto.getPosition();
      isCaptain = dto.isCaptain();
      depth = dto.getDepth();
      height = dto.getHeight();
      weight = dto.getWeight();
   }

   /**
    * Marks the player for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(PlayerDeleted event) {
      markDeleted();
   }

   /**
    * Returns the player's aggregate identifier.
    *
    * @return
    */
   public PlayerId getId() {
      return id;
   }

   /**
    * Returns the identifier of the associated person aggregate.
    *
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Returns the identifier of the associated team season aggregate.
    *
    * @return
    */
   public String getTeamSeasonId() {
      return teamSeasonId;
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
    * Returns the player's depth in the roster.
    *
    * @return
    */
   public int getDepth() {
      return depth;
   }

   /**
    * Returns the player's height, in inches.
    *
    * @return
    */
   public int getHeight() {
      return height;
   }

   /**
    * Returns the player's weight, in pounds.
    *
    * @return
    */
   public int getWeight() {
      return weight;
   }
}
