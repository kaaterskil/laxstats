package laxstats.web.events;

import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.events.AthleteStatus;
import laxstats.api.players.Role;
import laxstats.query.players.PlayerEntry;

public class AttendeeForm {
	private String id;

	@NotNull
	private String playerId;

	@NotNull
	private Role role;

	private AthleteStatus status;
	private Map<String, PlayerEntry> roster;

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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

	/*---------- Drop-down menu methods ----------*/

	public Map<String, PlayerEntry> getRoster() {
		return roster;
	}

	public void setRoster(Map<String, PlayerEntry> roster) {
		this.roster = roster;
	}
}
