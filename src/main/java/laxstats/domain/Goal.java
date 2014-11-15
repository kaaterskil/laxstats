package laxstats.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("GOAL")
public class Goal extends Play {
	
	public Goal() {
		playKey = PlayKey.GOAL;
	}
}
