package laxstats.query.users;

import laxstats.api.users.UserCreatedEvent;
import laxstats.api.users.UserDTO;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

	private UserQueryRepository userRepository;

	@Autowired
	public void setUserRepository(UserQueryRepository userRepository) {
		this.userRepository = userRepository;
	}

	@EventHandler
	public void handle(UserCreatedEvent event) {
		final UserDTO dto = event.getUserDTO();

		final UserEntry user = new UserEntry();
		user.setId(event.getUserId().toString());

		user.setCreatedAt(dto.getCreatedAt());
		user.setCreatedBy(dto.getCreatedBy());
		user.setEmail(dto.getEmail());
		user.setEnabled(dto.isEnabled());
		user.setEncodedPassword(dto.getEncodedPassword());
		user.setFirstName(dto.getFirstName());
		user.setIpAddress(dto.getIpAddress());
		user.setLastName(dto.getLastName());
		user.setModifiedAt(dto.getModifiedAt());
		user.setModifiedBy(dto.getModifiedBy());
		user.setRole(dto.getRole());
		user.setTeamId(dto.getTeamId());

		userRepository.save(user);
	}
}
