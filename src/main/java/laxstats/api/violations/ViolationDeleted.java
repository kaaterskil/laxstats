package laxstats.api.violations;

/**
 * {@code violationDeleted} represents an event marking the deletion of a violation.
 */
public class ViolationDeleted {

   private final ViolationId penaltyTypeId;

   /**
    * Creates a {@code ViolationDeleted} event with the given aggregate identifier.
    * 
    * @param penaltyTypeId
    */
   public ViolationDeleted(ViolationId penaltyTypeId) {
      this.penaltyTypeId = penaltyTypeId;
   }

   /**
    * Returns the aggregate identifier of the de;eted violation.
    * 
    * @return
    */
   public ViolationId getPenaltyTypeId() {
      return penaltyTypeId;
   }
}
