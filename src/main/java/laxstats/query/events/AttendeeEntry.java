package laxstats.query.events;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.events.AthleteStatus;
import laxstats.api.players.Role;
import laxstats.query.players.PlayerEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "event_attendees", indexes = {
		@Index(name = "event_attendees_idx1", columnList = "event, player, team_season"),
		@Index(name = "event_attendees_idx2", columnList = "team_season, event"),
		@Index(name = "event_attendees_idx3", columnList = "player, event"),
		@Index(name = "event_attendees_idx4", columnList = "event, player"),
		@Index(name = "event_attendees_idx5", columnList = "event, team_season"),
		@Index(name = "event_attendees_idx6", columnList = "role"),
		@Index(name = "event_attendees_idx7", columnList = "status") }, uniqueConstraints = { @UniqueConstraint(name = "event_attendees_uk1", columnNames = {
		"player", "event", "team_season" }) })
public class AttendeeEntry {
	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private EventEntry event;

	@ManyToOne
	private PlayerEntry player;

	@ManyToOne
	private TeamSeasonEntry teamSeason;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private AthleteStatus status;

	@Column(length = 100)
	private String name;

	@Column(length = 4)
	private String jerseyNumber;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	// ---------- Constructors ----------//

	public AttendeeEntry(EventEntry event, PlayerEntry player,
			TeamSeasonEntry teamSeason) {
		this.event = event;
		this.player = player;
		this.teamSeason = teamSeason;
	}

	protected AttendeeEntry() {
	}

	public String label() {
		final StringBuilder sb = new StringBuilder();
		boolean concat = false;
		if (jerseyNumber != null) {
			sb.append(jerseyNumber);
			concat = true;
		}
		if (name != null) {
			if (concat) {
				sb.append(" ");
			}
			sb.append(name);
		}
		final String result = sb.toString();
		return result.length() > 0 ? result : "Unknown player";
	}

	/* ---------- Getter/Setters ---------- */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EventEntry getEvent() {
		return event;
	}

	public void setEvent(EventEntry event) {
		this.event = event;
	}

	public PlayerEntry getPlayer() {
		return player;
	}

	public void setPlayer(PlayerEntry player) {
		this.player = player;
	}

	public TeamSeasonEntry getTeamSeason() {
		return teamSeason;
	}

	public void setTeamSeason(TeamSeasonEntry teamSeason) {
		this.teamSeason = teamSeason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(String jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AthleteStatus getStatus() {
		return status;
	}

	public void setStatus(AthleteStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntry createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntry modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
