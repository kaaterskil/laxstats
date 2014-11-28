package laxstats.domain.people;

import laxstats.api.people.CreatePersonCommand;
import laxstats.api.people.PersonId;
import laxstats.query.people.PersonQueryRepository;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PersonCommandHandler {

	private Repository<Person> repository;
	
	@SuppressWarnings("unused")
	private PersonQueryRepository personQueryRepository;

	@Autowired
	@Qualifier("personRepository")
	public void setRepository(Repository<Person> repository) {
		this.repository = repository;
	}

	@Autowired
	public void setPersonQueryRepository(PersonQueryRepository personQueryRepository) {
		this.personQueryRepository = personQueryRepository;
	}
	
	@CommandHandler
	public PersonId handle(CreatePersonCommand command) {
		PersonId identifier = command.getPersonId();
		Person person = new Person(identifier, command.getPersonDTO());
		repository.add(person);
		return identifier;
	}
}
