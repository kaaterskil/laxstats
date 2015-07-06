package laxstats.web.games;

public interface GroundBallResource extends PlayResource {

   /**
    * Returns the identifier of the player making the play. Never null.
    *
    * @return
    */
   public String getPlayerId();

   /**
    * Sets the identifier of the player making the play. Must not be null.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId);

}
