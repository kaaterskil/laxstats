package laxstats.domain.people;

import laxstats.api.people.Address;
import laxstats.api.people.Contact;
import laxstats.api.people.CreatePersonCommand;
import laxstats.api.people.PersonId;
import laxstats.api.people.RegisterAddressCommand;
import laxstats.api.people.RegisterContactCommand;
import laxstats.query.people.PersonQueryRepository;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PersonCommandHandler {
	private final static Logger logger = LoggerFactory
			.getLogger(PersonCommandHandler.class);

	private Repository<Person> repository;

	@SuppressWarnings("unused")
	private PersonQueryRepository personQueryRepository;

	@CommandHandler
	public PersonId handle(final CreatePersonCommand command) {
		logger.debug("Command received to create a new contact with id: {}",
				command.getPersonId().toString());

		final PersonId identifier = command.getPersonId();
		final Person person = new Person(identifier, command.getPersonDTO());
		repository.add(person);
		return identifier;
	}

	@CommandHandler
	public void handle(RegisterAddressCommand command) {
		logger.debug("Command received to register an address with id {}",
				command.getId());

		final Address address = new Address(command.getId(), command
				.getPersonId().toString(), null, command.getAddressType(),
				command.getAddress1(), command.getAddress2(),
				command.getCity(), command.getRegion(),
				command.getPostalCode(), command.isPrimary(),
				command.isDoNotUse(), command.getCreatedBy(),
				command.getCreatedAt(), command.getModifiedBy(),
				command.getModifiedAt());
		final Person person = repository.load(command.getPersonId());
		person.registerAddress(address);
	}

	@CommandHandler
	public void handle(RegisterContactCommand command) {
		logger.debug("Command received to register a contact with id {}",
				command.getId());

		final Contact contact = new Contact(command.getId(), command
				.getPersonId().toString(), command.getMethod(),
				command.getValue(), command.isPrimary(), command.isDoNotUse(),
				command.getCreatedAt(), command.getCreatedBy(),
				command.getModifiedAt(), command.getModifiedBy());
		final Person person = repository.load(command.getPersonId());
		person.registerContact(contact);
	}

	@Autowired
	public void setPersonQueryRepository(
			PersonQueryRepository personQueryRepository) {
		this.personQueryRepository = personQueryRepository;
	}

	@Autowired
	@Qualifier("personRepository")
	public void setRepository(Repository<Person> repository) {
		this.repository = repository;
	}
}
