package laxstats.web.seasons;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.utils.Constants;

/**
 * {@code SeasonResource} represents season resource for remote clients.
 */
public class SeasonResource {
   private String id;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING)
   private String description;

   @NotNull
   private String startsOn;

   private String endsOn;

   /**
    * Creates a {@code SeasonResource} with the given information.
    *
    * @param id
    * @param description
    * @param startsOn
    * @param endsOn
    */
   public SeasonResource(String id, String description, String startsOn, String endsOn) {
      this.id = id;
      this.description = description;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
   }

   /**
    * Creates an empty {@code SeasonResource}.
    */
   public SeasonResource() {
   }

   /**
    * Returns the season's unique identifier, or null if the season has not been persisted.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the season's unique identifier. Use null only if the season has not been persisted.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns a description of the season. Never null.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets a description of the season. Must not be null.
    *
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Returns a string representation of the season start date. Never null.
    *
    * @return
    */
   public String getStartsOn() {
      return startsOn;
   }

   /**
    * Sets a string representation of the season start date. Must not be null.
    *
    * @param startsOn
    */
   public void setStartsOn(String startsOn) {
      this.startsOn = startsOn;
   }

   /**
    * Returns a string representation of the season end date, or null.
    *
    * @return
    */
   public String getEndsOn() {
      return endsOn;
   }

   /**
    * Sets a string representation of the season end date. Use null for none or unknown.
    *
    * @param endsOn
    */
   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn;
   }
}
