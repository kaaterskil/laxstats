package laxstats.api.events;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class PlayUtils {
	private static int REGULAR_PERIOD_MINUTES = 12;
	private static int OVERTIME_PERIOD_MIUTES = 5;

	/* Prevent instantiation */
	protected PlayUtils() {
	}

	public static LocalTime getTotalElapsedTime(int period,
			LocalTime elapsedTime) {
		int priorMillis = 0;
		if (period <= 4) {
			priorMillis = (period - 1) * REGULAR_PERIOD_MINUTES * 1000;
		} else {
			priorMillis = ((4 * REGULAR_PERIOD_MINUTES) + ((period - 5) * OVERTIME_PERIOD_MIUTES)) * 1000;
		}
		return elapsedTime.plusMillis(priorMillis);
	}

	public static DateTime getInstant(LocalDateTime startsAt, int period,
			LocalTime elapsedTime) {
		final LocalTime totalElapsedTime = PlayUtils.getTotalElapsedTime(
				period, elapsedTime);
		final int mins = totalElapsedTime.getMinuteOfHour();
		final int secs = totalElapsedTime.getSecondOfMinute();
		final int millis = ((mins * 60) + secs) * 1000;
		return startsAt.plusMillis(millis).toDateTime();
	}

	public static Interval getPenaltyInterval(LocalDateTime startsAt,
			int period, LocalTime elapsedTime, int duration) {
		final LocalTime totalElapsedTime = PlayUtils.getTotalElapsedTime(
				period, elapsedTime);
		final int mins = totalElapsedTime.getMinuteOfHour();
		final int secs = totalElapsedTime.getSecondOfMinute();
		final int millis = ((mins * 60) + secs) * 1000;

		final DateTime start = startsAt.plusMillis(millis).toDateTime();
		final DateTime end = start.plusMillis(duration * 1000).toDateTime();
		return new Interval(start, end);
	}
}
