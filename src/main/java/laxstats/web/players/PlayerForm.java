package laxstats.web.players;

import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

public class PlayerForm {
	@NotNull
	private String teamSeasonId;

	@NotNull
	private String personId;
	private Role role;

	@NotNull
	private PlayerStatus status;

	@NotNull
	private String jerseyNumber;
	private Position position;
	private boolean isCaptain;
	private int depth;
	private int height;
	private int weight;
	private Map<String, String> seasons;
	private Map<String, String> people;

	public String getTeamSeasonId() {
		return teamSeasonId;
	}

	public void setTeamSeasonId(String teamSeasonId) {
		this.teamSeasonId = teamSeasonId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(String jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isCaptain() {
		return isCaptain;
	}

	public void setCaptain(boolean isCaptain) {
		this.isCaptain = isCaptain;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Map<String, String> getSeasons() {
		return seasons;
	}

	public void setSeasons(Map<String, String> seasons) {
		this.seasons = seasons;
	}

	public Map<String, String> getPeople() {
		return people;
	}

	public void setPeople(Map<String, String> people) {
		this.people = people;
	}
}
