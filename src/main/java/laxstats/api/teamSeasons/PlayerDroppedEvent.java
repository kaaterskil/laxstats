package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerId;

public class PlayerDroppedEvent {
	private final TeamSeasonId teamSeasonId;
	private final PlayerId playerId;

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}

	public PlayerDroppedEvent(TeamSeasonId teamSeasonId, PlayerId playerId) {
		this.teamSeasonId = teamSeasonId;
		this.playerId = playerId;
	}
}
