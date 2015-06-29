package laxstats.domain.users;

import laxstats.api.users.CreateUser;
import laxstats.api.users.UpdateUser;
import laxstats.api.users.UserId;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code UserCommandHandler} manages commands for the user aggregate.
 */
@Component
public class UserCommandHandler {
   private Repository<User> repository;

   @Autowired
   @Qualifier("userRepository")
   public void setRepository(Repository<User> userRepository) {
      repository = userRepository;
   }

   /**
    * Creates a new user aggregate from information contained in the payload of the given command.
    * 
    * @param command
    * @return
    */
   @CommandHandler
   public UserId handle(CreateUser command) {
      final UserId identifier = command.getUserId();
      final User user = new User(identifier, command.getUserDTO());
      repository.add(user);
      return identifier;
   }

   /**
    * Updates the state of a user aggregate from information contained in the payload of the given
    * command.
    * 
    * @param command
    */
   @CommandHandler
   public void handle(UpdateUser command) {
      final UserId identifier = command.getUserId();
      final User user = repository.load(identifier);
      user.update(command.getUserId(), command.getUserDTO());
   }
}
