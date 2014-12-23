package laxstats.domain.teamSeasons;

import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;

public class PlayerInfo {
	private final String id;
	private final String personId;
	private final Role role;
	private final PlayerStatus status;
	private final String jerseyNumber;
	private final Position position;

	public PlayerInfo(String id, String personId, Role role,
			PlayerStatus status, String jerseyNumber, Position position) {
		this.id = id;
		this.personId = personId;
		this.role = role;
		this.status = status;
		this.jerseyNumber = jerseyNumber;
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public String getPersonId() {
		return personId;
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

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof PlayerInfo) {
			final PlayerInfo that = (PlayerInfo) obj;
			return this.id.equals(that.id);
		}
		return false;
	}
}
