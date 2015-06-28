package laxstats.api.games;

/**
 * {@code ScoreAttemptType} enumerates the type of shots a player may make.
 */
public enum ScoreAttemptType {

   /**
    * A shot against an opponent's goal.
    */
   REGULAR("Regular"),

   /**
    * A shot against an opponent's goal permitted as the results of a violation.
    */
   PENALTY_SHOT("Penalty Shot"),

   /**
    * A shot against an undefended oponent's goal.
    */
   EMPTY_NET("Empty Net"),

   /**
    * A shot against one's own goal.
    */
   OWN_GOAL("Own Goal");

   /**
    * Returns the pretty name of a {@code ShotAttemptType} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code ShotAttemptType} with the given label.
    *
    * @param label
    */
   private ScoreAttemptType(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
