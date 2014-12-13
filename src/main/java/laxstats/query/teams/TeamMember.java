package laxstats.query.teams;

import laxstats.query.people.PersonEntry;
import laxstats.query.season.SeasonEntry;
import laxstats.query.users.UserEntry;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
	indexes = {
		@Index(name = "player_idx1", columnList = "role"),
		@Index(name = "player_idx2", columnList = "status"),
		@Index(name = "player_idx3", columnList = "isCaptain"),
		@Index(name = "player_idx4", columnList = "depth")
	}
)
public class TeamMember {
	
	public enum Status {
		ACTIVE, INJURED, TRYOUT, INACTIVE
	}

	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = 4232283469443735627L;
		
		@Column(length = 36)
		private String personId;
		
		@Column(length = 36)
		private String seasonId;
		
		@Column(length = 36)
		private String teamId;
		
		public Id(){}
		
		public Id(String personId, String seasonId, String teamId){
			this.personId = personId;
			this.seasonId = seasonId;
			this.teamId = teamId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof TeamMember.Id) {
				Id that = (Id) o;
				return this.personId.equals(that.personId) && 
						this.seasonId.equals(that.seasonId) && 
						this.teamId.equals(that.teamId);
			}
			return false;
		}
		
		public int hashCode() {
			return personId.hashCode() + seasonId.hashCode() + 
					teamId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private TeamMember.Id id = new Id();
	
	@ManyToOne
	@JoinColumn(name = "personId", insertable = false, updatable = false)
	private PersonEntry person;
	
	@ManyToOne
	@JoinColumn(name = "seasonId", insertable = false, updatable = false)
	private SeasonEntry season;
	
	@ManyToOne
	@JoinColumn(name = "teamId", insertable = false, updatable = false)
	private TeamEntry team;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Status status;
	
	@Column(length = 4)
	private String jerseyNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Position position;
	
	private boolean isCaptain;
	
	private int depth;
	private int height;
	private int weight;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;
	
	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
	//---------- Constructors ----------//
	
	public TeamMember(){}

	public TeamMember(PersonEntry person, SeasonEntry season, TeamEntry team) {
		this.person = person;
		this.season = season;
		this.team = team;
		
		this.id.personId = person.getId();
		this.id.seasonId = season.getId();
		this.id.teamId = team.getId();
		
		person.getPlayedSeasons().add(this);
	}
	
	//---------- Getter/Setters ----------//

	public TeamMember.Id getId() {
		return id;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public SeasonEntry getSeason() {
		return season;
	}

	public TeamEntry getTeam() {
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
