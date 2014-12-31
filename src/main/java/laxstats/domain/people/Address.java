package laxstats.domain.people;

import laxstats.api.Region;
import laxstats.api.people.AddressChangedEvent;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressType;
import laxstats.api.people.Contactable;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

public class Address extends AbstractAnnotatedEntity implements Contactable {
	private String id;
	private String personId;
	private String siteId;
	private AddressType addressType;
	private String address1;
	private String address2;
	private String city;
	private Region region;
	private String postalCode;
	private boolean isPrimary;
	private boolean doNotUse;

	/*---------- Methods ----------*/

	public String getAddress() {
		final StringBuilder sb = new StringBuilder();
		boolean concat = false;
		if (address1 != null) {
			sb.append(address1);
			concat = true;
		}
		if (address2 != null) {
			if (concat) {
				sb.append(", ");
			}
			sb.append(address2);
			concat = true;
		}
		if (city != null) {
			if (concat) {
				sb.append(", ");
			}
			sb.append(city);
			concat = true;
		}
		if (region != null) {
			if (concat) {
				sb.append(" ");
			}
			sb.append(region.getAbbreviation());
		}
		if (postalCode != null) {
			sb.append(" ").append(postalCode);
		}
		return sb.toString();
	}

	/*---------- Event handlers ----------*/

	@EventHandler
	protected void handle(AddressChangedEvent event) {
		if (!this.id.equals(event.getAddressDTO().getId())) {
			return;
		}

		final AddressDTO dto = event.getAddressDTO();
		addressType = dto.getAddressType();
		address1 = dto.getAddress1();
		address2 = dto.getAddress2();
		city = dto.getCity();
		region = dto.getRegion();
		postalCode = dto.getPostalCode();
		isPrimary = dto.isPrimary();
		doNotUse = dto.isDoNotUse();
	}

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

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
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

	@Override
	public boolean isPrimary() {
		return isPrimary;
	}

	@Override
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	@Override
	public boolean isDoNotUse() {
		return doNotUse;
	}

	@Override
	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}
}
