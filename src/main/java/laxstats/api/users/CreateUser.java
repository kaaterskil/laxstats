package laxstats.api.users;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code CreateUser} represents a command to create a new user.
 */
public class CreateUser {

   @TargetAggregateIdentifier
   private final UserId userId;
   private final UserDTO userDTO;

   /**
    * Creates a {@code CreateUser} command with the given aggregate identifier and data.
    * 
    * @param userId
    * @param userDTO
    */
   public CreateUser(UserId userId, UserDTO userDTO) {
      this.userId = userId;
      this.userDTO = userDTO;
   }

   /**
    * Returns the aggregate identifier of the user to create.
    * 
    * @return
    */
   public UserId getUserId() {
      return userId;
   }

   /**
    * Returns the data with which to create a new user.
    * 
    * @return
    */
   public UserDTO getUserDTO() {
      return userDTO;
   }
}