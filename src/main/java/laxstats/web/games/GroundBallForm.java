package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code GroundBallForm} contains user-defined information with which to create and update a ground
 * ball play.
 */
public class GroundBallForm extends AbstractPlayForm {
   @NotNull
   private String playerId;

   /**
    * Creates a {@code GroundBallForm}.
    */
   public GroundBallForm() {
      super(PlayType.GROUND_BALL, PlayKey.PLAY);
   }

   /**
    * Returns the identifier of the player making the play. Never null.
    *
    * @return
    */
   public String getPlayerId() {
      return playerId;
   }

   /**
    * Sets the identifier of the player making the play. Must not be null.
    *
    * @param playerId
    */
   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }
}
