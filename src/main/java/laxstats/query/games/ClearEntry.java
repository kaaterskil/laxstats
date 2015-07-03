package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code ClearEntry} represents a query object model of a clear, a play designed to move the ball
 * from the defensive end to the offensive end after a save or turnover.
 */
@Entity
@DiscriminatorValue(PlayType.CLEAR)
public class ClearEntry extends PlayEntry {
   private static final long serialVersionUID = -1067151560295967085L;

   /**
    * Creates a {@code ClearEntry}.
    */
   public ClearEntry() {
      playKey = PlayKey.PLAY;
   }
}
