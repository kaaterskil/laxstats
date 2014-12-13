package laxstats.api.seasons;

public class SeasonCreatedEvent {
	private SeasonId seasonId;
	private SeasonDTO seasonDTO;

	public SeasonCreatedEvent(SeasonId seasonId, SeasonDTO seasonDTO) {
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
