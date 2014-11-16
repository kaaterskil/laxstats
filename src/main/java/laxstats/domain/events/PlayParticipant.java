package laxstats.domain.events;

import java.io.Serializable;

import laxstats.domain.people.Person;
import laxstats.domain.teams.Team;

import org.joda.time.LocalDateTime;

public class PlayParticipant {

	public enum Role {
		SCORER, ASSIST, GOALIE, SHOOTER, BLOCKER, GROUND_BALL, PENALTY_COMMITTED_BY, PENALTY_COMMITTED_AGAINST, FACEOFF_WINNER, FACEOFF_LOSER;
	}

	public static class Id implements Serializable {
		private static final long serialVersionUID = 912492002722920566L;
		private String playId;
		private String personId;
		private String teamId;

		public Id() {
		}

		public Id(String playId, String personId, String teamId) {
			this.playId = playId;
			this.personId = personId;
			this.teamId = teamId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof PlayParticipant.Id) {
				Id that = (Id) o;
				return this.playId.equals(that.playId)
						&& this.personId.equals(that.personId)
						&& this.teamId.equals(that.teamId);
			}
			return false;
		}

		public int hashCode() {
			return playId.hashCode() + personId.hashCode() + teamId.hashCode();
		}
	}

	private Id id = new Id();
	private Play play;
	private Person person;
	private Team team;
	private PlayParticipant.Role role;
	private boolean pointCredit = false;
	private int cumulativeAssists;
	private int cumulativeGoals;
	private LocalDateTime createdAt;
	private String createdBy;
}
