package laxstats.api;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

/**
 * {@code Common} presents class-level utility methods for use throughout the application.
 */
public class Common {
   public static final LocalDateTime EOT = new LocalDateTime(Long.MAX_VALUE);

   /**
    * Returns the given local date if it is not null, otherwise the default local date is returned.
    *
    * @param targetDate
    * @param defaultDate
    * @return
    */
   public static final LocalDate nvl(LocalDate targetDate, LocalDate defaultDate) {
      if (targetDate == null) {
         return defaultDate;
      }
      return targetDate;
   }

   /**
    * Returns the given string if it is not null or empty, otherwise the default value is returned.
    *
    * @param target
    * @param defaultValue
    * @return
    */
   public static final String nvl(String target, String defaultValue) {
      if (target == null || target.length() == 0) {
         return defaultValue;
      }
      return target;
   }

   /**
    * Returns the given enumerated value if it is not null, otherwise the default value is returned.
    *
    * @param target
    * @param defaultValue
    * @return
    */
   public static final Enum<?> nvl(Enum<?> target, Enum<?> defaultValue) {
      if (target == null) {
         return defaultValue;
      }
      return target;
   }

   /**
    * Returns the given temporal period if it is not null, otherwise the default value is returned.
    *
    * @param target
    * @param defaultValue
    * @return
    */
   public static final Period nvl(Period target, Period defaultValue) {
      if (target == null) {
         return defaultValue;
      }
      return target;
   }

   private Common() {
   }
}
