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

import laxstats.api.events.PlayRole;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "play_participants", indexes = {
		@Index(name = "play_participants_idx1", columnList = "role"),
		@Index(name = "play_participants_idx2", columnList = "pointCredit"),
		@Index(name = "play_participants_idx3", columnList = "team_season") }, uniqueConstraints = { @UniqueConstraint(name = "play_participants_uk1", columnNames = {
		"id", "play", "attendee" }) })
public class PlayParticipantEntry {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PlayEntry play;

	@ManyToOne
	@JoinColumn(nullable = false)
	private AttendeeEntry attendee;

	@ManyToOne
	private TeamEntry teamSeason;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private PlayRole role;

	private final boolean pointCredit = false;

	private int cumulativeAssists;

	private int cumulativeGoals;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlayEntry getPlay() {
		return play;
	}

	public void setPlay(PlayEntry play) {
		this.play = play;
	}

	public AttendeeEntry getAttendee() {
		return attendee;
	}

	public void setAttendee(AttendeeEntry attendee) {
		this.attendee = attendee;
	}

	public TeamEntry getTeamSeason() {
		return teamSeason;
	}

	public void setTeamSeason(TeamEntry teamSeason) {
		this.teamSeason = teamSeason;
	}

	public PlayRole getRole() {
		return role;
	}

	public void setRole(PlayRole role) {
		this.role = role;
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

	public boolean isPointCredit() {
		return pointCredit;
	}
}
