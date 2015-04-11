package laxstats.api.events;

/**
 * {@code Conditions} enumerates the weather conditions at a game.
 */
public enum Conditions {

   /**
    * When there are no opaque (not transparent) clouds. Same as Clear.
    */
   SUNNY("Sunny"),

   /**
    * Sky condition of less than 1/10 cloud coverage.
    */
   CLEAR("Clear"),

   /**
    * Between 3/8 and 5/8 of the sky is covered by clouds. Used more frequently at night.
    */
   PARTLY_CLOUDY("Party Cloudy"),

   /**
    * Similar to partly cloudy. Used to emphasize daytime sunshine.
    */
   PARTLY_SUNNY("Partly Sunny"),

   /**
    * When the 6/8th to 7/8ths of the sky is covered by with opaque (not transparent) clouds.
    */
   MOSTLY_CLOUDY("Mostly Cloudy"),

   /**
    * When 7/8ths or more of the sky is covered by clouds.
    */
   CLOUDY("Cloudy"),

   /**
    * Sky condition when greater than 9/10ths of the sky is covered by clouds.
    */
   OVERCAST("Overcast"),

   /**
    * Wind in the range of 15 miles per hour to 25 mile per hour with mild or warm temperatures.
    */
   BREEZY("Breezy"),

   /**
    * 20 to 30 mph winds.
    */
   WINDY("Windy"),

   /**
    * Small, slowly falling water droplets, with diameters between .2 and .5 millimeters.
    */
   DRIZZLE("Drizzle"),

   /**
    * Precipitation characterized by the suddenness with which it starts and stops, by rapid changes
    * in intensity, and rapid changes in the appearance of the sky.
    */
   SHOWERS("Showers"),

   /**
    * Precipitation that falls to earth in drops more than 0.5 mm in diameter.
    */
   RAIN("Rain"),

   /**
    * Rain that freezes on objects such as trees, cars and roads, forming a coating or glaze of ice.
    * Temperatures at higher levels are warm enough for rain to form, but surface temperatures are
    * below 32 degrees Fahrenheit, causing the rain to freeze on impact.
    */
   FREEZING_RAIN("Freezing Rain"),

   /**
    * Rain drops that freeze into ice pellets before reaching the ground
    */
   SLEET("Sleet"),

   /**
    * Snow fall.
    */
   SNOW("Snow");

   /**
    * Returns the pretty name for this weather condition.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code Conditions} with the given label.
    *
    * @param label
    */
   private Conditions(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
