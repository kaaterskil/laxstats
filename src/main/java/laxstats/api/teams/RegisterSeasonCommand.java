package laxstats.api.teams;

import laxstats.domain.teams.TeamSeasonInfo;

public class RegisterSeasonCommand extends AbstractTeamCommand {
	private final TeamSeasonInfo season;

	public RegisterSeasonCommand(TeamId teamId, TeamSeasonInfo season) {
		super(teamId);
		this.season = season;
	}

	public TeamSeasonInfo getSeason() {
		return season;
	}
}
