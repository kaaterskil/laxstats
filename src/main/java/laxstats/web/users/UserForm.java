package laxstats.web.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.users.UserRole;

public class UserForm {

	private String teamId;

	@NotNull
	private String email;

	@Size(min = 6)
	private String password;

	private boolean enabled;

	@Size(max = 20)
	private String firstName;

	@NotNull
	@Size(min = 3, max = 30)
	private String lastName;

	@NotNull
	private UserRole role;

	private List<UserRole> roles;
	private Map<String, String> teams;

	/*---------- Methods ----------*/

	public List<UserRole> getUserRoles() {
		final List<UserRole> list = new ArrayList<>();
		list.add(UserRole.COACH);
		list.add(UserRole.MANAGER);

		return list;
	}

	/*---------- Getter/Setters ----------*/

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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	/*---------- Select element options ----------*/

	public Map<String, String> getTeams() {
		return teams;
	}

	public void setTeams(Map<String, String> teams) {
		this.teams = teams;
	}

	public List<UserRole> getRoles() {
		if (roles == null) {
			roles = Arrays.asList(UserRole.values());
		}
		return roles;
	}
}
