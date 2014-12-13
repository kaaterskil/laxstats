package laxstats.api.users;


public class UserUpdatedEvent {

	private final UserId userId;
	private final UserDTO userDTO;

	public UserUpdatedEvent(UserId userId, UserDTO userDTO) {
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
