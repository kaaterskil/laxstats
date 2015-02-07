package laxstats.api.people;

public class AddressDeletedEvent {
	private final PersonId personId;
	private final String addressId;

	public AddressDeletedEvent(PersonId personId, String addressId) {
		this.personId = personId;
		this.addressId = addressId;
	}

	public String getAddressId() {
		return addressId;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
