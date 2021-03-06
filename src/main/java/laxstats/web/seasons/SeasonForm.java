package laxstats.web.seasons;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class SeasonForm implements SeasonResource {

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

   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getDescription() {
      return description;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getStartsOn() {
      return startsOn == null ? null : startsOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOn(String startsOn) {
      this.startsOn = startsOn == null ? null : LocalDate.parse(startsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getStartsOnAsLocalDate() {
      return startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOnAsLocalDate(LocalDate startsOn) {
      this.startsOn = startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getEndsOn() {
      return endsOn == null ? null : endsOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn == null ? null : LocalDate.parse(endsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getEndsOnAsLocalDate() {
      return endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOnAsLocalDate(LocalDate endsOn) {
      this.endsOn = endsOn;
   }
}
