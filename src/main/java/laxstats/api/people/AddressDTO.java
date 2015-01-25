package laxstats.api.people;

import laxstats.api.Region;
import laxstats.query.people.PersonEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class AddressDTO {
	private final String id;
	private final PersonEntry person;
	private final SiteEntry site;
	private final AddressType addressType;
	private final String address1;
	private final String address2;
	private final String city;
	private final Region region;
	private final String postalCode;
	private final boolean isPrimary;
	private final boolean doNotUse;
	private final UserEntry createdBy;
	private final LocalDateTime createdAt;
	private final UserEntry modifiedBy;
	private final LocalDateTime modifiedAt;

	public AddressDTO(String id, SiteEntry site, PersonEntry person,
			AddressType addressType, String address1, String address2,
			String city, Region region, String postalCode, boolean isPrimary,
			boolean doNotUse, UserEntry createdBy, LocalDateTime createdAt,
			UserEntry modifiedBy, LocalDateTime modifiedAt) {
		this.id = id;
		this.site = site;
		this.person = person;
		this.addressType = addressType;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.region = region;
		this.postalCode = postalCode;
		this.isPrimary = isPrimary;
		this.doNotUse = doNotUse;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.modifiedBy = modifiedBy;
		this.modifiedAt = modifiedAt;
	}

	public AddressDTO(String id, SiteEntry site, PersonEntry person,
			AddressType addressType, String address1, String address2,
			String city, Region region, String postalCode, boolean isPrimary,
			boolean doNotUse, UserEntry modifiedBy, LocalDateTime modifiedAt) {
		this(id, site, person, addressType, address1, address2, city, region,
				postalCode, isPrimary, doNotUse, null, null, modifiedBy,
				modifiedAt);
	}

	public String getId() {
		return id;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public SiteEntry getSite() {
		return site;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}

	public Region getRegion() {
		return region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public boolean isDoNotUse() {
		return doNotUse;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}
}
