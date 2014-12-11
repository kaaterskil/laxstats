package laxstats.api.people;

import laxstats.domain.Region;

import org.joda.time.LocalDateTime;

public class RegisterAddressCommand extends AbstractPersonCommand {
	private String id;
	private String siteId;
	private AddressType addressType;
	private String address1;
	private String address2;
	private String city;
	private Region region;
	private String postalCode;
	private boolean isPrimary;
	private boolean doNotUse;
	private String createdBy;
	private LocalDateTime createdAt;
	private String modifiedBy;
	private LocalDateTime modifiedAt;

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

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
