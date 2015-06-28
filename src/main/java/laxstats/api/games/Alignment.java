package laxstats.api.games;

/**
 * {@code Alignment} enumerates the relationship a team participating in event has with the site
 * venue.
 */
public enum Alignment {

   /**
    * A team playing at its home site.
    */
   HOME("Home"),

   /**
    * A team visiting another team's home site.
    */
   AWAY("Away"),

   /**
    * A team playing at a neutral site.
    */
   NEUTRAL("Neutral");

   /**
    * Returns the pretty name for an {@code Alignment}
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates an {@code Alignment} with a specific label.
    * 
    * @param label
    */
   private Alignment(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
