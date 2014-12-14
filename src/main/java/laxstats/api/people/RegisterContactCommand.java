package laxstats.api.people;

public class RegisterContactCommand extends AbstractPersonCommand {
	private final ContactDTO contactDTO;

	public RegisterContactCommand(PersonId personId, ContactDTO contactDTO) {
		super(personId);
		this.contactDTO = contactDTO;
	}

	public ContactDTO getContactDTO() {
		return contactDTO;
	}
}
