package laxstats.domain.teams;

import org.joda.time.LocalDate;

public class TeamSeasonInfo {
	private final String id;
	private final LocalDate startsOn;
	private final LocalDate endsOn;

	public TeamSeasonInfo(String id, LocalDate startsOn, LocalDate endsOn) {
		this.id = id;
		this.startsOn = startsOn;
		this.endsOn = endsOn;
	}

	public String getId() {
		return id;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public LocalDate getEndsOn() {
		return endsOn;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TeamSeasonInfo) {
			final TeamSeasonInfo that = (TeamSeasonInfo) obj;
			return this.id.equals(that.id);
		}
		return false;
	}
}
