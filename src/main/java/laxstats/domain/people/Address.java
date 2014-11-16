package laxstats.domain.people;

import laxstats.domain.Region;

import org.joda.time.LocalDateTime;

public class Address {
	private String id;
	private Person person;
	private String siteId;
	private String address1;
	private String address2;
	private String city;
	private Region region;
	private String postalCode;
	private boolean isPrimary = false;
	private boolean doNotUse = false;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
