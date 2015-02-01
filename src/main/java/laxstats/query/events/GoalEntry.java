package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

@Entity
@DiscriminatorValue(PlayType.GOAL)
public class GoalEntry extends PlayEntry {
	private static final long serialVersionUID = -5067568871399121267L;

	public GoalEntry() {
		playKey = PlayKey.GOAL;
	}
}
