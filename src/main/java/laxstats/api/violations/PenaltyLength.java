package laxstats.api.violations;

/**
 * {@code PenaltyLength} enumerates the length of player suspension for a given penalty.
 */
public enum PenaltyLength {

   /**
    * Thirty seconds.
    */
   THIRTY_SECONDS("0:30", 30),

   /**
    * One minute.
    */
   ONE_MINUTE("1:00", 60),

   /**
    * Two minutes.
    */
   TWO_MINUTES("2:00", 120),

   /**
    * Three minutes.
    */
   THREE_MINUTES("3:00", 180),

   /**
    * Award of the ball to the team fouled in lieu of suspension.
    */
   AWARD("Award of ball", -1),

   /**
    * Ejection.
    */
   EJECTION("Ejection", -1);

   /**
    * Returns the pretty name of a {@code PenaltyLength} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Returns the penalty duration in seconds, or -1 if the penalty has no specified duration.
    *
    * @return
    */
   public int getDuration() {
      return duration;
   }

   /**
    * Creates a {@code PenaltyLength} with the given pretty name and duration in seconds.
    *
    * @param label
    * @param duration The duration in seconds, or -1 if the duration has no length.
    */
   private PenaltyLength(String label, int duration) {
      assert label != null;
      assert duration > -1;

      this.label = label;
      this.duration = duration;
   }

   private final String label;
   private int duration;
}
