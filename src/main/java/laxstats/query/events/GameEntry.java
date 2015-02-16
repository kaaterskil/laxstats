package laxstats.query.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import laxstats.api.events.Alignment;
import laxstats.api.events.Conditions;
import laxstats.api.events.PlayUtils;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

@Entity
@Table(name = "games", indexes = {
		@Index(name = "event_idx1", columnList = "schedule"),
		@Index(name = "event_idx2", columnList = "startsAt"),
		@Index(name = "event_idx3", columnList = "alignment"),
		@Index(name = "event_idx4", columnList = "status"),
		@Index(name = "event_idx5", columnList = "conditions") })
public class GameEntry implements Serializable {
	private static final long serialVersionUID = 3864013780705615870L;

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	private SiteEntry site;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private SiteAlignment alignment;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime startsAt;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Schedule schedule;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Status status;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Conditions conditions;

	@Column(nullable = false)
	private String description;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final Map<String, AttendeeEntry> eventAttendees = new HashMap<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final List<TeamEvent> teams = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private final Map<String, PlayEntry> plays = new HashMap<>();

	/*---------- Methods ----------*/

	public AttendeeEntry getAttendee(String key) {
		return eventAttendees.get(key);
	}

	public void addPlay(PlayEntry play) {
		play.setEvent(this);
		plays.put(play.getId(), play);
	}

	public void deletePlay(PlayEntry play) {
		play.clear();
		plays.remove(play.getId());
	}

	public List<PenaltyEntry> getPenalties() {
		final List<PenaltyEntry> list = new ArrayList<>();
		for (final PlayEntry play : plays.values()) {
			if (play instanceof PenaltyEntry) {
				list.add((PenaltyEntry) play);
			}
		}
		Collections.sort(list, new PenaltyComparator());
		return list;
	}

	public Map<String, List<AttendeeEntry>> getAttendees() {
		final Map<String, List<AttendeeEntry>> result = new HashMap<>();
		for (final TeamEvent teamEvent : teams) {
			final TeamSeasonEntry tse = teamEvent.getTeamSeason();

			final List<AttendeeEntry> list = new ArrayList<AttendeeEntry>();
			for (final AttendeeEntry attendee : eventAttendees.values()) {
				if (attendee.getTeamSeason().equals(tse)) {
					list.add(attendee);
				}
			}
			Collections.sort(list, new AttendeeComparator());
			result.put(tse.getName(), list);
		}
		return result;
	}

	public TeamSeasonEntry getHomeTeam() {
		if (teams.size() == 0) {
			return null;
		} else if (teams.size() == 2
				&& teams.get(1).getAlignment().equals(Alignment.HOME)) {
			return teams.get(1).getTeamSeason();
		}
		return teams.get(0).getTeamSeason();
	}

	public TeamSeasonEntry getVisitingTeam() {
		if (teams.size() < 2) {
			return null;
		} else if (teams.get(0).getAlignment().equals(Alignment.AWAY)) {
			return teams.get(0).getTeamSeason();
		}
		return teams.get(1).getTeamSeason();
	}

	/* ---------- Getter/Setters ---------- */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SiteEntry getSite() {
		return site;
	}

	public void setSite(SiteEntry site) {
		this.site = site;
	}

	public SiteAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(SiteAlignment alignment) {
		this.alignment = alignment;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Map<String, AttendeeEntry> getEventAttendees() {
		return eventAttendees;
	}

	public List<TeamEvent> getTeams() {
		return teams;
	}

	public Map<String, PlayEntry> getPlays() {
		return plays;
	}

	private static class AttendeeComparator implements
			Comparator<AttendeeEntry> {
		@Override
		public int compare(AttendeeEntry o1, AttendeeEntry o2) {
			final String l1 = o1.label();
			final String l2 = o2.label();
			return l1.compareToIgnoreCase(l2);
		}
	}

	private static class PenaltyComparator implements Comparator<PenaltyEntry> {
		@Override
		public int compare(PenaltyEntry o1, PenaltyEntry o2) {
			final LocalTime t1 = PlayUtils.getTotalElapsedTime(o1.getPeriod(),
					o1.getElapsedTime());
			final LocalTime t2 = PlayUtils.getTotalElapsedTime(o2.getPeriod(),
					o2.getElapsedTime());
			return t1.compareTo(t2);
		}
	}
}
