package laxstats.domain.players;

import laxstats.api.players.PlayerCreatedEvent;
import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerDeletedEvent;
import laxstats.api.players.PlayerId;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.PlayerUpdatedEvent;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

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

	public Player(PlayerId playerId, PlayerDTO playerDTO) {
		apply(new PlayerCreatedEvent(playerId, playerDTO));
	}

	protected Player() {
	}

	/*---------- Methods ----------*/

	public void update(PlayerDTO playerDTO) {
		apply(new PlayerUpdatedEvent(id, playerDTO));
	}

	public void delete() {
		apply(new PlayerDeletedEvent(id));
	}

	/*---------- Event handlers ----------*/

	@EventSourcingHandler
	protected void handle(PlayerCreatedEvent event) {
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

	@EventSourcingHandler
	protected void handle(PlayerUpdatedEvent event) {
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

	@EventSourcingHandler
	protected void handle(PlayerDeletedEvent event) {
		markDeleted();
	}

	/*---------- Getters ----------*/

	public PlayerId getId() {
		return id;
	}

	public String getPersonId() {
		return personId;
	}

	public String getTeamSeasonId() {
		return teamSeasonId;
	}

	public Role getRole() {
		return role;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isCaptain() {
		return isCaptain;
	}

	public int getDepth() {
		return depth;
	}

	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}
}
