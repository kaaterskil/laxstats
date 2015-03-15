package laxstats.api.events;

public enum PlayResult {
   GOAL("Goal", PlayKey.GOAL),
   SHOT_MISSED("Missed Shot", PlayKey.PLAY),
   SHOT_SAVED("Saved Shot", PlayKey.PLAY),
   SHOT_BLOCKED("Blocked Shot", PlayKey.PLAY),
   SHOT_OFF_POST("Shot Off Post", PlayKey.PLAY),
   CLEAR_SUCCEEDED("Clear Succeeded", PlayKey.CLEAR),
   CLEAR_FAILED("Clear Failed", PlayKey.CLEAR);

   private String label;
   private PlayKey playKey;

   private PlayResult(String label, PlayKey playKey) {
      this.label = label;
      this.playKey = playKey;
   }

   public String getLabel() {
      return label;
   }

   public PlayKey getPlayKey() {
      return playKey;
   }
}
