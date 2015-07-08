package laxstats.web.seasons;

import org.joda.time.LocalDate;

public interface SeasonResource {

   /**
    * Returns the season's unique identifier, or null if the season has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the season's unique identifier. Use null only if the season has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns a description of the season. Never null.
    *
    * @return
    */
   public String getDescription();

   /**
    * Sets a description of the season. Must not be null.
    *
    * @param description
    */
   public void setDescription(String description);

   /**
    * Returns a string representation of the season start date. Never null.
    *
    * @return
    */
   public String getStartsOn();

   /**
    * Sets a string representation of the season start date. Must not be null.
    *
    * @param startsOn
    */
   public void setStartsOn(String startsOn);

   /**
    * Returns a string representation of the season end date, or null.
    *
    * @return
    */
   public String getEndsOn();

   /**
    * Sets a string representation of the season end date. Use null for none or unknown.
    *
    * @param endsOn
    */
   public void setEndsOn(String endsOn);

   /**
    * Returns a LocalDate representation of the season start date. Never null.
    *
    * @return
    */
   public LocalDate getStartsOnAsLocalDate();

   /**
    * Sets a LocalDate representation of the season start date. Must not be null.
    *
    * @param startsOn
    */
   public void setStartsOnAsLocalDate(LocalDate startsOn);

   /**
    * Returns a LocalDate representation of the season end date, or null.
    *
    * @return
    */
   public LocalDate getEndsOnAsLocalDate();

   /**
    * Sets a LocalDate representation of the season end date. Use null for none or unknown.
    *
    * @param endsOn
    */
   public void setEndsOnAsLocalDate(LocalDate endsOn);

}
