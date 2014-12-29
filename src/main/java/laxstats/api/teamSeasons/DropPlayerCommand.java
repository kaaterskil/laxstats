package laxstats.api.teamSeasons;

import laxstats.api.players.PlayerId;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DropPlayerCommand {
	@TargetAggregateIdentifier
	private final TeamSeasonId teamSeasonId;
	private final PlayerId playerId;

	public DropPlayerCommand(TeamSeasonId teamSeasonId, PlayerId playerId) {
		this.teamSeasonId = teamSeasonId;
		this.playerId = playerId;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public PlayerId getPlayerId() {
		return playerId;
	}
}
