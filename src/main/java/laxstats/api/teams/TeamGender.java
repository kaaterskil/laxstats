package laxstats.api.teams;

/**
 * {@code TeamGender} enumerates the gender-specific types of teams.
 */
public enum TeamGender {

   /**
    * A college or adult team for male players.
    */
   MEN("Men"),

   /**
    * A college or adult team for female players.
    */
   WOMEN("Women"),

   /**
    * A youth or high-school team for male players.
    */
   BOYS("Boys"),

   /**
    * A youth or high school team for female players.
    */
   GIRLS("Girls");

   /**
    * Returns the pretty name of a {@code TeamGender} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code TeamGender} with the given pretyy name.
    *
    * @param label
    */
   private TeamGender(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
