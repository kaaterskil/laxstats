package laxstats.query.leagues;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "team_affiliations", indexes = { @Index(name = "team_affiliation_idx1", columnList = "startingOn") })
public class TeamAffiliation {

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 5408146241619855006L;

		@Column(length = 36)
		private String teamId;

		@Column(length = 36)
		private String affiliationId;

		public Id() {
		}

		public Id(String teamId, String affiliationId) {
			this.teamId = teamId;
			this.affiliationId = affiliationId;
		}

		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof TeamAffiliation.Id) {
				final Id that = (Id) o;
				return this.teamId.equals(that.teamId)
						&& this.affiliationId.equals(that.affiliationId);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return teamId.hashCode() + affiliationId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private final TeamAffiliation.Id id = new Id();

	@ManyToOne
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private TeamEntry team;

	@ManyToOne
	@JoinColumn(name = "affiliationId", insertable = false, updatable = false)
	private LeagueEntry affiliation;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startingOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endingOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	// ---------- Constructors ----------//

	public TeamAffiliation() {
	}

	public TeamAffiliation(TeamEntry team, LeagueEntry affiliation) {
		this.team = team;
		this.affiliation = affiliation;

		this.id.teamId = team.getId();
		this.id.affiliationId = affiliation.getId();

		affiliation.getAffiliatedTeams().add(this);
	}

	// ---------- Getter/Setters ----------//

	public LocalDate getStartingOn() {
		return startingOn;
	}

	public void setStartingOn(LocalDate startingOn) {
		this.startingOn = startingOn;
	}

	public LocalDate getEndingOn() {
		return endingOn;
	}

	public void setEndingOn(LocalDate endingOn) {
		this.endingOn = endingOn;
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

	public TeamAffiliation.Id getId() {
		return id;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public LeagueEntry getAffiliation() {
		return affiliation;
	}
}
