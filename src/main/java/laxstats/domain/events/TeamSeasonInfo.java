package laxstats.domain.events;

import laxstats.api.events.Alignment;

public class TeamSeasonInfo {
	private final String teamSeasonId;
	private final String name;
	private final Alignment alignment;

	public TeamSeasonInfo(String teamSeasonId, String name,
			Alignment alignment) {
		this.teamSeasonId = teamSeasonId;
		this.name = name;
		this.alignment = alignment;
	}

	public String getTeamSeasonId() {
		return teamSeasonId;
	}

	public String getName() {
		return name;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	@Override
	public int hashCode() {
		return teamSeasonId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TeamSeasonInfo) {
			final TeamSeasonInfo that = (TeamSeasonInfo) obj;
			return this.teamSeasonId.equals(that.teamSeasonId);
		}
		return false;
	}
}
