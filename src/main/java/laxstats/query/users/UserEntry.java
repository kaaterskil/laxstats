package laxstats.query.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.query.teams.TeamEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "users", indexes = { @Index(name = "users_idx1", columnList = "lastName") }, uniqueConstraints = { @UniqueConstraint(name = "users_uk1", columnNames = { "email" }) })
public class UserEntry {

	@Id
	@org.springframework.data.annotation.Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	private TeamEntry team;

	@Column(nullable = false)
	private String email;

	@Column(length = 100, nullable = false)
	private String encodedPassword;

	@Column(nullable = false)
	private boolean enabled = false;

	@Column(length = 20)
	private String firstName;

	@Column(length = 30, nullable = false)
	private String lastName;

	@Column(length = 50)
	private String ipAddress;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private laxstats.api.users.Role role;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	protected UserEntry() {
	}

	// ---------- Getter/Setters ----------//

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public void setTeam(TeamEntry team) {
		this.team = team;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public laxstats.api.users.Role getRole() {
		return role;
	}

	public void setRole(laxstats.api.users.Role role) {
		this.role = role;
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
}
