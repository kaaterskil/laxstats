package laxstats.domain.people;

import laxstats.api.people.CreatePerson;
import laxstats.api.people.DeleteAddress;
import laxstats.api.people.DeleteContact;
import laxstats.api.people.DeletePerson;
import laxstats.api.people.PersonId;
import laxstats.api.people.RegisterAddress;
import laxstats.api.people.RegisterContact;
import laxstats.api.people.UpdateAddress;
import laxstats.api.people.UpdateContact;
import laxstats.api.people.UpdatePerson;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code PersonCommandHandler} manages commands for the person aggregate.
 */
@Component
public class PersonCommandHandler {
   private Repository<Person> repository;

   @Autowired
   @Qualifier("personRepository")
   public void setRepository(Repository<Person> repository) {
      this.repository = repository;
   }

   /**
    * Creates and persists a new person.
    *
    * @param command
    * @return
    */
   @CommandHandler
   public PersonId handle(final CreatePerson command) {
      final PersonId identifier = command.getPersonId();
      final Person aggregate = new Person(identifier, command.getPersonDTO());
      repository.add(aggregate);
      return identifier;
   }

   /**
    * Updates and persists changes to an existing person.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdatePerson command) {
      final PersonId identifier = command.getPersonId();
      final Person aggregate = repository.load(identifier);
      aggregate.update(identifier, command.getPersonDTO());
   }

   /**
    * Deletes an existing person.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeletePerson command) {
      final PersonId identifier = command.getPersonId();
      final Person aggregate = repository.load(identifier);
      aggregate.delete(identifier);
   }

   /**
    * Creates and persists a new address.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RegisterAddress command) {
      final Person person = repository.load(command.getPersonId());
      person.registerAddress(command.getAddressDTO(), false);
   }

   /**
    * Updates and persists changes to an existing address.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateAddress command) {
      final Person person = repository.load(command.getPersonId());
      person.updateAddress(command.getAddressDTO());
   }

   /**
    * Deletes an existing address.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteAddress command) {
      final Person person = repository.load(command.getPersonId());
      person.deleteAddress(command.getAddressId());
   }

   /**
    * Creates and persists a new contact.
    *
    * @param command
    */
   @CommandHandler
   public void handle(RegisterContact command) {
      final Person person = repository.load(command.getPersonId());
      person.registerContact(command.getContactDTO(), false);
   }

   /**
    * Updates and persists changes to an existing contact.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateContact command) {
      final Person person = repository.load(command.getPersonId());
      person.updateContact(command.getContactDTO());
   }

   /**
    * Deletes an existing contact.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteContact command) {
      final Person person = repository.load(command.getPersonId());
      person.deleteContact(command.getContactId());
   }
}
