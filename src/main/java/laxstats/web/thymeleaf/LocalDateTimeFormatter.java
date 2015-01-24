package laxstats.web.thymeleaf;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
	private static String DEFAULT_DATETIME_STYLE = "MM/dd/yyyy hh:mm";
	DateTimeFormatter formatter;

	public LocalDateTimeFormatter() {
		super();
	}

	public DateTimeFormatter getFormatter() {
		if (formatter == null) {
			formatter = DateTimeFormat.forPattern(DEFAULT_DATETIME_STYLE);
		}
		return formatter;
	}

	@Override
	public String print(LocalDateTime object, Locale locale) {
		String str = "";
		if (object != null) {
			final DateTime date = object.toDateTime();
			str = date.toString(getFormatter());
		}
		return str;
	}

	@Override
	public LocalDateTime parse(String text, Locale locale)
			throws ParseException {
		LocalDateTime date = null;
		if (text != null) {
			try {
				date = getFormatter().parseLocalDateTime(text);
			} catch (final Exception e) {
				throw new ParseException(e.getMessage(), 0);
			}
		}
		return date;
	}

}
