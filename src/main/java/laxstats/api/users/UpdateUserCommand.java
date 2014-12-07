package laxstats.api.users;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateUserCommand {

	@TargetAggregateIdentifier
	private final UserId userId;
	private final UserDTO userDTO;

	public UpdateUserCommand(UserId userId, UserDTO userDTO) {
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