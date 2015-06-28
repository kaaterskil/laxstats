package laxstats.api.violations;

/**
 * {@code ViolationCreated} represents an event marking the creation of a violation.
 */
public class ViolationCreated {

   private final ViolationId penaltyTypeId;
   private final ViolationDTO penaltyTypeDTO;

   /**
    * Creates a {@code ViolationCreated} event with the given aggregate identifier and updated
    * violation data.
    *
    * @param penaltyTypeId
    * @param penaltyTypeDTO
    */
   public ViolationCreated(ViolationId penaltyTypeId, ViolationDTO penaltyTypeDTO) {
      this.penaltyTypeId = penaltyTypeId;
      this.penaltyTypeDTO = penaltyTypeDTO;
   }

   /**
    * Returns the aggregate identifier of the updated violation.
    *
    * @return
    */
   public ViolationId getPenaltyTypeId() {
      return penaltyTypeId;
   }

   /**
    * Returns the updated violation data.
    *
    * @return
    */
   public ViolationDTO getPenaltyTypeDTO() {
      return penaltyTypeDTO;
   }
}
