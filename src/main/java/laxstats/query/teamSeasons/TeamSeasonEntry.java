package laxstats.query.teamSeasons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.events.TeamEvent;
import laxstats.query.players.PlayerEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "team_seasons", indexes = {
		@Index(name = "team_season_idx1", columnList = "id, team, season"),
		@Index(name = "team_season_idx2", columnList = "affiliation") }, uniqueConstraints = {
		@UniqueConstraint(name = "team_season_uk1", columnNames = { "team",
				"season" }),
		@UniqueConstraint(name = "team_season_uk2", columnNames = { "startsOn",
				"endsOn" }) })
public class TeamSeasonEntry {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private TeamEntry team;

	@ManyToOne
	@JoinColumn(nullable = false)
	private SeasonEntry season;

	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startsOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endsOn;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private TeamStatus status;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	@OneToMany(mappedBy = "teamSeason")
	private final List<PlayerEntry> roster = new ArrayList<>();

	@OneToMany(mappedBy = "teamSeason")
	private final Map<LocalDateTime, TeamEvent> events = new HashMap<>();

	// ---------- Methods ---------- //

	public Interval getSeasonInterval() {
		return new Interval(startsOn.toDateTimeAtStartOfDay(),
				endsOn.toDateTimeAtStartOfDay());
	}

	public boolean addPlayerToRoster(PlayerEntry player) {
		player.setTeamSeason(this);
		return roster.add(player);
	}

	// ---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public void setTeam(TeamEntry team) {
		this.team = team;
	}

	public SeasonEntry getSeason() {
		return season;
	}

	public void setSeason(SeasonEntry season) {
		this.season = season;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public void setStartsOn(LocalDate startsOn) {
		this.startsOn = startsOn;
	}

	public LocalDate getEndsOn() {
		return endsOn;
	}

	public void setEndsOn(LocalDate endsOn) {
		this.endsOn = endsOn;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public void setStatus(TeamStatus status) {
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

	public List<PlayerEntry> getRoster() {
		return roster;
	}

	public Map<LocalDateTime, TeamEvent> getEvents() {
		return events;
	}
}
