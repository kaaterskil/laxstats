package laxstats.web.people;

import java.io.Serializable;

public class SearchResult implements Serializable {
	private static final long serialVersionUID = -3611194243027367740L;

	private final String id;
	private final String fullName;
	private String city;
	private String region;
	private String postalCode;
	private String contact;

	public SearchResult(String id, String fullName) {
		this.id = id;
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
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
}