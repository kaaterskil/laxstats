package laxstats.api.events;

/**
 * {@code Status} enumerates the state of a game.
 */
public enum Status {

   /**
    * A game that has not yet been played, but with a known date and time.
    */
   SCHEDULED("Scheduled"),

   /**
    * A game that has already been played to completion.
    */
   OCCURRED("Occurred"),

   /**
    * A game that has been rescheduled to an as-yet unknown new date and time in the future.
    */
   POSTPONED("Postponed"),

   /**
    * A game temporarily stopped before the end of regular time, usually due to weather conditions,
    * with the intention of later completion.
    */
   SUSPENDED("Suspended"),

   /**
    * A game stopped before the end of regular time and not resumed.
    */
   HALTED("Halted"),

   /**
    * A game in which one team concedes the result before the end of regular time.
    */
   FORFEITED("Forfeited"),

   /**
    * A game that has been rescheduled to a known new date and time in the future.
    */
   RESCHEDULED("Rescheduled"),

   /**
    * A game the start time of which is later than scheduled, usually due to weather conditions.
    */
   DELAYED("Delayed"),

   /**
    * A previously scheduled game that will not be played.
    */
   CANCELLED("Cancelled"),

   /**
    * A scheduled game that is dependent on the outcome of another game.
    */
   IF_NECESSARY("If Necessary"),

   /**
    * A scheduled game that is no longer necessary.
    */
   DISCARDED("Discarded");

   /**
    * Creates a {@code Status} with the given label.
    *
    * @param label
    */
   private Status(String label) {
      assert label != null;

      this.label = label;
   }

   /**
    * Returns the pretty name of a {@code Status} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   private String label;

}
