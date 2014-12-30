package laxstats.domain.events;

import laxstats.api.events.AthleteStatus;
import laxstats.api.players.Role;

public class AttendeeInfo {
	private final String playerId;
	private final String teamId;
	private final String name;
	private final String jerseyNumber;
	private final Role role;
	private final AthleteStatus status;

	public AttendeeInfo(String playerId, String teamId, String name,
			String jerseyNumber, Role role, AthleteStatus status) {
		this.playerId = playerId;
		this.teamId = teamId;
		this.name = name;
		this.jerseyNumber = jerseyNumber;
		this.role = role;
		this.status = status;
	}

	public String getPlayerId() {
		return playerId;
	}

	public String getTeamId() {
		return teamId;
	}

	public String getName() {
		return name;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public Role getRole() {
		return role;
	}

	public AthleteStatus getStatus() {
		return status;
	}
}
