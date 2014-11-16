package laxstats.domain.events;

import java.io.Serializable;

import laxstats.domain.teams.Team;

import org.joda.time.LocalDateTime;

public class TeamEvent {

	public enum Alignment {
		HOME, AWAY;
	}

	public enum Outcome {
		WIN, LOSS;
	}

	public static class Id implements Serializable {
		private static final long serialVersionUID = -3438452317292121148L;
		private String teamId;
		private String eventId;

		public Id() {
		}

		public Id(String teamId, String eventID) {
			this.teamId = teamId;
			this.eventId = eventID;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof TeamEvent.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId)
						&& this.eventId.equals(that.eventId);
			}
			return false;
		}

		public int hashCode() {
			return teamId.hashCode() + eventId.hashCode();
		}
	}

	private TeamEvent.Id id = new Id();
	private Team team;
	private Event event;
	private Alignment alignment;
	private int score;
	private Outcome outcome;
	private String scorekeeper;
	private String timekeeper;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
