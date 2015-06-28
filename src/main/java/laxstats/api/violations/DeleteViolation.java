package laxstats.api.violations;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code DeleteViolation} represents a command to delete a violation.
 */
public class DeleteViolation {

   @TargetAggregateIdentifier
   private final ViolationId penaltyTypeId;

   /**
    * Creates a {@code DeleteViolation} command with the given aggregate identifier.
    * 
    * @param penaltyTypeId
    */
   public DeleteViolation(ViolationId penaltyTypeId) {
      this.penaltyTypeId = penaltyTypeId;
   }

   /**
    * Returns the aggregate identifier of the violation to delete.
    * 
    * @return
    */
   public ViolationId getPenaltyTypeId() {
      return penaltyTypeId;
   }
}
