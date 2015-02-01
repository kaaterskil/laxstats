package laxstats.api.events;

import laxstats.api.players.Role;
import laxstats.query.events.GameEntry;
import laxstats.query.players.PlayerEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class AttendeeDTO {
	private final String id;
	private final GameEntry event;
	private final PlayerEntry player;
	private final TeamSeasonEntry teamSeason;
	private final Role role;
	private final AthleteStatus status;
	private final String name;
	private final String jerseyNumber;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public AttendeeDTO(String id, GameEntry event, PlayerEntry player,
			TeamSeasonEntry teamSeason, Role role, AthleteStatus status,
			String name, String jerseyNumber, LocalDateTime createdAt,
			UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.event = event;
		this.player = player;
		this.teamSeason = teamSeason;
		this.role = role;
		this.status = status;
		this.name = name;
		this.jerseyNumber = jerseyNumber;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public AttendeeDTO(String id, GameEntry event, PlayerEntry player,
			TeamSeasonEntry teamSeason, Role role, AthleteStatus status,
			String name, String jerseyNumber, LocalDateTime modifiedAt,
			UserEntry modifiedBy) {
		this(id, event, player, teamSeason, role, status, name, jerseyNumber,
				null, null, modifiedAt, modifiedBy);
	}

	public String getId() {
		return id;
	}

	public GameEntry getEvent() {
		return event;
	}

	public PlayerEntry getPlayer() {
		return player;
	}

	public TeamSeasonEntry getTeamSeason() {
		return teamSeason;
	}

	public Role getRole() {
		return role;
	}

	public AthleteStatus getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
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
