package laxstats.api.utils;

public class Constants {

   /**
    * The maximum length of a string used as a unique identifier in the database.
    */
   public static final int MAX_LENGTH_DATABASE_KEY = 36;

   /**
    * The maximum length of an enum string in the database.
    */
   public static final int MAX_LENGTH_ENUM_STRING = 20;

   /**
    * The Hibernate datatype used by persistent LocalDate fields.
    */
   public static final String LOCAL_DATE_DATABASE_TYPE =
      "org.jadira.usertype.dateandtime.joda.PersistentLocalDate";

   /**
    * The Hibernate datatype used by persistent LocalDateTime fields
    */
   public static final String LOCAL_DATETIME_DATABASE_TYPE =
      "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime";

   /**
    * the maximum length of a person's name prefix or suffix.
    */
   public static final int MAX_LENGTH_NAME_PREFIX_OR_SUFFIX = 10;

   /**
    * The maximum length of a person's first or middle name.
    */
   public static final int MAX_LENGTH_FIRST_OR_MIDDLE_NAME = 20;

   /**
    * The maximum length of a person's last name.
    */
   public static final int MAX_LENGTH_LAST_NAME = 30;

   /**
    * The maximum length of a postal street address.
    */
   public static final int MAX_LENGTH_ADDRESS = 34;

   /**
    * The maximum length for the name of a city.
    */
   public static final int MAX_LENGTH_CITY = 30;

   /**
    * The maximum length for postal code.
    */
   public static final int MAX_LENGTH_LONG_POSTAL_CODE = 10;

   /**
    * The maximum length of a contact value (e.g. email address, telephone etc.)
    */
   public static final int MAX_LENGTH_CONTACT_VALUE = 255;

   /**
    * Regex pattern string that matches a 5- or 9-digit US ZIP Code with a dash or space.
    */
   public static final String PATTERN_LONG_ZIP_CODE = "^[0-9]{5}([\\s\\-][0-9]{4})?$";

   /**
    * Pattern string to convert to DateTime format.
    */
   public static final String PATTERN_DATE_TIME_FORMAT = "yyyy-MM-dd";
}
