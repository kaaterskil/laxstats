package laxstats.api.sites;

/**
 * {@code SiteAlignment} enumerates the relationship that a game venue has with a team.
 */
public enum SiteAlignment {

   /**
    * The venue is either the team's designated playing site or is in the team's lowest-level
    * organizing region.
    */
   HOME("Home"),

   /**
    * The venue has no organizing or regional relationship to the team.
    */
   NEUTRAL("Neutral");


   /**
    * Returns the pretty name for a {@code SiteAlignment} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code SiteAlignment} with the given pretty name.
    *
    * @param label
    */
   private SiteAlignment(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
