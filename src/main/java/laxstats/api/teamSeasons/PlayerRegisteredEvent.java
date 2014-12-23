package laxstats.api.teamSeasons;

import laxstats.domain.teamSeasons.PlayerInfo;

public class PlayerRegisteredEvent {
	private final TeamSeasonId seasonId;
	private final PlayerInfo player;

	public PlayerRegisteredEvent(TeamSeasonId seasonId, PlayerInfo player) {
		this.seasonId = seasonId;
		this.player = player;
	}

	public TeamSeasonId getSeasonId() {
		return seasonId;
	}

	public PlayerInfo getPlayer() {
		return player;
	}
}
