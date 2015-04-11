package laxstats.api.people;

/**
 * {@code DominantHand} enumerates a player's known favorite hand.
 */
public enum DominantHand {

   /**
    * The player's right hand.
    */
   RIGHT("Right"),

   /**
    * The player's left hand.
    */
   LEFT("Left"),

   /**
    * The player is known to be equally dextrous with either hand.
    */
   AMBIDEXTROUS("Ambidextrous"),

   /**
    * The player is not known to have a favorite hand.
    */
   UNKNOWN("Unknown");

   /**
    * Creates a {@code DominantHand} with the given pretty name.
    *
    * @param label
    */
   private DominantHand(String label) {
      assert label != null;

      this.label = label;
   }

   /**
    * Returns the pretty name of a {@code DominantHand} for use in a dopr-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   private String label;
}
