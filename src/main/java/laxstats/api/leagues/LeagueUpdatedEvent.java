package laxstats.api.leagues;

public class LeagueUpdatedEvent {
	private final LeagueId leagueId;
	private final LeagueDTO leagueDTO;

	public LeagueUpdatedEvent(LeagueId leagueId, LeagueDTO leagueDTO) {
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
