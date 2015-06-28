package laxstats.api.games;

import laxstats.query.games.AttendeeEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class PlayParticipantDTO {
	private final String id;
	private final String playId;
	private final AttendeeEntry attendee;
	private final TeamSeasonEntry teamSeason;
	private final PlayRole role;
	private final boolean pointCredit;
	private int cumulativeAssists;
	private int cumulativeGoals;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public PlayParticipantDTO(String id, String playId, AttendeeEntry attendee,
			TeamSeasonEntry teamSeason, PlayRole role, boolean pointCredit,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.playId = playId;
		this.attendee = attendee;
		this.teamSeason = teamSeason;
		this.role = role;
		this.pointCredit = pointCredit;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public PlayParticipantDTO(String id, String playId, AttendeeEntry attendee,
			TeamSeasonEntry teamSeason, PlayRole role, boolean pointCredit,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(id, playId, attendee, teamSeason, role, pointCredit, null, null,
				modifiedAt, modifiedBy);
	}

	public String getId() {
		return id;
	}

	public String getPlayId() {
		return playId;
	}

	public AttendeeEntry getAttendee() {
		return attendee;
	}

	public TeamSeasonEntry getTeamSeason() {
		return teamSeason;
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

	public void setCumulativeAssists(int cumulativeAssists) {
		this.cumulativeAssists = cumulativeAssists;
	}

	public int getCumulativeGoals() {
		return cumulativeGoals;
	}

	public void setCumulativeGoals(int cumulativeGoals) {
		this.cumulativeGoals = cumulativeGoals;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}
}
