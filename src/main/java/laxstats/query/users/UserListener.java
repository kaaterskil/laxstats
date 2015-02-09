package laxstats.query.users;

import laxstats.api.users.UserCreatedEvent;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserUpdatedEvent;

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
	protected void handle(UserCreatedEvent event) {
		final UserDTO dto = event.getUserDTO();

		final UserEntry user = new UserEntry();
		user.setId(event.getUserId().toString());

		user.setTeam(dto.getTeam());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setEncodedPassword(dto.getEncodedPassword());
		user.setRole(dto.getRole());
		user.setEnabled(dto.isEnabled());
		user.setIpAddress(dto.getIpAddress());
		user.setCreatedAt(dto.getCreatedAt());
		user.setCreatedBy(dto.getCreatedBy());
		user.setModifiedAt(dto.getModifiedAt());
		user.setModifiedBy(dto.getModifiedBy());

		userRepository.save(user);
	}

	@EventHandler
	protected void handle(UserUpdatedEvent event) {
		final UserDTO dto = event.getUserDTO();
		final String userId = event.getUserId().toString();
		final UserEntry user = userRepository.findOne(userId);

		user.setTeam(dto.getTeam());
		user.setEmail(dto.getEmail());
		user.setEncodedPassword(dto.getEncodedPassword());
		user.setFirstName(dto.getFirstName());
		user.setIpAddress(dto.getIpAddress());
		user.setLastName(dto.getLastName());
		user.setRole(dto.getRole());
		user.setEnabled(dto.isEnabled());
		user.setIpAddress(dto.getIpAddress());
		user.setModifiedAt(dto.getModifiedAt());
		user.setModifiedBy(dto.getModifiedBy());

		userRepository.save(user);
	}
}
