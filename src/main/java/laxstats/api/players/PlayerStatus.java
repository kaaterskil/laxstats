package laxstats.api.players;

/**
 * {@code PlayerStatus} enumerates the current state of a {@code Player} in a given season.
 */
public enum PlayerStatus {

   /**
    * A player registered on a team's roster.
    */
   ACTIVE("Active"),

   /**
    * A player registered on a team's roster but who is not permitted or able to play due to injury.
    */
   INJURED("Injured"),

   /**
    * A player who is temporarily permitted to play pending an official decision.
    */
   TRYOUT("Tryout"),

   /**
    * A player who is registered on a team's roster but who is no longer able or willing to play.
    */
   INACTIVE("Inactive");

   /**
    * Returns the pretty name of a {@code PlayerStatus} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code PlayerStatus} with the given pretty name.
    *
    * @param label
    */
   private PlayerStatus(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
