package laxstats.api.games;

/**
 * {@code PlayType} constants represent the object subtypes of a {@code Play} and are used as the
 * discriminator values in the {@code Play} single-table inheritance.
 */
public class PlayType {

   /**
    * A play designed to move the ball from the defensive end to the offensive end after a save or
    * turnover.
    */
   public static final String CLEAR = "CLEAR";

   /**
    * Takes place at the start of each quarter, after every goal, and after certain dead balls. Two
    * opposing players crouch down at midfield, hold their sticks flat on the ground and press the
    * backs of their stick pockets together. The ball is then placed between the pockets and, when
    * signaled to start, the players “rake” or clamp on the ball to vie for control.
    */
   public static final String FACEOFF = "FACEOFF";

   /**
    * The one-point score made by landing the ball in the opponent's goal.
    */
   public static final String GOAL = "GOAL";

   /**
    * The competition for control of loose balls.
    */
   public static final String GROUND_BALL = "GROUND_BALL";

   /**
    * The commission of a rule violation.
    */
   public static final String PENALTY = "PENALTY";

   /**
    * An attempt to score.
    */
   public static final String SHOT = "SHOT";
}
