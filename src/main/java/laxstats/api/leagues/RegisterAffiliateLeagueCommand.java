package laxstats.api.leagues;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class RegisterAffiliateLeagueCommand {
	@TargetAggregateIdentifier
	private final LeagueId leagueId;
	private final LeagueId affiliateId;

	public RegisterAffiliateLeagueCommand(LeagueId leagueId,
			LeagueId affiliateId) {
		this.leagueId = leagueId;
		this.affiliateId = affiliateId;
	}

	public LeagueId getLeagueId() {
		return leagueId;
	}

	public LeagueId getAffiliateId() {
		return affiliateId;
	}
}
