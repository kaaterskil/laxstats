package laxstats.api.users;


public class UserCreatedEvent {

	private final UserId userId;
	private final UserDTO userDTO;

	public UserCreatedEvent(UserId userId, UserDTO userDTO) {
		this.userId = userId;
		this.userDTO = userDTO;
	}

	public UserId getUserId() {
		return userId;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

}
