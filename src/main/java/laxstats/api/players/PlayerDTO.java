package laxstats.api.players;

import laxstats.query.people.PersonEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class PlayerDTO {
	private final String id;
	private final PersonEntry person;
	private final TeamSeasonEntry team;
	private final Role role;
	private final PlayerStatus status;
	private final String jerseyNumber;
	private final Position position;
	private final boolean isCaptain;
	private final int depth;
	private final int height;
	private final int weight;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public PlayerDTO(String id, PersonEntry person, TeamSeasonEntry team,
			Role role, PlayerStatus status, String jerseyNumber,
			Position position, boolean isCaptain, int depth, int height,
			int weight, LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.person = person;
		this.team = team;
		this.role = role;
		this.status = status;
		this.jerseyNumber = jerseyNumber;
		this.position = position;
		this.isCaptain = isCaptain;
		this.depth = depth;
		this.height = height;
		this.weight = weight;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public String getId() {
		return id;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public TeamSeasonEntry getTeam() {
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

}
