package laxstats.api.sites;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@code SiteStyle} enumerates the qualities of playing fields.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
   @JsonProperty("label")
   public String getLabel() {
      return label;
   }

   @JsonProperty("value")
   public String getValue() {
      return name();
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
