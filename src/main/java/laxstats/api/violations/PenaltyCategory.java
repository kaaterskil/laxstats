package laxstats.api.violations;

/**
 * {@code PenaltyCategory} enumerates the classification of penalties.
 */
public enum PenaltyCategory {

   /**
    * Serious violations.
    */
   PERSONAL_FOUL("Personal"),

   /**
    * Less serious and procedural violations.
    */
   TECHNICAL_FOUL("Technical");

   /**
    * Returns the pretty name of a {@code PenaltyCategory} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code PenaltyCategory} with the given pretty name.
    * 
    * @param label
    */
   private PenaltyCategory(String label) {
      assert label != null;

      this.label = label;
   }

   private final String label;
}
