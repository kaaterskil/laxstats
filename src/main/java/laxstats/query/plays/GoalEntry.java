package laxstats.query.plays;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.plays.PlayKey;

@Entity
@DiscriminatorValue("GOAL")
public class GoalEntry extends PlayEntry {

	public GoalEntry() {
		playKey = PlayKey.GOAL;
	}
}
