package laxstats.query.events;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;
import laxstats.api.events.PlayUtils;

import org.joda.time.Interval;

@Entity
@DiscriminatorValue(PlayType.PENALTY)
public class PenaltyEntry extends PlayEntry {

	public PenaltyEntry() {
		playKey = PlayKey.PLAY;
	}

	public Interval getInterval() {
		return PlayUtils.getPenaltyInterval(event.getStartsAt(), period,
				elapsedTime, duration);
	}
}
