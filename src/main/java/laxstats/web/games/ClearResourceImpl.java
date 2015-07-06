package laxstats.web.games;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;

/**
 * {@code ClearResource} represents a clear resource for remote clients.
 */
public class ClearResourceImpl extends AbstractPlayResource implements ClearResource {

   @NotNull
   private PlayResult result;

   /**
    * Creates a {@code ClearResource} with the given information.
    *
    * @param playId
    * @param gameId
    * @param teamSeasonId
    * @param period
    * @param comment
    * @param teamName
    * @param result
    */
   public ClearResourceImpl(String playId, String gameId, String teamSeasonId, int period,
      String comment, String teamName, PlayResult result) {
      super(playId, PlayType.CLEAR, PlayKey.PLAY, gameId, teamSeasonId, period, comment, teamName);
      this.result = result;
   }

   /**
    * Creates an empty {@code ClearResource}.
    */
   public ClearResourceImpl() {
      super(PlayType.CLEAR, PlayKey.PLAY);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PlayResult getResult() {
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setResult(PlayResult result) {
      assert result != null;
      this.result = result;
   }
}
