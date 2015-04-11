package laxstats.api.teams;

/**
 * {@code Letter} enumerates the groups of athletic teams representing a secondary school.
 */
public enum Letter {

   /**
    * The first-string team.
    */
   VARSITY("Varsity"),

   /**
    * The second-string team.
    */
   JV("Junior Varsity"),

   /**
    * A team composed of first-year students.
    */
   FRESHMAN("Freshman"),

   /**
    * A privately-sponsored team for adult athletes.
    */
   CLUB("Club"),

   /**
    * A privately sponsored team for minor athletes.
    */
   YOUTH("Youth");

   /**
    * Returns the pretty name of a {@code Letter} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code Letter} with the given pertty name.
    * 
    * @param label
    */
   private Letter(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
