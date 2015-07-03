package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code FaceOffEntry} represents the query object model of a face-off. A face-off takes place at
 * the start of each quarter, after every goal, and after certain dead balls. Two opposing players
 * crouch down at midfield, hold their sticks flat on the ground and press the backs of their stick
 * pockets together. The ball is then placed between the pockets and, when signaled to start, the
 * players “rake” or clamp on the ball to vie for control.
 */
@Entity
@DiscriminatorValue(PlayType.FACEOFF)
public class FaceOffEntry extends PlayEntry {
   private static final long serialVersionUID = 2039846177366422767L;

   /**
    * Creates a {@code FaceOffEntry}.
    */
   public FaceOffEntry() {
      playKey = PlayKey.PLAY;
   }
}
