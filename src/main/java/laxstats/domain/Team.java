package laxstats.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "team_idx1", columnList = "gender")})
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@NotNull
	@Column(length = 100, nullable = false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Gender gender;
	
	@ManyToOne
	@JoinColumn(name = "home_site")
	private Site homeSite;
	
	@Column(name = "encrypted_password", length = 30)
	private String encryptedPassword;
	
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<TeamEvent> teamEvents = new HashSet<TeamEvent>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private Set<TeamSeason> teamSeasons = new HashSet<TeamSeason>();
	
	@OneToMany(mappedBy = "team")
	private Set<TeamAffiliation> teamAffiliations = new HashSet<TeamAffiliation>();
	
	//---------- Getter/Setters ----------//

	public UUID getId() {
		return id;
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

	public Site getHomeSite() {
		return homeSite;
	}

	public void setHomeSite(Site homeSite) {
		this.homeSite = homeSite;
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
