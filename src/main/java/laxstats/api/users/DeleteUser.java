package laxstats.api.users;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code DeleteUser} represents a command to delete a user.
 */
public class DeleteUser {

   @TargetAggregateIdentifier
   private final UserId userId;

   /**
    * Creates a {@code DeleteUser} command with the given aggregate identifier.
    * 
    * @param userId
    */
   public DeleteUser(UserId userId) {
      this.userId = userId;
   }

   /**
    * Returns the aggregate identifier of the user to delete.
    * 
    * @return
    */
   public UserId getUserId() {
      return userId;
   }
}
