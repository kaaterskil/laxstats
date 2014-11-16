package laxstats.api.users;

public class UserCreatedEvent {

	private UserId userId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String role;
	private String teamId;
	private String ipAddress;
	
	public UserCreatedEvent(UserId userId, String email, String password,
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

	@Override
	public String toString() {
		return "UserCreatedEvent [userId=" + userId + ", email=" + email
				+ ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", role=" + role + ", teamId="
				+ teamId + ", ipAddress=" + ipAddress + "]";
	}
	
}
