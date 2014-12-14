package laxstats.domain.people;

import laxstats.api.people.*;
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
    final Person person = new Person(identifier, command.getPersonDTO());
    repository.add(person);
    return identifier;
  }

  //---------- Addresses ----------//

  @CommandHandler
  public void handle(RegisterAddressCommand command) {
    Person person = repository.load(command.getPersonId());
    person.registerAddress(command.getAddressDTO());
  }

  @CommandHandler
  public void handle(UpdateAddressCommand command) {
    Person person = repository.load(command.getPersonId());
    person.updateAddress(command.getAddressDTO());
  }

  //---------- Contacts ----------//

  @CommandHandler
  public void handle(RegisterContactCommand command) {
    Person person = repository.load(command.getPersonId());
    person.registerContact(command.getContactDTO());
  }

  @CommandHandler
  public void handle(UpdateContactCommand command) {
    Person person = repository.load(command.getPersonId());
    person.updateContact(command.getContactDTO());
  }
}
