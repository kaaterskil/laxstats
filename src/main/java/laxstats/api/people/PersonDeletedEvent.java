package laxstats.api.people;

public class PersonDeletedEvent {

	private final PersonId personId;

	public PersonDeletedEvent(PersonId personId) {
		this.personId = personId;
	}

	public PersonId getPersonId() {
		return personId;
	}

}
