package laxstats.query.teams;

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

import laxstats.query.season.Season;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(
	indexes = {
		@Index(name = "team_season_idx1", columnList = "affiliation")
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "team_season_uk1", columnNames = {"startsOn", "endsOn"})
	}
)
public class TeamSeason {
	
	public enum Status {
		ACTIVE, INACTIVE;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 6990697052332076930L;

		@Column(length = 36)
		private String teamId;
		
		@Column(length = 36)
		private String seasonId;
		
		public Id(){}
		
		public Id(String teamId, String seasonId) {
			this.teamId = teamId;
			this.seasonId = seasonId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof TeamSeason.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId) && this.seasonId.equals(that.seasonId);
			}
			return false;
		}
		
		public int hashCode() {
			return teamId.hashCode() + seasonId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private TeamSeason.Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private TeamEntry team;
	
	@ManyToOne
	@JoinColumn(name = "seasonId", insertable = false, updatable = false)
	private Season season;
	
	@ManyToOne
	private Affiliation affiliation;
	
	@NotNull
	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startsOn;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endsOn;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private TeamSeason.Status status;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
	//---------- Constructors ----------//
	
	public TeamSeason(){}
	
	public TeamSeason(TeamEntry team, Season season) {
		this.team = team;
		this.season = season;
		
		this.id.teamId = team.getId();
		this.id.seasonId = season.getId();
		
		team.getTeamSeasons().add(this);
	}
	
	//---------- Getter/Setters ----------//

	public TeamSeason.Id getId() {
		return id;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public Season getSeason() {
		return season;
	}

	public Affiliation getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation) {
		this.affiliation = affiliation;
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

	public TeamSeason.Status getStatus() {
		return status;
	}

	public void setStatus(TeamSeason.Status status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
