package laxstats.domain.teams;

import java.io.Serializable;

import laxstats.domain.leagues.League;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class TeamAffiliation {

	public static class Id implements Serializable {
		private static final long serialVersionUID = 5408146241619855006L;
		private String teamId;
		private String affiliationId;

		public Id() {
		}

		public Id(String teamId, String affiliationId) {
			this.teamId = teamId;
			this.affiliationId = affiliationId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof TeamAffiliation.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId)
						&& this.affiliationId.equals(that.affiliationId);
			}
			return false;
		}

		public int hashCode() {
			return teamId.hashCode() + affiliationId.hashCode();
		}
	}

	private TeamAffiliation.Id id = new Id();
	private Team team;
	private League affiliation;
	private LocalDate startingOn;
	private LocalDate endingOn;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
