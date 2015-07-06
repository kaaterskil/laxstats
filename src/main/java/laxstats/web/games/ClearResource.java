package laxstats.web.games;

import laxstats.api.games.PlayResult;

public interface ClearResource extends PlayResource {

   /**
    * Returns the play result. Never null.
    *
    * @return
    */
   public PlayResult getResult();

   /**
    * Sets the play result. Must not be null.
    *
    * @param result
    */
   public void setResult(PlayResult result);

}
