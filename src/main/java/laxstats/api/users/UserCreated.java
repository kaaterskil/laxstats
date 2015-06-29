package laxstats.api.users;

/**
 * {@code UserCreated} represents an event marking the creation of a user.
 */
public class UserCreated {

   private final UserId userId;
   private final UserDTO userDTO;

   /**
    * Creates a {@code UserCreated} event with the given aggregate identifier and user data.
    * 
    * @param userId
    * @param userDTO
    */
   public UserCreated(UserId userId, UserDTO userDTO) {
      this.userId = userId;
      this.userDTO = userDTO;
   }

   /**
    * Returns the aggregate identifier of the created user.
    * 
    * @return
    */
   public UserId getUserId() {
      return userId;
   }

   /**
    * Returns data about the created user.
    * 
    * @return
    */
   public UserDTO getUserDTO() {
      return userDTO;
   }

}
