package laxstats.domain.people;

import laxstats.api.people.CreatePersonCommand;
import laxstats.api.people.DeleteAddressCommand;
import laxstats.api.people.DeleteContactCommand;
import laxstats.api.people.DeletePersonCommand;
import laxstats.api.people.PersonId;
import laxstats.api.people.RegisterAddressCommand;
import laxstats.api.people.RegisterContactCommand;
import laxstats.api.people.UpdateAddressCommand;
import laxstats.api.people.UpdateContactCommand;
import laxstats.api.people.UpdatePersonCommand;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PersonCommandHandler {
	private Repository<Person> repository;

	@Autowired
	@Qualifier("personRepository")
	public void setRepository(Repository<Person> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public PersonId handle(final CreatePersonCommand command) {
		final PersonId identifier = command.getPersonId();
		final Person aggregate = new Person(identifier, command.getPersonDTO());
		repository.add(aggregate);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdatePersonCommand command) {
		final PersonId identifier = command.getPersonId();
		final Person aggregate = repository.load(identifier);
		aggregate.update(identifier, command.getPersonDTO());
	}

	@CommandHandler
	public void handle(DeletePersonCommand command) {
		final PersonId identifier = command.getPersonId();
		final Person aggregate = repository.load(identifier);
		aggregate.delete(identifier);
	}

	/*---------- Addresses ----------*/

	@CommandHandler
	public void handle(RegisterAddressCommand command) {
		final Person person = repository.load(command.getPersonId());
		person.registerAddress(command.getAddressDTO(), false);
	}

	@CommandHandler
	public void handle(UpdateAddressCommand command) {
		final Person person = repository.load(command.getPersonId());
		person.updateAddress(command.getAddressDTO());
	}

	@CommandHandler
	public void handle(DeleteAddressCommand command) {
		final Person person = repository.load(command.getPersonId());
		person.deleteAddress(command.getAddressId());
	}

	/*---------- Contacts ----------*/

	@CommandHandler
	public void handle(RegisterContactCommand command) {
		final Person person = repository.load(command.getPersonId());
		person.registerContact(command.getContactDTO(), false);
	}

	@CommandHandler
	public void handle(UpdateContactCommand command) {
		final Person person = repository.load(command.getPersonId());
		person.updateContact(command.getContactDTO());
	}

	@CommandHandler
	public void handle(DeleteContactCommand command) {
		final Person person = repository.load(command.getPersonId());
		person.deleteContact(command.getContactId());
	}
}
