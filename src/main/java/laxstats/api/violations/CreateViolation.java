package laxstats.api.violations;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code CreateViolation} represents a command to create a new violation.
 */
public class CreateViolation {

   @TargetAggregateIdentifier
   private final ViolationId penaltyTypeId;
   private final ViolationDTO penaltyTypeDTO;

   /**
    * Creates a {@code CreateViolation} command with the given aggregate identifier and data.
    *
    * @param penaltyTypeId
    * @param penaltyTypeDTO
    */
   public CreateViolation(ViolationId penaltyTypeId, ViolationDTO penaltyTypeDTO) {
      this.penaltyTypeId = penaltyTypeId;
      this.penaltyTypeDTO = penaltyTypeDTO;
   }

   /**
    * Returns the aggregate identifier of the violation to create.
    *
    * @return
    */
   public ViolationId getPenaltyTypeId() {
      return penaltyTypeId;
   }

   /**
    * Returns the data necessary to create the violation.
    *
    * @return
    */
   public ViolationDTO getPenaltyTypeDTO() {
      return penaltyTypeDTO;
   }
}
