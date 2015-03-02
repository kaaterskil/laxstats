package laxstats.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailValidator implements Validator {
	private static String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	private static Validator INSTANCE = new EmailValidator();

	public static Validator getInstance() {
		return INSTANCE;
	}

	private Pattern pattern;

	protected EmailValidator() {
		initialize();
	}

	@Override
	public boolean isValid(Object value) {
		if (value == null) {
			return true;
		}
		if (!(value instanceof String)) {
			return false;
		}

		final String input = (String) value;
		if (input.length() == 0) {
			return true;
		}

		final Matcher m = pattern.matcher(input);
		return m.matches();
	}

	private void initialize() {
		pattern = java.util.regex.Pattern.compile("^" + ATOM + "+(\\." + ATOM
				+ "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$",
				java.util.regex.Pattern.CASE_INSENSITIVE);
	}

}
