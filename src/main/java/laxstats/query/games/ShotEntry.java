package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code ShotEntry} represents the query object model of shot.
 */
@Entity
@DiscriminatorValue(PlayType.SHOT)
public class ShotEntry extends PlayEntry {
   private static final long serialVersionUID = -6788526267404351022L;

   /**
    * Creates a {@code ShotEntry}.
    */
   public ShotEntry() {
      playKey = PlayKey.PLAY;
   }
}
