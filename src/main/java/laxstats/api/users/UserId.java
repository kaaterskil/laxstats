package laxstats.api.users;

import laxstats.api.AggregateId;

/**
 * {@code UserId} represents the user's aggregate identifier.
 */
public class UserId extends AggregateId {
   private static final long serialVersionUID = -3348074765836159716L;

   /**
    * Creates a {@code UserId} with a new unique identifier.
    */
   public UserId() {
      super();
   }

   /**
    * Creates a {@code UserId} with the given unique identifier.
    * 
    * @param identifier
    */
   public UserId(String identifier) {
      super(identifier);
   }
}
