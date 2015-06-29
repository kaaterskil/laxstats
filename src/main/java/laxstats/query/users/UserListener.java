package laxstats.query.users;

import laxstats.api.users.UserCreated;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code UserListener} manages events that write to the query database.
 */
@Component
public class UserListener {

   private UserQueryRepository userRepository;

   @Autowired
   public void setUserRepository(UserQueryRepository userRepository) {
      this.userRepository = userRepository;
   }

   /**
    * Creates and persists a {@code UserEntry} with information contained in the payload of the
    * given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(UserCreated event) {
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

   /**
    * Updates and persists a {@code UserEntry} from information contained in the payload of the
    * given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(UserUpdated event) {
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
