package laxstats.api.events;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

public class PlayUtils {
	public static final Minutes REGULAR_PERIOD_MINUTES = Minutes.minutes(12);
	public static final Minutes OVERTIME_PERIOD_MINUTES = Minutes.minutes(5);

	protected PlayUtils() {
	}

	public static Period getTotalElapsedTime(int period, Period elapsedTime) {
		Minutes priorMinutes = Minutes.ZERO;
		if (period <= 4) {
			priorMinutes = REGULAR_PERIOD_MINUTES.multipliedBy(period - 1);
		} else {
			priorMinutes = REGULAR_PERIOD_MINUTES.multipliedBy(4).plus(
					OVERTIME_PERIOD_MINUTES.multipliedBy(period - 5));
		}
		return elapsedTime.plus(priorMinutes);
	}

	public static DateTime getInstant(LocalDateTime startsAt, int period,
			Period elapsedTime) {
		final Period totalElapsedTime = PlayUtils.getTotalElapsedTime(period,
				elapsedTime);
		return startsAt.toDateTime().plus(totalElapsedTime);
	}

	public static Interval getPenaltyInterval(LocalDateTime startsAt,
			int period, Period elapsedTime, Period duration) {
		final Period totalElapsedTime = PlayUtils.getTotalElapsedTime(period,
				elapsedTime);

		final DateTime start = startsAt.toDateTime().plus(totalElapsedTime);
		final DateTime end = start.plus(duration);
		return new Interval(start, end);
	}
}
