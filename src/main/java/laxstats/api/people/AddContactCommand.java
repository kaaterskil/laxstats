package laxstats.api.people;


public class AddContactCommand extends AbstractPersonCommand {
	private final ContactDTO contactDTO;

	public AddContactCommand(PersonId personId, ContactDTO contactDTO) {
		super(personId);
		this.contactDTO = contactDTO;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}
}
