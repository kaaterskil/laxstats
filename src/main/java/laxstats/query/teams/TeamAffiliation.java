package laxstats.query.teams;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "team_affiliation_idx1", columnList = "startingOn")})
public class TeamAffiliation {
	
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 5408146241619855006L;

		@Column(length = 36)
		private String teamId;
		
		@Column(length = 36)
		private String affiliationId;
		
		public Id(){}
		
		public Id(String teamId, String affiliationId) {
			this.teamId = teamId;
			this.affiliationId = affiliationId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof TeamAffiliation.Id) {
				Id that = (Id) o;
				return this.teamId.equals(that.teamId) && 
						this.affiliationId.equals(that.affiliationId);
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
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "affiliationId", insertable = false, updatable = false)
	private Affiliation affiliation;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startingOn;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate endingOn;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
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
