package laxstats.api.people;


public class PersonCreatedEvent {
	private final PersonId personId;
	private final PersonDTO personDTO;
	
	public PersonCreatedEvent(PersonId personId, PersonDTO personDTO) {
		this.personId = personId;
		this.personDTO = personDTO;
	}

	public PersonId getPersonId() {
		return personId;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}
	
}
