package laxstats.api.users;

/**
 * {@code UserUpdated} represents an event marking the update of a user.
 */
public class UserUpdated {

   private final UserId userId;
   private final UserDTO userDTO;

   /**
    * Creates a {@code UserUpdated} event with the given aggregate identifier and updated data.
    * 
    * @param userId
    * @param userDTO
    */
   public UserUpdated(UserId userId, UserDTO userDTO) {
      this.userId = userId;
      this.userDTO = userDTO;
   }

   /**
    * Returns the aggregate identifier of the updated user.
    * 
    * @return
    */
   public UserId getUserId() {
      return userId;
   }

   /**
    * Returns the user's updated data.
    * 
    * @return
    */
   public UserDTO getUserDTO() {
      return userDTO;
   }

}
