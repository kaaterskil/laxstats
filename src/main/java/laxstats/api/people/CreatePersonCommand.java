package laxstats.api.people;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreatePersonCommand {
	
	@TargetAggregateIdentifier
	private final PersonId personId;
	private final PersonDTO personDTO;

	public CreatePersonCommand(PersonId personId, PersonDTO personDTO) {
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
