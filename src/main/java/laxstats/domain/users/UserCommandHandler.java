package laxstats.domain.users;

import laxstats.api.users.CreateUserCommand;
import laxstats.api.users.UserId;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserCommandHandler {

	private Repository<User> repository;
	
	@SuppressWarnings("unused")
	private UserQueryRepository userQueryRepository;

	@Autowired
	@Qualifier("userRepository")
	public void setRepository(Repository<User> userRepository) {
		this.repository = userRepository;
	}

	@Autowired
	public void setUserRepository(UserQueryRepository userRepository) {
		this.userQueryRepository = userRepository;
	}

	@CommandHandler
	public UserId handleCreateUser(CreateUserCommand command) {
		UserId id = command.getUserId();
		User user = new User(id, command.getEmail(), command.getPassword(),
				command.getFirstName(), command.getLastName(),
				command.getRole(), command.getTeamId(), command.getIpAddress());
		repository.add(user);
		return id;
	}
}
