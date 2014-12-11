package laxstats.api.people;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public abstract class AbstractPersonCommand {

	@TargetAggregateIdentifier
	private PersonId personId;

	public PersonId getPersonId() {
		return personId;
	}

	public void setPersonId(PersonId personId) {
		this.personId = personId;
	}

}
