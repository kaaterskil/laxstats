package laxstats.web.people;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import laxstats.api.Region;

public class SearchPeopleForm implements Serializable {
	private static final long serialVersionUID = 3639107587775616095L;

	private String firstName;
	private String lastName;
	private String city;
	private String region;
	private String postalCode;
	private String contact;
	private List<Region> regions;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public List<Region> getRegions() {
		if (regions == null) {
			regions = Arrays.asList(Region.values());
		}
		return regions;
	}
}
