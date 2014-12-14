package laxstats.domain.users;

import laxstats.api.users.CreateUserCommand;
import laxstats.api.users.UpdateUserCommand;
import laxstats.api.users.UserId;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserCommandHandler {
	private Repository<User> repository;

	@Autowired
	@Qualifier("userRepository")
	public void setRepository(Repository<User> userRepository) {
		this.repository = userRepository;
	}

	@CommandHandler
	public UserId handle(CreateUserCommand command) {
		final UserId identifier = command.getUserId();
		final User user = new User(identifier, command.getUserDTO());
		repository.add(user);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdateUserCommand command) {
		final UserId identifier = command.getUserId();
		final User user = repository.load(identifier);
		user.update(command.getUserId(), command.getUserDTO());
	}
}
