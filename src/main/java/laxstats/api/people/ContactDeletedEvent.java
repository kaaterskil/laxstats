package laxstats.api.people;

public class ContactDeletedEvent {
	private final PersonId personId;
	private final String contactId;

	public ContactDeletedEvent(PersonId personId, String contactId) {
		this.personId = personId;
		this.contactId = contactId;
	}

	public String getContactId() {
		return contactId;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
