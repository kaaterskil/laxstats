package laxstats.api.people;


public class ContactChangedEvent {
	private final PersonId personId;
	private final ContactDTO contactDTO;

	public ContactChangedEvent(PersonId personId, ContactDTO contactDTO) {
		this.personId = personId;
		this.contactDTO = contactDTO;
	}

	public ContactDTO getContact() {
		return contactDTO;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
