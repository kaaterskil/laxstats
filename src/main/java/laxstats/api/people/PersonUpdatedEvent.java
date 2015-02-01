package laxstats.api.people;

public class PersonUpdatedEvent {

	private final PersonId personId;
	private final PersonDTO personDTO;

	public PersonUpdatedEvent(PersonId personId, PersonDTO personDTO) {
		this.personId = personId;
		this.personDTO = personDTO;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
