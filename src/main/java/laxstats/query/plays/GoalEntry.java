package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.GOAL)
public class GoalEntry extends PlayEntry {

	public GoalEntry() {
		playKey = PlayKey.GOAL;
	}
}
