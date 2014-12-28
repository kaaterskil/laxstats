package laxstats.domain.teamSeasons;

import org.joda.time.LocalDateTime;

public class EventInfo implements Comparable<EventInfo> {
	private final String eventId;
	private final String siteId;
	private final String teamOneId;
	private final String teamTwoId;
	private final LocalDateTime startsAt;

	public EventInfo(String eventId, String siteId, String teamOneId,
			String teamTwoId, LocalDateTime startsAt) {
		this.eventId = eventId;
		this.siteId = siteId;
		this.teamOneId = teamOneId;
		this.teamTwoId = teamTwoId;
		this.startsAt = startsAt;
	}

	public String getEventId() {
		return eventId;
	}

	public String getSiteId() {
		return siteId;
	}

	public String getTeamOneId() {
		return teamOneId;
	}

	public String getTeamTwoId() {
		return teamTwoId;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	@Override
	public int hashCode() {
		return eventId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof EventInfo) {
			final EventInfo that = (EventInfo) obj;
			return this.eventId.equals(that.eventId);
		}
		return false;
	}

	@Override
	public int compareTo(EventInfo o) {
		return this.startsAt.isBefore(o.startsAt) ? -1 : (this.startsAt
				.equals(o.startsAt) ? 0 : 1);
	}
}
