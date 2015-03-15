package laxstats.web.thymeleaf;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateFormatter implements Formatter<LocalDate> {
   private static Logger logger = LoggerFactory.getLogger(LocalDate.class);
   private static String DEFAULT_DATE_STYLE = "MM/dd/yyyy";
   DateTimeFormatter formatter;

   public LocalDateFormatter() {
      super();
   }

   public DateTimeFormatter getFormatter() {
      if (formatter == null) {
         formatter = DateTimeFormat.forPattern(DEFAULT_DATE_STYLE);
      }
      return formatter;
   }

   @Override
   public String print(LocalDate object, Locale locale) {
      logger.info("Printing a LocalDate");
      String str = "";
      if (object != null) {
         final DateTime date = object.toDateTimeAtCurrentTime();
         str = date.toString(getFormatter());
      }
      return str;
   }

   @Override
   public LocalDate parse(String text, Locale locale) throws ParseException {
      logger.info("Parsing a LocalDate");
      LocalDate date = null;
      if (text != null) {
         try {
            date = getFormatter().parseLocalDate(text);
         } catch (final Exception e) {
            throw new ParseException(e.getMessage(), 0);
         }
      }
      return date;
   }

}
