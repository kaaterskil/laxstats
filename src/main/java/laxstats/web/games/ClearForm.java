package laxstats.web.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;

/**
 * {@code ClearForm} contains user-defined information with which to create and update a clear play.
 */
public class ClearForm extends AbstractPlayForm {
   @NotNull
   private PlayResult result;

   private List<PlayResult> results;

   /**
    * Creates a {@code ClearForm}.
    */
   public ClearForm() {
      super(PlayType.CLEAR, PlayKey.PLAY);
   }

   /**
    * Returns the play result. Never null.
    * 
    * @return
    */
   public PlayResult getResult() {
      return result;
   }

   /**
    * Sets the play result. Must not be null.
    * 
    * @param result
    */
   public void setResult(PlayResult result) {
      this.result = result;
   }

   /*---------- Drop-down menu options ----------*/

   public List<PlayResult> getResults() {
      if (results == null) {
         results = new ArrayList<PlayResult>();
         for (final PlayResult each : Arrays.asList(PlayResult.values())) {
            if (each.getPlayKey().equals(PlayKey.CLEAR)) {
               results.add(each);
            }
         }
      }
      return results;
   }
}
