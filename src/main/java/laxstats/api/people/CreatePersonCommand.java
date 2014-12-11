package laxstats.api.people;

public class CreatePersonCommand extends AbstractPersonCommand {

	private PersonDTO PersonDTO;

	public PersonDTO getPersonDTO() {
		return PersonDTO;
	}

	public void setPersonDTO(PersonDTO PersonDTO) {
		this.PersonDTO = PersonDTO;
	}

}
