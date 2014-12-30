package laxstats.web.events;

import java.util.Map;

import laxstats.api.events.AthleteStatus;
import laxstats.api.players.Role;

public class AttendeeForm {
	private String playerId;
	private String teamSeasonId;
	private Role role;
	private AthleteStatus status;
	private Map<String, String> playerData;
	private Map<String, String> teamSeasonData;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getTeamSeasonId() {
		return teamSeasonId;
	}

	public void setTeamSeasonId(String teamSeasonId) {
		this.teamSeasonId = teamSeasonId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AthleteStatus getStatus() {
		return status;
	}

	public void setStatus(AthleteStatus status) {
		this.status = status;
	}

	public Map<String, String> getPlayerData() {
		return playerData;
	}

	public void setPlayerData(Map<String, String> playerData) {
		this.playerData = playerData;
	}

	public Map<String, String> getTeamSeasonData() {
		return teamSeasonData;
	}

	public void setTeamSeasonData(Map<String, String> teamSeasonData) {
		this.teamSeasonData = teamSeasonData;
	}
}
