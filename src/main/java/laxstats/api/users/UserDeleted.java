package laxstats.api.users;

/**
 * {@code UserDeleted} represents an event marking the deletion of a user.
 */
public class UserDeleted {

   private final UserId userId;

   /**
    * Creates a {@code UserDeleted} eent with the given aggregate identifier.
    * 
    * @param userId
    */
   public UserDeleted(UserId userId) {
      this.userId = userId;
   }

   /**
    * Returns the aggregate identifier of the deleted user.
    * 
    * @return
    */
   public UserId getUserId() {
      return userId;
   }

}
