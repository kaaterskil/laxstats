package laxstats.api.games;

import laxstats.api.utils.Constants;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

/**
 * {@code PlayUtils} provides class methods for common processing of play data.
 */
public class PlayUtils {

   /**
    * Given a period of time with respect to the given play period, returns a period of time from
    * the start of the game to the same ending moment.
    *
    * @param period The current play period.
    * @param elapsedTime The period of time with respect to the current play period.
    * @return
    */
   public static Period getTotalElapsedTime(int period, Period elapsedTime) {
      Minutes priorMinutes = Minutes.ZERO;
      if (period <= 4) {
         priorMinutes = Constants.REGULAR_PERIOD_MINUTES.multipliedBy(period - 1);
      }
      else {
         priorMinutes =
            Constants.REGULAR_PERIOD_MINUTES.multipliedBy(4).plus(
               Constants.OVERTIME_PERIOD_MINUTES.multipliedBy(period - 4));
      }
      return elapsedTime.plus(priorMinutes);
   }

   /**
    * Returns an instant of time from the given information.
    *
    * @param startsAt The start of the game.
    * @param period The current play period.
    * @param elapsedTime The moment in time with respect to the start of the game.
    * @return
    */
   public static DateTime getInstant(LocalDateTime startsAt, int period, Period elapsedTime) {
      final Period totalElapsedTime = PlayUtils.getTotalElapsedTime(period, elapsedTime);
      return startsAt.toDateTime().plus(totalElapsedTime);
   }

   /**
    * Returns the time interval that a player may be sidelined due to the imposition of the penalty
    * with the given duration. The time interval represents a period of time between two instants
    * and, as such, is independent of and can overlap more than one play period.
    *
    * @param startsAt The start of the game.
    * @param period The period in which the violation occurred.
    * @param elapsedTime The time the violation occurred with respect to the start of the game.
    * @param duration The duration of the penalty, in seconds.
    * @return
    */
   public static Interval getPenaltyInterval(LocalDateTime startsAt, int period, Period elapsedTime,
      Period duration)
   {
      final Period totalElapsedTime = PlayUtils.getTotalElapsedTime(period, elapsedTime);

      final DateTime start = startsAt.toDateTime().plus(totalElapsedTime);
      final DateTime end = start.plus(duration);
      return new Interval(start, end);
   }

   protected PlayUtils() {
   }
}
