package laxstats.api.games;

/**
 * {@code PlayRole} enumerates the responsibility of an athlete in a given play.
 */
public enum PlayRole {

   /**
    * The athlete who shot the successful goal.
    */
   SCORER("Scorer"),

   /**
    * The last offensive pass that sets up a goal. An assist is awarded if the receiver of the pass
    * does not have to dodge a defender on his way to the goal. One assist per goal may be awarded.
    */
   ASSIST("Assist"),

   /**
    * The player whose primary responsibility is to defend the goal.
    */
   GOALIE("Goalie"),

   /**
    * The offensive player who attempted an unsuccessful goal.
    */
   SHOOTER("Shooter"),

   /**
    * The defensive player who prevented a goal.
    */
   BLOCKER("Blocker"),

   /**
    * The player, either offensive or defensive, who retrieved a contended ground ball.
    */
   GROUND_BALL("Ground Ball"),

   /**
    * The player who committed a violation.
    */
   PENALTY_COMMITTED_BY("Penalty Committed By"),

   /**
    * The player upon whom a violation was committed.
    */
   PENALTY_COMMITTED_AGAINST("Penalty Committed Against"),

   /**
    * The player who successfully retrieved the ball as a result of a face-off.
    */
   FACEOFF_WINNER("Faceoff Winner"),

   /**
    * The player who did not retrieve the ball as a result of a face-off.
    */
   FACEOFF_LOSER("Faceoff Loser");

   /**
    * Returns the pretty name for a {@code PlayRole}, used for a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code PlayRole} with the given name.
    *
    * @param label
    */
   private PlayRole(String label) {
      assert label != null;

      this.label = label;
   }

   private final String label;
}
