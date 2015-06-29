package laxstats.domain.users;

import laxstats.api.users.UserCreated;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;
import laxstats.api.users.UserRole;
import laxstats.api.users.UserUpdated;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * {@code User} represents a persistent domain object model of a user of this application.
 */
public class User extends AbstractAnnotatedAggregateRoot<UserId> {
   private static final long serialVersionUID = -2440058181713894132L;

   @AggregateIdentifier
   private UserId userId;

   private String teamId;
   private String email;
   private String encodedPassword;
   private boolean enabled;
   private String firstName;
   private String lastName;
   private String ipAddress;
   private UserRole role;

   /**
    * Applies a creation event to a user aggregate with the given aggregate identifier and user
    * data.
    *
    * @param userId
    * @param userDTO
    */
   public User(UserId userId, UserDTO userDTO) {
      apply(new UserCreated(userId, userDTO));
   }

   /**
    * Creates a user. Internal use only.
    */
   protected User() {
   }

   /**
    * Instructs the framework to update the state of this user.
    *
    * @param userId
    * @param userDTO
    */
   public void update(UserId userId, UserDTO userDTO) {
      apply(new UserUpdated(userId, userDTO));
   }

   /**
    * Stores and persists the user data contained in the payload of the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(UserCreated event) {
      final UserDTO dto = event.getUserDTO();
      userId = event.getUserId();

      if (dto.getTeam() != null) {
         teamId = dto.getTeam().getId().toString();
      }
      email = dto.getEmail();
      encodedPassword = dto.getEncodedPassword();
      enabled = dto.isEnabled();
      firstName = dto.getFirstName();
      lastName = dto.getLastName();
      ipAddress = dto.getIpAddress();
      role = dto.getRole();
   }

   /**
    * Updates and persists the user with information form the payload of the given event.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(UserUpdated event) {
      final UserDTO dto = event.getUserDTO();
      if (dto.getTeam() != null) {
         teamId = dto.getTeam().getId().toString();
      }
      email = dto.getEmail();
      encodedPassword = dto.getEncodedPassword();
      enabled = dto.isEnabled();
      firstName = dto.getFirstName();
      lastName = dto.getLastName();
      ipAddress = dto.getIpAddress();
      role = dto.getRole();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public UserId getIdentifier() {
      return userId;
   }

   /**
    * Returns the aggraget identifier of the user's associated team.
    *
    * @return
    */
   public String getTeamId() {
      return teamId;
   }

   /**
    * Returns the user's email address. Never null.
    *
    * @return
    */
   public String getEmail() {
      return email;
   }

   /**
    * Returns the user's encoded password.
    *
    * @return
    */
   public String getEncodedPassword() {
      return encodedPassword;
   }

   /**
    * Returns true if the user is enabled to user this application.
    *
    * @return
    */
   public boolean isEnabled() {
      return enabled;
   }

   /**
    * Returns the user's first name.
    *
    * @return
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Returns the user's last name.
    *
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Returns the user's IP address, or null if none.
    *
    * @return
    */
   public String getIpAddress() {
      return ipAddress;
   }

   /**
    * Returns the user's role.
    *
    * @return
    */
   public UserRole getRole() {
      return role;
   }
}
