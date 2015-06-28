package laxstats.web.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayType;

public class ClearForm extends AbstractPlayForm {
   private PlayResult result;
   private List<PlayResult> results;

   public ClearForm() {
      super(PlayType.CLEAR, PlayKey.PLAY);
   }

   /*---------- Getter/Setters ----------*/

   public PlayResult getResult() {
      return result;
   }

   public void setResult(PlayResult result) {
      this.result = result;
   }

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