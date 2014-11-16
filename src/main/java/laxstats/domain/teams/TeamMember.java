package laxstats.domain.teams;

import java.io.Serializable;

import laxstats.domain.Season;
import laxstats.domain.people.Person;

import org.joda.time.LocalDateTime;

public class TeamMember {

	public enum Status {
		ACTIVE, INJURED, TRYOUT, INACTIVE;
	}

	public static class Id implements Serializable {
		private static final long serialVersionUID = 4232283469443735627L;
		private String personId;
		private String seasonId;
		private String teamId;

		public Id() {
		}

		public Id(String personId, String seasonId, String teamId) {
			this.personId = personId;
			this.seasonId = seasonId;
			this.teamId = teamId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof TeamMember.Id) {
				Id that = (Id) o;
				return this.personId.equals(that.personId)
						&& this.seasonId.equals(that.seasonId)
						&& this.teamId.equals(that.teamId);
			}
			return false;
		}

		public int hashCode() {
			return personId.hashCode() + seasonId.hashCode()
					+ teamId.hashCode();
		}
	}

	private TeamMember.Id id = new Id();
	private Person person;
	private Season season;
	private Team team;
	private Role role;
	private Status status;
	private String jerseyNumber;
	private Position position;
	private boolean isCaptain;
	private int depth;
	private int height;
	private int weight;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
