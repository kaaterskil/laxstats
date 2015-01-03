package laxstats.api.events;

import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.PlayEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class PlayParticipantDTO {
	private final String id;
	private final PlayEntry play;
	private final AttendeeEntry attendee;
	private final TeamSeasonEntry teamSeason;
	private final PlayRole role;
	private final boolean pointCredit;
	private final int cumulativeAssists;
	private final int cumulativeGoals;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public PlayParticipantDTO(String id, PlayEntry play,
			AttendeeEntry attendee, TeamSeasonEntry teamSeason, PlayRole role,
			boolean pointCredit, int cumulativeAssists, int cumulativeGoals,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.play = play;
		this.attendee = attendee;
		this.teamSeason = teamSeason;
		this.role = role;
		this.pointCredit = pointCredit;
		this.cumulativeAssists = cumulativeAssists;
		this.cumulativeGoals = cumulativeGoals;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public PlayParticipantDTO(String id, PlayEntry play,
			AttendeeEntry attendee, TeamSeasonEntry teamSeason, PlayRole role,
			boolean pointCredit, int cumulativeAssists, int cumulativeGoals,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(id, play, attendee, teamSeason, role, pointCredit,
				cumulativeAssists, cumulativeGoals, null, null, modifiedAt,
				modifiedBy);
	}

	public String getId() {
		return id;
	}

	public PlayEntry getPlay() {
		return play;
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

	public int getCumulativeGoals() {
		return cumulativeGoals;
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
