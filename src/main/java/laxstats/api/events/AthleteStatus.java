package laxstats.api.events;

/**
 * {@code AthleteStatus} enumerates a player's activity at a specified game.
 */
public enum AthleteStatus {

   /**
    * An athlete that played a starting position at a game.
    */
   STARTED("Starter"),

   /**
    * An athlete that played in a non-starting position at a game.
    */
   PLAYED("Player");

   /**
    * Returns the pretty name for this {@code AthleteStatus}
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates an {@code AthleteStatus} with the given label.
    * 
    * @param label
    */
   private AthleteStatus(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
