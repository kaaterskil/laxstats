package laxstats.web.people;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.people.AddressType;

public class AddressForm {

	private String id;
	private String personId;

	@NotNull
	private AddressType type;

	@Size(max = 34)
	private String address1;

	@Size(max = 34)
	private String address2;

	@NotNull
	@Size(min = 3, max = 30)
	private String city;

	private Region region;

	@Size(max = 10)
	@Pattern(regexp = "^[0-9]{5}([\\s\\-][0-9]{4})?$")
	private String postalCode;

	private boolean primary;
	private boolean doNotUse;
	private List<AddressType> addressTypes;
	private List<Region> regions;

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean isDoNotUse() {
		return doNotUse;
	}

	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	public List<AddressType> getAddressTypes() {
		if (addressTypes == null) {
			addressTypes = Arrays.asList(AddressType.values());
		}
		return addressTypes;
	}

	public List<Region> getRegions() {
		if (regions == null) {
			regions = Arrays.asList(Region.values());
		}
		return regions;
	}

}
