package laxstats.api.leagues;

public class LeagueDeletedEvent {
	private final LeagueId leagueId;

	public LeagueDeletedEvent(LeagueId leagueId) {
		this.leagueId = leagueId;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}
}
