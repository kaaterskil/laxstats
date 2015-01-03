package laxstats.domain.events;

import laxstats.api.events.PlayRole;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

public class PlayParticipant extends AbstractAnnotatedEntity {
	private String id;
	private String playId;
	private String attendeeId;
	private String teamSeasonId;
	private PlayRole role;
	private boolean pointCredit;
	private int cumulativeAssists;
	private int cumulativeGoals;

	/*---------- Constructors ----------*/

	public PlayParticipant(String id, String playId, String attendeeId,
			String teamSeasonId, PlayRole role, boolean pointCredit,
			int cumulativeAssists, int cumulativeGoals) {
		this.id = id;
		this.playId = playId;
		this.attendeeId = attendeeId;
		this.teamSeasonId = teamSeasonId;
		this.role = role;
		this.pointCredit = pointCredit;
		this.cumulativeAssists = cumulativeAssists;
		this.cumulativeGoals = cumulativeGoals;
	}

	protected PlayParticipant() {
	}

	/*---------- Methods ----------*/

	/*---------- Event Handlers ----------*/

	/*---------- Getters ----------*/

	public String getId() {
		return id;
	}

	public String getPlayId() {
		return playId;
	}

	public String getAttendeeId() {
		return attendeeId;
	}

	public String getSeasonTeamId() {
		return teamSeasonId;
	}

	public PlayRole getRole() {
		return role;
	}

	public boolean isPointCredit() {
		return pointCredit;
	}

	public int getCumulativeAssists() {
		return cumulativeAssists;
	}

	public int getCumulativeGoals() {
		return cumulativeGoals;
	}
}
