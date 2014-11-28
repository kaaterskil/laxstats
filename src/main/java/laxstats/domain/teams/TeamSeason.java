package laxstats.domain.teams;

import java.io.Serializable;

import laxstats.domain.seasons.Season;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class TeamSeason {
	
	public enum Status {
		ACTIVE, INACTIVE;
	}

	public static class Id implements Serializable {
		private static final long serialVersionUID = 6990697052332076930L;
		private String teamId;
		private String seasonId;
		
		public Id(){}
		
		public Id(String teamId, String seasonId) {
			this.teamId = teamId;
			this.seasonId = seasonId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof TeamSeason.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId) && this.seasonId.equals(that.seasonId);
			}
			return false;
		}
		
		public int hashCode() {
			return teamId.hashCode() + seasonId.hashCode();
		}
	}

	private TeamSeason.Id id = new Id();
	private Team team;
	private Season season;
	private Affiliation affiliation;
	private LocalDate startsOn;
	private LocalDate endsOn;
	private TeamSeason.Status status;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
