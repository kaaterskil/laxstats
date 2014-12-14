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

import laxstats.api.people.Gender;
import laxstats.query.events.TeamEvent;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = { @Index(name = "team_idx1", columnList = "gender"),
		@Index(name = "team_idx2", columnList = "name") })
public class TeamEntry {

	@Id
	@Column(length = 36)
	private String id;

	@NotNull
	@Column(length = 100, nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Gender gender;

	@ManyToOne
	private SiteEntry homeSite;

	@Column(length = 50)
	private String encodedPassword;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private final Set<TeamEvent> teamEvents = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
	private final Set<TeamSeason> teamSeasons = new HashSet<>();

	@OneToMany(mappedBy = "team")
	private final Set<TeamAffiliation> teamAffiliations = new HashSet<>();

	// ---------- Getter/Setters ----------//

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

	public SiteEntry getHomeSite() {
		return homeSite;
	}

	public void setHomeSite(SiteEntry homeSite) {
		this.homeSite = homeSite;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
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
