package laxstats.domain.events;

import laxstats.domain.people.Address;

import org.joda.time.LocalDateTime;

public class Site {

	public enum Style {
		COMPETITION, PRACTICE;
	}

	public enum Surface {
		GRASS, TURF;
	}

	private String id;
	private String name;
	private Site.Style style;
	private Site.Surface surface;
	private Address address;
	private String directions;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
