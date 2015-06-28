package laxstats.api.utils;

public class Constants {

   /**
    * The maximum length of a string used as a unique identifier in the database.
    */
   public static final int MAX_LENGTH_DATABASE_KEY = 36;

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
}
