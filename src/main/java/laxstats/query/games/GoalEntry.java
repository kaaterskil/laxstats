package laxstats.query.games;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayType;

/**
 * {@code GoalEntry} represents the query object model of a goal.
 */
@Entity
@DiscriminatorValue(PlayType.GOAL)
public class GoalEntry extends PlayEntry {
   private static final long serialVersionUID = -5067568871399121267L;

   /**
    * Creates a {@code GoalEntry}.
    */
   public GoalEntry() {
      playKey = PlayKey.GOAL;
   }
}
