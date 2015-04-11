package laxstats.api.events;

/**
 * {@code Strength} enumerates the balance of players on the field at the moment of a play from the
 * perspective of the team making the play.
 */
public enum Strength {

   /**
    * The condition where the two teams have an equal number of players on the field.
    */
   EVEN_STRENGTH,

   /**
    * The condition where the team making the play has more players on the field than the opposing
    * team.
    */
   MAN_UP,

   /**
    * The condition where the team making the play has fewer players on the field than the opposing
    * team.
    */
   MAN_DOWN;
}
