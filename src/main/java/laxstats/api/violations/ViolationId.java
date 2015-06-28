package laxstats.api.violations;

import laxstats.api.AggregateId;

/**
 * {@code ViolationId} represents a violation aggregate identifier.
 */
public class ViolationId extends AggregateId {
   private static final long serialVersionUID = -870876302500090604L;

   /**
    * Creates a {@code ViolationId} with a new unique identifier.
    */
   public ViolationId() {
      super();
   }

   /**
    * Creates a {@code ViolationId} with the given unique identifier.
    * 
    * @param identifier
    */
   public ViolationId(String identifier) {
      super(identifier);
   }
}
