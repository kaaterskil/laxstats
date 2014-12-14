package laxstats.api.people;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public abstract class AbstractPersonCommand {

	@TargetAggregateIdentifier
	private final PersonId personId;

	protected AbstractPersonCommand(PersonId personId) {
		this.personId = personId;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
