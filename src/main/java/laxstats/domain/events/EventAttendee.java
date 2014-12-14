package laxstats.domain.events;

import java.io.Serializable;

import laxstats.domain.people.Person;
import laxstats.domain.teams.Role;

import org.joda.time.LocalDateTime;

public class EventAttendee {

	public enum Status {
		STARTED, PLAYED
	}

	public static class Id implements Serializable {
		private static final long serialVersionUID = 1582104599083216078L;
		private String personId;
		private String eventId;

		public Id() {
		}

		public Id(String personId, String eventId) {
			this.personId = personId;
			this.eventId = eventId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof EventAttendee.Id) {
				Id that = (Id) o;
				return this.personId.equals(that.personId)
						&& this.eventId.equals(that.eventId);
			}
			return false;
		}

		public int hashCode() {
			return personId.hashCode() + eventId.hashCode();
		}
	}

	private EventAttendee.Id id = new Id();
	private Person person;
	private Event event;
	private String teamId;
	private Role role;
	private Status status;
	private LocalDateTime createdAt;
	private String createdBy;
}
