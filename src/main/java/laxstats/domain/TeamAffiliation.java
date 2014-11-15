package laxstats.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "team_affiliation_idx1", columnList = "starting_on")})
public class TeamAffiliation {
	
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 5408146241619855006L;

		@Column(name = "team_id")
		private UUID teamId;
		
		@Column(name = "affiliation_id")
		private UUID affiliationId;
		
		public Id(){}
		
		public Id(UUID teamId, UUID affiliationId) {
			this.teamId = teamId;
			this.affiliationId = affiliationId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof TeamAffiliation.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId) && this.affiliationId.equals(that.affiliationId);
			}
			return false;
		}
		
		public int hashCode() {
			return teamId.hashCode() + affiliationId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private TeamAffiliation.Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "affiliation_id", insertable = false, updatable = false)
	private Affiliation affiliation;

	@Column(name = "starting_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startingOn;
	
	@Column(name = "ending_on")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endingOn;
	
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
	
	public TeamAffiliation(){}
	
	public TeamAffiliation(Team team, Affiliation affiliation) {
		this.team = team;
		this.affiliation = affiliation;
		
		this.id.teamId = team.getId();
		this.id.affiliationId = affiliation.getId();
		
		team.getTeamAffiliations().add(this);
		affiliation.getAffiliatedTeams().add(this);
	}
	
	//---------- Getter/Setters ----------//

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

	public TeamAffiliation.Id getId() {
		return id;
	}

	public Team getTeam() {
		return team;
	}

	public Affiliation getAffiliation() {
		return affiliation;
	}
}
