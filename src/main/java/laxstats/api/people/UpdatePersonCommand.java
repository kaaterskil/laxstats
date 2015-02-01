package laxstats.api.people;

public class UpdatePersonCommand extends AbstractPersonCommand {
	private final PersonDTO personDTO;

	public UpdatePersonCommand(PersonId personId, PersonDTO personDTO) {
		super(personId);
		this.personDTO = personDTO;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}

}
