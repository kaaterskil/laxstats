package laxstats.api.people;

public class UpdateContactCommand extends AbstractPersonCommand {
    private final ContactDTO contactDTO;

    public UpdateContactCommand(PersonId personId, ContactDTO contactDTO) {
        super(personId);
        this.contactDTO = contactDTO;
    }

    public ContactDTO getContactDTO() {
        return contactDTO;
    }
}
