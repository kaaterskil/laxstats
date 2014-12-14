package laxstats.api.people;

public class CreatePersonCommand extends AbstractPersonCommand {

	private final PersonDTO personDTO;

	public CreatePersonCommand(PersonId personId, PersonDTO personDTO) {
		super(personId);
		this.personDTO = personDTO;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}
}
