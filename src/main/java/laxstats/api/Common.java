package laxstats.api;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class Common {
	public static final LocalDateTime EOT = new LocalDateTime(Long.MAX_VALUE);

	public static final LocalDate nvl(LocalDate targetDate,
			LocalDate defaultDate) {
		if (targetDate == null) {
			return defaultDate;
		}
		return targetDate;
	}

	public static final String nvl(String target, String defaultValue) {
		if (target == null) {
			return defaultValue;
		}
		return target;
	}

	public static final Enum<?> nvl(Enum<?> target, Enum<?> defaultValue) {
		if (target == null) {
			return defaultValue;
		}
		return target;
	}

	private Common() {
	}
}
