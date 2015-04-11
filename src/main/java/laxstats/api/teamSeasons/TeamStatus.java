package laxstats.api.teamSeasons;

/**
 * {@code TeamStatus} enumerates the state of a team in a given season.
 */
public enum TeamStatus {

   /**
    * The team is actively participating in the given season.
    */
   ACTIVE("Active"),

   /**
    * The team is not active in the given season.
    */
   INACTIVE("Inactive");

   /**
    * Returns the pretty name of a {@code TeamStatus} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code TeamStatus} with the given pretty name.
    *
    * @param label
    */
   private TeamStatus(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
