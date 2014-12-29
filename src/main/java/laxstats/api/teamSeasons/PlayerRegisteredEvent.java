package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerDTO;

public class PlayerRegisteredEvent {
	private final TeamSeasonId teamSeasonId;
	private final PlayerDTO playerDTO;

	public PlayerRegisteredEvent(TeamSeasonId teamSeasonId, PlayerDTO playerDTO) {
		this.teamSeasonId = teamSeasonId;
		this.playerDTO = playerDTO;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public PlayerDTO getPlayerDTO() {
		return playerDTO;
	}
}
