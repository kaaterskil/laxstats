package laxstats.api.events;

import org.joda.time.LocalTime;

public class PlayUtils {
	private static int REGULAR_PERIOD_MINUTES = 12;
	private static int OVERTIME_PERIOD_MIUTES = 5;

	/* Prevent instantiation */
	protected PlayUtils() {
	}

	static public LocalTime getTotalElapsedTime(int period,
			LocalTime elapsedTime) {
		int priorMillis = 0;
		if (period <= 4) {
			priorMillis = (period - 1) * REGULAR_PERIOD_MINUTES * 1000;
		} else {
			priorMillis = ((4 * REGULAR_PERIOD_MINUTES) + ((period - 5) * OVERTIME_PERIOD_MIUTES)) * 1000;
		}
		return elapsedTime.plusMillis(priorMillis);
	}
}
