package laxstats.api.people;


public class ContactAddedEvent {

	private final PersonId personId;
	private final Contact contact;

	public ContactAddedEvent(PersonId personId, Contact contact) {
		this.personId = personId;
		this.contact = contact;
	}

	public Contact getContact() {
		return contact;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
