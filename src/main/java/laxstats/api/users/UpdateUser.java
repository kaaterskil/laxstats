package laxstats.api.users;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code UpdateUser} represents a command to update the state of a user.
 */
public class UpdateUser {

   @TargetAggregateIdentifier
   private final UserId userId;
   private final UserDTO userDTO;

   /**
    * Creates an {@code UpdateUser} command with the given aggregate identifier and data to update.
    * 
    * @param userId
    * @param userDTO
    */
   public UpdateUser(UserId userId, UserDTO userDTO) {
      this.userId = userId;
      this.userDTO = userDTO;
   }

   /**
    * Returns the aggregate identifier of the user to update.
    * 
    * @return
    */
   public UserId getUserId() {
      return userId;
   }

   /**
    * Returns the data with which to update the user.
    * 
    * @return
    */
   public UserDTO getUserDTO() {
      return userDTO;
   }

}