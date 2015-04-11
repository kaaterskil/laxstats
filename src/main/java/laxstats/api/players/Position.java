package laxstats.api.players;

/**
 * {@code Position} enumerates the various roles that a {@code Person} may have on a team roster,
 * whether as an athlete or an official.
 */
public enum Position {

   /**
    * The player position in the game that is stationed in the offensive end and is responsible for
    * offense primarily.
    */
   ATTACK("Attack"),

   /**
    * A player position that covers the whole field. Each team has three on the field and they start
    * the game and face offs at the midfield line. players called Midfielders and Middies.
    */
   MIDFIELD("Midfield"),

   /**
    * The mid-field defensive position on the field.
    */
   LSM("Long-Stick Midfield"),

   /**
    * The player position that is responsible for defense primarily and are stationed in the
    * defensive end near their goal.
    */
   DEFENSE("Defense"),

   /**
    * The player position responsible for protecting the actual goal. Very rarely leaves the
    * defending goal area and uses a larger stick head to block shots.
    */
   GOALIE("Goalie"),

   /**
    * The highest ranking on-field official.
    */
   REFEREE("Referee"),

   /**
    * The second on-field official.
    */
   UMPIRE("Umpire"),

   /**
    * The third on-field official.
    */
   FIELD_JUDGE("Field Judge"),

   /**
    * The individual responsible for training and directing the team and who makes decisions about
    * how the team plays during games.
    */
   COACH("Coach"),

   /**
    * A member of a team's coaching staff other than the head coach.
    */
   ASST_COACH("Assistant Coach"),

   /**
    * A member of a team's coaching staff responsible for support and administrative capacity,
    * usually planning and record keeping.
    */
   MANAGER("Manager"),

   /**
    * The game-day individual responsible for record keeping.
    */
   SCOREKEEPER("Scorekeeper"),

   /**
    * The game-day individual responsible for tracking penalties.
    */
   TIMEKEEPER("Timekeeper"),

   /**
    * In youth lacrosse, an adult assigned to help encourage, maintain and manage the sportsmanlike
    * behavior of spectators and fans.
    */
   SIDELINE_MANAGER("Sideline Manager");

   /**
    * Returns the pretty name for a {@code Position} for use in a drop down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code Position} with the given pretyy name.
    *
    * @param label
    */
   private Position(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
