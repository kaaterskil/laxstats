package laxstats.api.players;

/**
 * {@code Role} enumerates the high-level relationship a {@code Person} may have with a team.
 */
public enum Role {

   /**
    * A player.
    */
   ATHLETE("Athlete"),

   /**
    * The team's head coach.
    */
   COACH("Coach"),

   /**
    * A member of the team's coaching staff other than the head coach.
    */
   ASSISTANT("Assistant Coach"),

   /**
    * A member of the team staff responsible for administration.
    */
   MANAGER("Manager"),

   /**
    * An on-field official responsible for enforcing game rules.
    */
   OFFICIAL("Official"),

   /**
    * A fan.
    */
   BOOSTER("Booster"),

   /**
    * In youth lacrosse, a school official resposible for guiding a player's non-sports academic
    * participation.
    */
   COUNSELOR("Counselor");

   /**
    * Returns the pretty name of a {@code Role} for use in a drop-doen menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code Role} with the given pretty name.
    * 
    * @param label
    */
   private Role(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
