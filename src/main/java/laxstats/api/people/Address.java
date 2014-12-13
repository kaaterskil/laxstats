package laxstats.api.people;

import laxstats.api.Region;

import org.joda.time.LocalDateTime;

public class Address {
	private final String id;
	private final String personId;
	private final String siteId;
	private final AddressType addressType;
	private final String address1;
	private final String address2;
	private final String city;
	private final Region region;
	private final String postalCode;
	private final boolean isPrimary;
	private final boolean doNotUse;
	private final String createdBy;
	private final LocalDateTime createdAt;
	private final String modifiedBy;
	private final LocalDateTime modifiedAt;

	public Address(String id, String personId, String siteId,
			AddressType addressType, String address1, String address2,
			String city, Region region, String postalCode, boolean isPrimary,
			boolean doNotUse, String createdBy, LocalDateTime createdAt,
			String modifiedBy, LocalDateTime modifiedAt) {
		this.id = id;
		this.personId = personId;
		this.siteId = siteId;
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

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public String getCity() {
		return city;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getPersonId() {
		return personId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public Region getRegion() {
		return region;
	}

	public String getSiteId() {
		return siteId;
	}

	public boolean isDoNotUse() {
		return doNotUse;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

}
