package laxstats.web.thymeleaf;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

public class LocalTimeFormatter implements Formatter<LocalTime> {
	private static String DEFAULT_TIME_STYLE = "mm:ss";
	DateTimeFormatter formatter;

	public LocalTimeFormatter() {
		super();
	}

	public DateTimeFormatter getFormatter() {
		if (formatter == null) {
			formatter = DateTimeFormat.forPattern(DEFAULT_TIME_STYLE);
		}
		return formatter;
	}

	@Override
	public String print(LocalTime object, Locale locale) {
		String str = "";
		if (object != null) {
			final DateTime date = object.toDateTimeToday();
			str = date.toString(getFormatter());
		}
		return str;
	}

	@Override
	public LocalTime parse(String text, Locale locale) throws ParseException {
		LocalTime time = null;
		if (text != null) {
			try {
				time = getFormatter().parseLocalTime(text);
			} catch (final Exception e) {
				throw new ParseException(e.getMessage(), 0);
			}
		}
		return time;
	}

}
