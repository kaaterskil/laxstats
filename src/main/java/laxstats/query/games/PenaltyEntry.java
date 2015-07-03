package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayRole;
import laxstats.api.games.PlayType;
import laxstats.api.games.PlayUtils;

import org.joda.time.Interval;

/**
 * {@code PenaltyEntry} represents the query object model of a penalty, or the side-lining of a
 * player caused by the commission of a violation of playing rules.
 */
@Entity
@DiscriminatorValue(PlayType.PENALTY)
public class PenaltyEntry extends PlayEntry {
   private static final long serialVersionUID = -8712295107965966601L;

   /**
    * Creates a {@code PenaltyEntry}.
    */
   public PenaltyEntry() {
      playKey = PlayKey.PLAY;
   }

   /**
    * Returns the time interval that a player may be sidelined due to the imposition of the penalty
    * with the given duration. The time interval represents a period of time between two instants
    * and, as such, is independent of and can overlap more than one play period.
    *
    * @return
    */
   public Interval getInterval() {
      return PlayUtils.getPenaltyInterval(event.getStartsAt(), period, elapsedTime, duration);
   }

   /**
    * Returns the play participant who committed the violation.
    *
    * @return
    */
   public PlayParticipantEntry getCommittedBy() {
      PlayParticipantEntry result = null;
      for (final PlayParticipantEntry player : participants) {
         if (player.getRole().equals(PlayRole.PENALTY_COMMITTED_BY)) {
            result = player;
         }
      }
      return result;
   }

   /**
    * Returns the play participant against whom the violation was committed, or null.
    *
    * @return
    */
   public PlayParticipantEntry getCommittedAgainst() {
      PlayParticipantEntry result = null;
      for (final PlayParticipantEntry player : participants) {
         if (player.getRole().equals(PlayRole.PENALTY_COMMITTED_AGAINST)) {
            result = player;
         }
      }
      return result;
   }
}
