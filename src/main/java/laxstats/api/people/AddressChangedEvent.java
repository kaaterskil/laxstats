package laxstats.api.people;

public class AddressChangedEvent {

	private final PersonId personId;
	private final Address address;

	public AddressChangedEvent(PersonId personId, Address address) {
		this.personId = personId;
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
