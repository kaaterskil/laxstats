package laxstats.api.people;

public class DeleteContactCommand extends AbstractPersonCommand {
	private final String contactId;

	public DeleteContactCommand(PersonId personId, String contactId) {
		super(personId);
		this.contactId = contactId;
	}

	public String getContactId() {
		return contactId;
	}
}
