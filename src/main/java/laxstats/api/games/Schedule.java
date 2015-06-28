package laxstats.api.games;

/**
 * {@code Schedule} enumerates the type of games in a season.
 */
public enum Schedule {

   /**
    * A game occurring before the official start of the regular season.
    */
   PRE_SEASON("Pre-season"),

   /**
    * A competitive game occurring during the official season, the results of which are accumulated
    * as part of the team's ranking.
    */
   REGULAR("Regular season"),

   /**
    * One of a round of playoff games which culminate in a final championship.
    */
   POST_SEASON("Post-season"),

   /**
    * A game whose impact on a team's rankings is either zero or greatly reduced. Matches of this
    * type are often used to help managers select players for the competitive matches of a
    * tournament and, if the players usually play in different teams in other leagues, it's an
    * opportunity for the players to learn to work with each other. The games can be held between
    * separate teams or between parts of the same team.
    */
   EXHIBITION("Exhibition");

   /**
    * Returns the pretty name for this {@code Schedule} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code Schedule} with the given label.
    *
    * @param label
    */
   private Schedule(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
