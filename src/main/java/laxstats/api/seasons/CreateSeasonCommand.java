package laxstats.api.seasons;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateSeasonCommand {

	@TargetAggregateIdentifier
	private SeasonId seasonId;
	private SeasonDTO seasonDTO;

	public CreateSeasonCommand(SeasonId seasonId, SeasonDTO seasonDTO) {
		this.seasonId = seasonId;
		this.seasonDTO = seasonDTO;
	}

	public SeasonId getSeasonId() {
		return seasonId;
	}

	public SeasonDTO getSeasonDTO() {
		return seasonDTO;
	}
}
