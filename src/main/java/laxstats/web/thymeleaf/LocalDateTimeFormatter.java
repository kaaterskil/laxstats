package laxstats.web.thymeleaf;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.format.Formatter;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
	DateTimeFormatter formatter;

	public LocalDateTimeFormatter() {
		super();
	}

	public DateTimeFormatter getFormatter() {
		if (formatter == null) {
			formatter = ISODateTimeFormat.dateTime();
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
	public LocalDateTime parse(String text, Locale locale)
			throws ParseException {
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
