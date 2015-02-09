package laxstats.web.players;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

public class PlayerForm {
	@NotNull
	private String personId;

	@NotNull
	private Role role = Role.ATHLETE;

	@NotNull
	private PlayerStatus status = PlayerStatus.ACTIVE;

	private String jerseyNumber;
	private Position position;
	private boolean captain;
	private int depth = 1;
	private int height;
	private int weight;
	private List<Role> roles;
	private List<PlayerStatus> statuses;
	private List<Position> positions;

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
		return captain;
	}

	public void setCaptain(boolean captain) {
		this.captain = captain;
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

	/*---------- Select element option values ----------*/

	public List<Role> getRoles() {
		if (roles == null) {
			roles = Arrays.asList(Role.values());
		}
		return roles;
	}

	public List<PlayerStatus> getStatuses() {
		if (statuses == null) {
			statuses = Arrays.asList(PlayerStatus.values());
		}
		return statuses;
	}

	public List<Position> getPositions() {
		if (positions == null) {
			positions = Arrays.asList(Position.values());
		}
		return positions;
	}
}
