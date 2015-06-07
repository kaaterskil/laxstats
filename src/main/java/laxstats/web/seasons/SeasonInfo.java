package laxstats.web.seasons;

import javax.validation.constraints.NotNull;

/**
 * {@code SeasonInfo} represents a presentation-level DTO of a {@code SeasonEntry} entity that does
 * not expose extraneous fields.
 */
public class SeasonInfo {
   private String id;

   @NotNull
   private String description;

   @NotNull
   private String startsOn;

   private String endsOn;

   /*---------- Constructors ----------*/

   public SeasonInfo(String id, String description, String startsOn, String endsOn) {
      this.id = id;
      this.description = description;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
   }

   public SeasonInfo() {
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getStartsOn() {
      return startsOn;
   }

   public void setStartsOn(String startsOn) {
      this.startsOn = startsOn;
   }

   public String getEndsOn() {
      return endsOn;
   }

   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn;
   }
}
