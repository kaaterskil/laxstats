package laxstats.web.seasons;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class SeasonForm {

   private String id;

   @NotNull
   @Size(min = 3)
   private String description;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   @NotNull
   private LocalDate startsOn;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate endsOn;

   public SeasonForm() {
   }

   public SeasonForm(String id, String description, LocalDate startsOn, LocalDate endsOn) {
      this.id = id;
      this.description = description;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
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

   public LocalDate getStartsOn() {
      return startsOn;
   }

   public void setStartsOn(LocalDate startsOn) {
      this.startsOn = startsOn;
   }

   public LocalDate getEndsOn() {
      return endsOn;
   }

   public void setEndsOn(LocalDate endsOn) {
      this.endsOn = endsOn;
   }
}
