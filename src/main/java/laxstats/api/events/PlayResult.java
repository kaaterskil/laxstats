package laxstats.api.events;

/**
 * {@code PlayResult} enumerates the result of efforts of the team possessing the ball to score or
 * otherwise move the ball down-field.
 */
public enum PlayResult {

   /**
    * The result of a successful shot attempt.
    */
   GOAL("Goal", PlayKey.GOAL),

   /**
    * An unobstructed shot that did not enter the goal.
    */
   SHOT_MISSED("Missed Shot", PlayKey.PLAY),

   /**
    * An unobstructed shot deflected or caught by the goalie.
    */
   SHOT_SAVED("Saved Shot", PlayKey.PLAY),

   /**
    * A shot deflected by a player before reaching the goal.
    */
   SHOT_BLOCKED("Blocked Shot", PlayKey.PLAY),

   /**
    * A shot deflected by the frame of the goal.
    */
   SHOT_OFF_POST("Shot Off Post", PlayKey.PLAY),

   /**
    * A successful attempt to clear the ball out of the scoring area by the defending team.
    */
   CLEAR_SUCCEEDED("Clear Succeeded", PlayKey.CLEAR),

   /**
    * A failed attempt to clear the ball out of the scoring area by the defending team.
    */
   CLEAR_FAILED("Clear Failed", PlayKey.CLEAR);

   /**
    * Returns the pretty name for the {@code PlayResult} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Returns the corresponding {@code PlayKey} for the {@code PlayResult}.
    *
    * @return
    */
   public PlayKey getPlayKey() {
      return playKey;
   }

   /**
    * Creates a {@code PlayResult} with the given pretty name and {@code PlayKey}.
    *
    * @param label
    * @param playKey
    */
   private PlayResult(String label, PlayKey playKey) {
      assert label != null;
      assert playKey != null;

      this.label = label;
      this.playKey = playKey;
   }

   private final String label;
   private final PlayKey playKey;
}
