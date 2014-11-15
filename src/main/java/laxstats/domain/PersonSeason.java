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
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "people_seasons")
public class PersonSeason {
	
	public enum Status {
		ACTIVE, INJURED, TRYOUT, INACTIVE;
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 4232283469443735627L;

		@Column(name = "person_id")
		private UUID personId;
		
		@Column(name = "season_id")
		private UUID seasonId;
		
		@Column(name = "team_id")
		private UUID teamId;
		
		public Id(){}
		
		public Id(UUID personId, UUID seasonId, UUID teamId){
			this.personId = personId;
			this.seasonId = seasonId;
			this.teamId = teamId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof PersonSeason.Id) {
				Id that = (Id) o;
				return this.personId.equals(that.personId) && 
						this.seasonId.equals(that.seasonId) && 
						this.teamId.equals(that.teamId);
			}
			return false;
		}
		
		public int hashCode() {
			return personId.hashCode() + seasonId.hashCode() + teamId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private PersonSeason.Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "person_id", insertable = false, updatable = false)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "season_id", insertable = false, updatable = false)
	private Season season;
	
	@ManyToOne
	@JoinColumn(name = "team_id", insertable = false, updatable = false)
	private Team team;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "jersey_number")
	private String jerseyNumber;
	
	@Enumerated(EnumType.STRING)
	private Position position;
	
	@Column(name = "captain")
	private boolean isCaptain;
	
	private int depth;
	private int height;
	private int weight;
	
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
	
	public PersonSeason(){}
	
	public PersonSeason(Person person, Season season, Team team) {
		this.person = person;
		this.season = season;
		this.team = team;
		
		this.id.personId = person.getId();
		this.id.seasonId = season.getId();
		this.id.teamId = team.getId();
		
		person.getPlayedSeasons().add(this);
		season.getSeasonPlayers().add(this);
	}
	
	//---------- Getter/Setters ----------//

	public PersonSeason.Id getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public Season getSeason() {
		return season;
	}

	public Team getTeam() {
		return team;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(String jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean isCaptain() {
		return isCaptain;
	}

	public void setCaptain(boolean isCaptain) {
		this.isCaptain = isCaptain;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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
