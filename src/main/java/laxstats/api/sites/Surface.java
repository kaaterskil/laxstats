package laxstats.api.sites;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@code Surface} enumerates the types of playing field surfaces.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
   @JsonProperty("label")
   public String getLabel() {
      return label;
   }

   @JsonProperty("value")
   public String getValue() {
      return name();
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
