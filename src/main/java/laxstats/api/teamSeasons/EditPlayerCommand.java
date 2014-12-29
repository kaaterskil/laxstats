package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerDTO;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class EditPlayerCommand {
	@TargetAggregateIdentifier
	private final TeamSeasonId teamSeasonId;
	private final PlayerDTO playerDTO;

	public EditPlayerCommand(TeamSeasonId teamSeasonId, PlayerDTO playerDTO) {
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
