package laxstats.web.seasons;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.utils.Constants;

import org.joda.time.LocalDate;

/**
 * {@code SeasonResource} represents season resource for remote clients.
 */
public class SeasonResourceImpl implements SeasonResource {
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
   public SeasonResourceImpl(String id, String description, String startsOn, String endsOn) {
      this.id = id;
      this.description = description;
      this.startsOn = startsOn;
      this.endsOn = endsOn;
   }

   /**
    * Creates an empty {@code SeasonResource}.
    */
   public SeasonResourceImpl() {
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
      return startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOn(String startsOn) {
      this.startsOn = startsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getEndsOn() {
      return endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOn(String endsOn) {
      this.endsOn = endsOn;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getStartsOnAsLocalDate() {
      return startsOn == null ? null : LocalDate.parse(startsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStartsOnAsLocalDate(LocalDate startsOn) {
      this.startsOn = startsOn == null ? null : startsOn.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getEndsOnAsLocalDate() {
      return endsOn == null ? null : LocalDate.parse(endsOn);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEndsOnAsLocalDate(LocalDate endsOn) {
      this.endsOn = endsOn == null ? null : endsOn.toString();
   }
}
