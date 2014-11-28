package laxstats.query.teams;

import java.util.HashSet;
import java.util.Set;

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
import javax.validation.constraints.NotNull;

import laxstats.domain.Gender;
import laxstats.query.events.Site;
import laxstats.query.events.TeamEvent;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "team_idx1", columnList = "gender")})
public class Team {

	@Id
	@Column(length = 36)
	private String id;
	
	@NotNull
	@Column(length = 100, nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Gender gender;
	
	@ManyToOne(targetEntity = Site.class)
	private String homeSiteId;
	
	@Column(length = 30)
	private String encryptedPassword;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;
	
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<TeamEvent> teamEvents = new HashSet<TeamEvent>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<TeamSeason> teamSeasons = new HashSet<TeamSeason>();
	
	@OneToMany(mappedBy = "team")
	private Set<TeamAffiliation> teamAffiliations = new HashSet<TeamAffiliation>();
	
	//---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getHomeSiteId() {
		return homeSiteId;
	}

	public void setHomeSite(String homeSiteId) {
		this.homeSiteId = homeSiteId;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
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

	public Set<TeamEvent> getTeamEvents() {
		return teamEvents;
	}

	public Set<TeamSeason> getTeamSeasons() {
		return teamSeasons;
	}

	public Set<TeamAffiliation> getTeamAffiliations() {
		return teamAffiliations;
	}
}
