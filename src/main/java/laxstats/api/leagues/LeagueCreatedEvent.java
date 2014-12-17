package laxstats.api.leagues;

public class LeagueCreatedEvent {
	private final LeagueId leagueId;
	private final LeagueDTO leagueDTO;

	public LeagueCreatedEvent(LeagueId leagueId, LeagueDTO leagueDTO) {
		this.leagueId = leagueId;
		this.leagueDTO = leagueDTO;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}

	public LeagueDTO getLeagueDTO() {
		return leagueDTO;
	}
}
