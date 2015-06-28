package laxstats.api.violations;

/**
 * {@code ViolationUpdated} represents an event marking the update of a violation.
 */
public class ViolationUpdated {

   private final ViolationId penaltyTypeId;
   private final ViolationDTO penaltyTypeDTO;

   /**
    * Creates a {@code ViolationUpdated} event with the given aggregate identifier and updated data.
    * 
    * @param penaltyTypeId
    * @param penaltyTypeDTO
    */
   public ViolationUpdated(ViolationId penaltyTypeId, ViolationDTO penaltyTypeDTO) {
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
    * Returns the violation's updated data.
    * 
    * @return
    */
   public ViolationDTO getPenaltyTypeDTO() {
      return penaltyTypeDTO;
   }
}
