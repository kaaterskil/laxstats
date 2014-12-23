package laxstats.domain.players;

import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.domain.teams.Team;

public class Player {
	private final String id;
	private final String personId;
	private final String seasonId;
	private final Team team;
	private final Role role;
	private final PlayerStatus status;
	private final String jerseyNumber;
	private final Position position;
	private final boolean isCaptain;
	private final int depth;
	private final int height;
	private final int weight;

	public Player(Team team, PlayerDTO dto) {
		this.team = team;

		id = dto.getId();
		personId = dto.getPerson().getId();
		seasonId = dto.getTeam().getId();
		role = dto.getRole();
		status = dto.getStatus();
		jerseyNumber = dto.getJerseyNumber();
		position = dto.getPosition();
		isCaptain = dto.isCaptain();
		depth = dto.getDepth();
		height = dto.getHeight();
		weight = dto.getWeight();
	}

	public String getId() {
		return id;
	}

	public String getPersonId() {
		return personId;
	}

	public String getSeasonId() {
		return seasonId;
	}

	public Team getTeam() {
		return team;
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
