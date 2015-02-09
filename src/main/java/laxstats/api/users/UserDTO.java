package laxstats.api.users;

import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class UserDTO {
	private final String userId;
	private final String email;
	private final String encodedPassword;
	private final String firstName;
	private final String lastName;
	private final TeamEntry team;
	private final String ipAddress;
	private final boolean enabled;
	private final UserRole role;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public UserDTO(String userId, String email, String encodedPassword,
			String firstName, String lastName, TeamEntry team,
			String ipAddress, boolean enabled, UserRole role,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.userId = userId;
		this.email = email;
		this.encodedPassword = encodedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		this.ipAddress = ipAddress;
		this.enabled = enabled;
		this.role = role;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public UserDTO(String userId, String email, String encodedPassword,
			String firstName, String lastName, TeamEntry team,
			String ipAddress, boolean enabled, UserRole role,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(userId, email, encodedPassword, firstName, lastName, team,
				ipAddress, enabled, role, null, null, modifiedAt, modifiedBy);
	}

	public String getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public TeamEntry getTeam() {
		return team;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public UserRole getRole() {
		return role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

}
