package laxstats.api.teamSeasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateTeamSeasonCommand {
	@TargetAggregateIdentifier
	private final TeamSeasonId teamSeasonId;
	private final TeamSeasonDTO teamSeasonDTO;

	public UpdateTeamSeasonCommand(TeamSeasonId teamSeasonId,
			TeamSeasonDTO teamSeasonDTO) {
		this.teamSeasonId = teamSeasonId;
		this.teamSeasonDTO = teamSeasonDTO;
	}

	public TeamSeasonId getTeamSeasonId() {
		return teamSeasonId;
	}

	public TeamSeasonDTO getTeamSeasonDTO() {
		return teamSeasonDTO;
	}
}
