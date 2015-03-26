package laxstats.web.thymeleaf;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
   DateTimeFormatter formatter;

   public LocalDateTimeFormatter() {
      super();
   }

   public DateTimeFormatter getFormatter() {
      if (formatter == null) {
         formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
      }
      return formatter;
   }

   @Override
   public String print(LocalDateTime localDateTime, Locale locale) {
      String str = "";
      if (localDateTime != null) {
         final DateTime datetime = localDateTime.toDateTime();
         str = datetime.toString(getFormatter());
      }
      return str;
   }

   @Override
   public LocalDateTime parse(String text, Locale locale) throws ParseException {
      LocalDateTime localDateTime = null;
      if (text != null) {
         try {
            localDateTime = getFormatter().parseLocalDateTime(text);
         } catch (final Exception e) {
            throw new ParseException(e.getMessage(), 0);
         }
      }
      return localDateTime;
   }

}
