package laxstats.api.people;

public class AddressAddedEvent {

	private final PersonId personId;
	private final AddressDTO address;

	public AddressAddedEvent(PersonId personId, AddressDTO address) {
		this.personId = personId;
		this.address = address;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
