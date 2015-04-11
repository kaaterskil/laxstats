package laxstats.api.sites;

/**
 * {@code SiteStyle} enumerates the qualities of playing fields.
 */
public enum SiteStyle {

   /**
    * The playing venue conforms to competitive standards.
    */
   COMPETITION("Competition"),

   /**
    * The playing venue may or may not conform to competitive standards and is primarily used for
    * practice.
    */
   PRACTICE("Practice"),

   /**
    * The playing venue quality is unknown.
    */
   UNKNOWN("Unknown");

   /**
    * Returns the pretty name of a {@code SiteStyle} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code SiteStye} with the given pretty name.
    * 
    * @param label
    */
   private SiteStyle(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
