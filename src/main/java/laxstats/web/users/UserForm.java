package laxstats.web.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.users.Role;

public class UserForm {

	private String teamId;

	@NotNull
	private String email;

	@NotNull
	@Size(min = 6)
	private String password;
	private boolean enabled;
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private Role role;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
