package laxstats.domain.teams;

import laxstats.api.teams.MemberStatus;
import laxstats.api.teams.TeamMemberDTO;

public class Player {
	private final String id;
	private final String personId;
	private final String seasonId;
	private final Team team;
	private final Role role;
	private final MemberStatus status;
	private final String jerseyNumber;
	private final Position position;
	private final boolean isCaptain;
	private final int depth;
	private final int height;
	private final int weight;

	public Player(Team team, TeamMemberDTO dto) {
		this.team = team;

		id = dto.getId();
		personId = dto.getPersonId();
		seasonId = dto.getSeasonId();
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

	public MemberStatus getStatus() {
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
