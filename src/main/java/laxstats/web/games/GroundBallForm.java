package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code GroundBallForm} contains user-defined information with which to create and update a ground
 * ball play.
 */
public class GroundBallForm extends AbstractPlayForm implements GroundBallResource {

   @NotNull
   private String playerId;

   /**
    * Creates a {@code GroundBallForm}.
    */
   public GroundBallForm() {
      super(PlayType.GROUND_BALL, PlayKey.PLAY);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPlayerId() {
      return playerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }
}
