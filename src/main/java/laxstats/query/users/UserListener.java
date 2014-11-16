package laxstats.query.users;

import laxstats.api.users.UserCreatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.LocalDateTime;
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
	public void handleUserCreated(UserCreatedEvent event) {
		LocalDateTime now = LocalDateTime.now();
		
		UserEntry user = new UserEntry();
		user.setId(event.getUserId().toString());
		user.setCreatedAt(now);
		user.setEmail(event.getEmail());
		user.setEncryptedPassword(event.getPassword());
		user.setFirstName(event.getFirstName());
		user.setIpAddress(event.getIpAddress());
		user.setLastName(event.getLastName());
		user.setModifiedAt(now);
		user.setRole(UserEntry.Role.valueOf(UserEntry.Role.class, event.getRole()));
		user.setTeamId(event.getTeamId());
		
		userRepository.save(user);
	}
}
