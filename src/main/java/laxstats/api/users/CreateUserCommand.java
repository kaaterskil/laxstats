package laxstats.api.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserCommand {

	private UserId userId;
	
	@NotNull
	private String email;
	
	@Size(min = 3)
	private String password;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String role;
	
	private String firstName;
	private String teamId;
	private String ipAddress;
	
	public CreateUserCommand(UserId userId, String email, String password,
			String firstName, String lastName, String role, String teamId,
			String ipAddress) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.teamId = teamId;
		this.ipAddress = ipAddress;
	}

	public UserId getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRole() {
		return role;
	}

	public String getTeamId() {
		return teamId;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	
}
