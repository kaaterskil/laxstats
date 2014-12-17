package laxstats.api.leagues;

public class AffiliateLeagueRegisteredEvent {
	private final LeagueId leagueId;
	private final String affiliateId;

	public AffiliateLeagueRegisteredEvent(LeagueId leagueId, String affiliateId) {
		this.leagueId = leagueId;
		this.affiliateId = affiliateId;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}

	public String getAffiliateId() {
		return affiliateId;
	}
}
