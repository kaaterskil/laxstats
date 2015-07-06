package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code GroundBallResource} represents a ground ball resource for remote clients.
 */
public class GroundBallResourceImpl extends AbstractPlayResource implements GroundBallResource {

   @NotNull
   private String playerId;

   /**
    * Creates a {@code GroundBallResource} from the given information.
    *
    * @param playId
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    * @param playerId
    */
   public GroundBallResourceImpl(String playId, String gameId, String teamSeasonId, int period,
      String comment, String teamName, String playerId) {
      super(playId, PlayType.GROUND_BALL, PlayKey.PLAY, gameId, teamSeasonId, period, comment,
         teamName);
      this.playerId = playerId;
   }

   /**
    * Creates an empty {@code GroundBallResource}.
    */
   public GroundBallResourceImpl() {
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
      assert playerId != null;
      this.playerId = playerId;
   }
}
