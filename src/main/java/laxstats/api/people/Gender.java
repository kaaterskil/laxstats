package laxstats.api.people;

/**
 * {@code Gender} enumerates a player's sex.
 */
public enum Gender {
   MALE("Male"),
   FEMALE("Female");

   private String label;

   /**
    * Creates a {@code Gender} with the given pretty name.
    *
    * @param label
    */
   private Gender(String label) {
      assert label != null;

      this.label = label;
   }

   /**
    * Returns the pretty name of a {@code Gender} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

}
