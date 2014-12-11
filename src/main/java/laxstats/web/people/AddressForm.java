package laxstats.web.people;

import javax.validation.constraints.NotNull;

import laxstats.api.people.AddressType;
import laxstats.domain.Region;

public class AddressForm {

	@NotNull
	private AddressType type;
	private String address1;
	private String address2;

	@NotNull
	private String city;
	private Region region;
	private String postalCode;
	private boolean isPrimary;
	private boolean doNotUse;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public boolean isDoNotUse() {
		return doNotUse;
	}

	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

}
