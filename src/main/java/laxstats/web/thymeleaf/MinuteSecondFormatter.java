package laxstats.web.thymeleaf;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MinuteSecondFormatter implements Formatter<Period> {
   private PeriodFormatter formatter;

   public MinuteSecondFormatter() {
      super();
   }

   public PeriodFormatter getFormatter() {
      if (formatter == null) {
         formatter =
            new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2).appendMinutes()
               .appendSeparator(":").printZeroAlways().minimumPrintedDigits(2).appendSeconds()
               .toFormatter();
      }
      return formatter;
   }

   @Override
   public String print(Period object, Locale locale) {
      String result = "";
      if (object != null) {
         result = getFormatter().print(object);
      }
      return result;
   }

   @Override
   public Period parse(String text, Locale locale) throws ParseException {
      Period result = null;
      if (text != null) {
         try {
            result = getFormatter().parsePeriod(text);
         } catch (final Exception e) {
            throw new ParseException(e.getMessage(), 0);
         }
      }
      return result;
   }

}
