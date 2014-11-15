package laxstats.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
public class TeamSeason {
	
	public enum Status {
		ACTIVE, INACTIVE;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 6990697052332076930L;

		@Column(name = "team_id")
		private UUID teamId;
		
		@Column(name = "season_id")
		private UUID seasonId;
		
		public Id(){}
		
		public Id(UUID teamId, UUID seasonId) {
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
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "season_id", insertable = false, updatable = false)
	private Season season;
	
	@ManyToOne
	private Affiliation affiliation;
	
	@Column(name = "starts_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startsOn;
	
	@Column(name = "ends_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endsOn;
	
	@Enumerated(EnumType.STRING)
	private TeamSeason.Status status;
	
	@Column(name = "created_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	@Column(name = "modified_at")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne
	@JoinColumn(name = "modified_by")
	private User modifiedBy;
	
	//---------- Constructors ----------//
	
	public TeamSeason(){}
	
	public TeamSeason(Team team, Season season) {
		this.team = team;
		this.season = season;
		
		this.id.teamId = team.getId();
		this.id.seasonId = season.getId();
		
		team.getTeamSeasons().add(this);
		season.getSeasonTeams().add(this);
	}
	
	//---------- Getter/Setters ----------//

	public TeamSeason.Id getId() {
		return id;
	}

	public Team getTeam() {
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
