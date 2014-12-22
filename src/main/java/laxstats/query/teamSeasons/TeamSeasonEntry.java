package laxstats.query.teamSeasons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "team_seasons", indexes = { @Index(name = "team_season_idx1", columnList = "affiliation") }, uniqueConstraints = { @UniqueConstraint(name = "team_season_uk1", columnNames = {
		"startsOn", "endsOn" }) })
public class TeamSeasonEntry {
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 6990697052332076930L;

		@Column(length = 36)
		private String teamId;

		@Column(length = 36)
		private String seasonId;

		public Id() {
		}

		public Id(String teamId, String seasonId) {
			this.teamId = teamId;
			this.seasonId = seasonId;
		}

		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof TeamSeasonEntry.Id) {
				final Id that = (Id) o;
				return this.teamId.equals(that.teamId)
						&& this.seasonId.equals(that.seasonId);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return teamId.hashCode() + seasonId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private final TeamSeasonEntry.Id id = new Id();

	@ManyToOne
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private TeamEntry team;

	@ManyToOne
	@JoinColumn(name = "seasonId", insertable = false, updatable = false)
	private SeasonEntry season;

	@NotNull
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

	// ---------- Constructors ----------//

	public TeamSeasonEntry(TeamEntry team, SeasonEntry season) {
		this.team = team;
		this.season = season;

		this.id.teamId = team.getId();
		this.id.seasonId = season.getId();

		team.getSeasons().add(this);
	}

	protected TeamSeasonEntry() {
	}

	// ---------- Getter/Setters ----------//

	public TeamSeasonEntry.Id getId() {
		return id;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public SeasonEntry getSeason() {
		return season;
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
}
