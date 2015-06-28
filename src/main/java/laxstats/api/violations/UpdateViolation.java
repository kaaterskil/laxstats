package laxstats.api.violations;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code UpdateViolation} represents a command to update the state of a violation.
 */
public class UpdateViolation {

   @TargetAggregateIdentifier
   private final ViolationId penaltyTypeId;
   private final ViolationDTO penaltyTypeDTO;

   /**
    * Creates a {@code UpdateViolation} command with the given aggregate identifier and updated
    * data.
    *
    * @param penaltyTypeId
    * @param penaltyTypeDTO
    */
   public UpdateViolation(ViolationId penaltyTypeId, ViolationDTO penaltyTypeDTO) {
      this.penaltyTypeId = penaltyTypeId;
      this.penaltyTypeDTO = penaltyTypeDTO;
   }

   /**
    * Returns the aggregate identifier of the violation to update.
    *
    * @return
    */
   public ViolationId getPenaltyTypeId() {
      return penaltyTypeId;
   }

   /**
    * Returns the updated data for the violation.
    *
    * @return
    */
   public ViolationDTO getPenaltyTypeDTO() {
      return penaltyTypeDTO;
   }
}
