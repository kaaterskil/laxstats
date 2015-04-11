package laxstats.api.sites;

/**
 * {@code Surface} enumerates the types of plyaing field surfaces.
 */
public enum Surface {

   /**
    * The playing surface is natural grass.
    */
   GRASS("Grass"),

   /**
    * The playing surface is artificial.
    */
   TURF("Turf"),

   /**
    * The playing surface material is unknown.
    */
   UNKNOWN("Unknown");

   /**
    * Returns the pretty name of a {@code Surface} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code Surface} with the given pretty name.
    * 
    * @param label
    */
   private Surface(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
