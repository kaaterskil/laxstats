package laxstats.query.people;

import laxstats.api.people.AddressAddedEvent;
import laxstats.api.people.AddressChangedEvent;
import laxstats.api.people.AddressDTO;
import laxstats.api.people.ContactAddedEvent;
import laxstats.api.people.ContactChangedEvent;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonDeletedEvent;
import laxstats.api.people.PersonId;
import laxstats.api.people.PersonUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonListener {
	private PersonQueryRepository personRepository;

	@Autowired
	public void setPersonRepository(PersonQueryRepository personRepository) {
		this.personRepository = personRepository;
	}

	@EventHandler
	protected void handle(PersonCreatedEvent event) {
		final PersonDTO dto = event.getPersonDTO();

		final PersonEntry aggregate = new PersonEntry();
		aggregate.setId(event.getPersonId().toString());
		aggregate.setPrefix(dto.getPrefix());
		aggregate.setFirstName(dto.getFirstName());
		aggregate.setMiddleName(dto.getMiddleName());
		aggregate.setLastName(dto.getLastName());
		aggregate.setSuffix(dto.getSuffix());
		aggregate.setFullName(dto.fullName());
		aggregate.setNickname(dto.getNickname());
		aggregate.setGender(dto.getGender());
		aggregate.setDominantHand(dto.getDominantHand());
		aggregate.setBirthdate(dto.getBirthdate());
		aggregate.setParentReleased(dto.isParentReleased());
		aggregate.setParentReleaseSentOn(dto.getParentReleaseSentOn());
		aggregate.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
		aggregate.setCollege(dto.getCollege());
		aggregate.setCreatedAt(dto.getCreatedAt());
		aggregate.setCreatedBy(dto.getCreatedBy());
		aggregate.setModifiedAt(dto.getModifiedAt());
		aggregate.setModifiedBy(dto.getModifiedBy());

		personRepository.save(aggregate);
	}

	@EventHandler
	protected void handle(PersonUpdatedEvent event) {
		final String id = event.getPersonId().toString();
		final PersonDTO dto = event.getPersonDTO();

		final PersonEntry aggregate = personRepository.findOne(id);
		aggregate.setPrefix(dto.getPrefix());
		aggregate.setFirstName(dto.getFirstName());
		aggregate.setMiddleName(dto.getMiddleName());
		aggregate.setLastName(dto.getLastName());
		aggregate.setSuffix(dto.getSuffix());
		aggregate.setFullName(dto.fullName());
		aggregate.setNickname(dto.getNickname());
		aggregate.setGender(dto.getGender());
		aggregate.setDominantHand(dto.getDominantHand());
		aggregate.setBirthdate(dto.getBirthdate());
		aggregate.setParentReleased(dto.isParentReleased());
		aggregate.setParentReleaseSentOn(dto.getParentReleaseSentOn());
		aggregate.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
		aggregate.setCollege(dto.getCollege());
		aggregate.setModifiedAt(dto.getModifiedAt());
		aggregate.setModifiedBy(dto.getModifiedBy());
	}

	@EventHandler
	protected void handle(PersonDeletedEvent event) {
		final String id = event.getPersonId().toString();
		personRepository.delete(id);
	}

	// ---------- Addresses ----------//

	@EventHandler
	protected void handle(AddressAddedEvent event) {
		final AddressDTO dto = event.getAddress();
		final PersonEntry aggregate = personRepository.findOne(event
				.getPersonId().toString());

		final AddressEntry address = new AddressEntry();
		address.setAddressType(dto.getAddressType());
		address.setAddress1(dto.getAddress1());
		address.setAddress2(dto.getAddress2());
		address.setCity(dto.getCity());
		address.setRegion(dto.getRegion());
		address.setPostalCode(dto.getPostalCode());
		address.setPrimary(dto.isPrimary());
		address.setDoNotUse(dto.isDoNotUse());
		address.setCreatedAt(dto.getCreatedAt());
		address.setCreatedBy(dto.getCreatedBy());
		address.setModifiedAt(dto.getModifiedAt());
		address.setModifiedBy(dto.getModifiedBy());

		aggregate.addAddress(address);
		personRepository.save(aggregate);
	}

	@EventHandler
	protected void handle(AddressChangedEvent event) {
		final PersonId identifier = event.getPersonId();
		final AddressDTO dto = event.getAddressDTO();
		final PersonEntry aggregate = personRepository.findOne(identifier
				.toString());

		final AddressEntry address = aggregate.getAddresses().get(dto.getId());
		address.setAddressType(dto.getAddressType());
		address.setAddress1(dto.getAddress1());
		address.setAddress2(dto.getAddress2());
		address.setCity(dto.getCity());
		address.setRegion(dto.getRegion());
		address.setPostalCode(dto.getPostalCode());
		address.setPrimary(dto.isPrimary());
		address.setDoNotUse(dto.isDoNotUse());
		address.setModifiedAt(dto.getModifiedAt());
		address.setModifiedBy(dto.getModifiedBy());

		personRepository.save(aggregate);
	}

	// ---------- Contacts ----------//

	@EventHandler
	protected void handle(ContactAddedEvent event) {
		final PersonId identifier = event.getPersonId();
		final ContactDTO dto = event.getContact();
		final PersonEntry aggregate = personRepository.findOne(identifier
				.toString());

		final ContactEntry contact = new ContactEntry();
		contact.setMethod(dto.getMethod());
		contact.setValue(dto.getValue());
		contact.setPrimary(dto.isPrimary());
		contact.setDoNotUse(dto.isDoNotUse());
		contact.setCreatedAt(dto.getCreatedAt());
		contact.setCreatedBy(dto.getCreatedBy());
		contact.setModifiedAt(dto.getModifiedAt());
		contact.setModifiedBy(dto.getModifiedBy());

		aggregate.addContact(contact);
		personRepository.save(aggregate);
	}

	@EventHandler
	protected void handle(ContactChangedEvent event) {
		final PersonId identifier = event.getPersonId();
		final ContactDTO dto = event.getContact();
		final PersonEntry aggregate = personRepository.findOne(identifier
				.toString());

		final ContactEntry contact = aggregate.getContacts().get(dto.getId());
		contact.setMethod(dto.getMethod());
		contact.setValue(dto.getValue());
		contact.setPrimary(dto.isPrimary());
		contact.setDoNotUse(dto.isDoNotUse());
		contact.setModifiedAt(dto.getModifiedAt());
		contact.setModifiedBy(dto.getModifiedBy());

		personRepository.save(aggregate);
	}
}
