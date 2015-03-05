package laxstats.query.teamSeasons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.players.PlayerEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "team_seasons", indexes = {
		@Index(name = "team_season_idx1", columnList = "id, team_id, season_id"),
		@Index(name = "team_season_idx2", columnList = "affiliation_id") }, uniqueConstraints = { @UniqueConstraint(name = "team_season_uk1", columnNames = {
		"team_id", "season_id" }) })
public class TeamSeasonEntry {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(name = "team_id", nullable = false)
	private TeamEntry team;

	@ManyToOne
	@JoinColumn(name = "season_id", nullable = false)
	private SeasonEntry season;

	@ManyToOne
	@JoinColumn(name = "affiliation_id")
	private LeagueEntry affiliation;

	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startsOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endsOn;

	@Column(length = 100)
	private String name;

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
	private final List<PlayerEntry> roster = new ArrayList<PlayerEntry>();

	@OneToMany(mappedBy = "teamSeason")
	private final Map<LocalDateTime, TeamEvent> events = new HashMap<>();

	/*---------- Methods ----------*/

	public String getFullName() {
		if (name != null) {
			final StringBuilder sb = new StringBuilder();
			sb.append(team.getSponsor()).append(" ").append(name);
			if (season.getDescription() != "") {
				sb.append(" ").append(season.getDescription());
			}
			return sb.toString();
		}
		return team.getTitle();
	}

	public String getShortName() {
		if (name != null) {
			final StringBuilder sb = new StringBuilder();
			sb.append(team.getSponsor()).append(" ").append(name);
			return sb.toString();
		}
		return team.getTitle();
	}

	public Interval getSeasonInterval() {
		return new Interval(startsOn.toDateTimeAtStartOfDay(),
				endsOn.toDateTimeAtStartOfDay());
	}

	public boolean addPlayerToRoster(PlayerEntry player) {
		player.setTeamSeason(this);
		return roster.add(player);
	}

	public PlayerEntry getPlayer(String id) {
		for (final PlayerEntry each : roster) {
			if (each.getId().equals(id)) {
				return each;
			}
		}
		return null;
	}

	public Map<String, PlayerEntry> getRosterData() {
		final Map<String, PlayerEntry> data = new HashMap<>();
		if (roster.size() > 0) {
			final List<PlayerEntry> list = new ArrayList<>(roster);
			Collections.sort(list, new PlayerComparator());
			for (final PlayerEntry each : list) {
				data.put(each.getId(), each);
			}
		}
		return data;
	}

	public boolean includes(LocalDateTime datetime) {
		final DateTime instant = datetime.toDateTime();
		final Interval interval = getSeasonInterval();
		return interval.contains(instant);
	}

	private static class PlayerComparator implements Comparator<PlayerEntry> {
		@Override
		public int compare(PlayerEntry o1, PlayerEntry o2) {
			final PersonEntry p1 = o1.getPerson();
			final PersonEntry p2 = o2.getPerson();
			final int result = p1.getLastName().compareToIgnoreCase(
					p2.getLastName());
			return result == 0 ? p1.getFirstName().compareToIgnoreCase(
					p2.getFirstName()) : result;
		}

	}

	/*---------- Getter/Setters ----------*/

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

	public LeagueEntry getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(LeagueEntry affiliation) {
		this.affiliation = affiliation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
