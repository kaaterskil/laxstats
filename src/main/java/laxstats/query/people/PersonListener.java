package laxstats.query.people;

import laxstats.api.people.Address;
import laxstats.api.people.AddressAddedEvent;
import laxstats.api.people.AddressChangedEvent;
import laxstats.api.people.Contact;
import laxstats.api.people.ContactAddedEvent;
import laxstats.api.people.ContactChangedEvent;
import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonListener {

	private PersonQueryRepository personRepository;

	@EventHandler
	protected void handle(AddressAddedEvent event) {
		final Address vo = event.getAddress();
		final PersonEntry person = personRepository.findOne(event.getPersonId()
				.toString());

		final AddressEntry entry = new AddressEntry();
		entry.setAddress1(vo.getAddress1());
		entry.setAddress2(vo.getAddress2());
		entry.setAddressType(vo.getAddressType());
		entry.setCity(vo.getCity());
		entry.setCreatedAt(vo.getCreatedAt());
		entry.setCreatedBy(vo.getCreatedBy());
		entry.setDoNotUse(vo.isDoNotUse());
		entry.setModifiedAt(vo.getModifiedAt());
		entry.setModifiedBy(vo.getModifiedBy());
		entry.setPostalCode(vo.getPostalCode());
		entry.setPrimary(vo.isPrimary());
		entry.setRegion(vo.getRegion());

		person.addAddress(entry);
		personRepository.save(person);
	}

	@EventHandler
	protected void handle(AddressChangedEvent event) {
		final Address address = event.getAddress();
		final PersonEntry person = personRepository.findOne(event.getPersonId()
				.toString());

		final AddressEntry entry = person.getAddresses().get(address.getId());
		entry.setAddress1(address.getAddress1());
		entry.setAddress2(address.getAddress2());
		entry.setAddressType(address.getAddressType());
		entry.setCity(address.getCity());
		entry.setDoNotUse(address.isDoNotUse());
		entry.setModifiedAt(address.getModifiedAt());
		entry.setModifiedBy(address.getModifiedBy());
		entry.setPostalCode(address.getPostalCode());
		entry.setPrimary(address.isPrimary());
		entry.setRegion(address.getRegion());

		personRepository.save(person);
	}

	@EventHandler
	protected void handle(ContactAddedEvent event) {
		final Contact vo = event.getContact();
		final PersonEntry person = personRepository.findOne(event.getPersonId()
				.toString());

		final ContactEntry entry = new ContactEntry();
		entry.setCreatedAt(vo.getCreatedAt());
		entry.setCreatedBy(vo.getCreatedBy());
		entry.setDoNotUse(vo.isDoNotUse());
		entry.setMethod(vo.getMethod());
		entry.setModifiedAt(vo.getModifiedAt());
		entry.setModifiedBy(vo.getModifiedBy());
		entry.setPerson(person);
		entry.setPrimary(vo.isPrimary());
		entry.setValue(vo.getValue());

		person.addContact(entry);
		personRepository.save(person);
	}

	@EventHandler
	protected void handle(ContactChangedEvent event) {
		final Contact contact = event.getContact();
		final PersonEntry person = personRepository.findOne(event.getPersonId()
				.toString());

		final ContactEntry entry = person.getContacts().get(contact.getId());
		entry.setDoNotUse(contact.isDoNotUse());
		entry.setMethod(contact.getMethod());
		entry.setModifiedAt(contact.getModifiedAt());
		entry.setModifiedBy(contact.getModifiedBy());
		entry.setPrimary(contact.isPrimary());
		entry.setValue(contact.getValue());

		personRepository.save(person);
	}

	@EventHandler
	protected void handle(final PersonCreatedEvent event) {
		final PersonDTO dto = event.getPersonDTO();

		final PersonEntry person = new PersonEntry();
		person.setId(event.getPersonId().toString());

		person.setBirthdate(dto.getBirthdate());
		person.setCreatedAt(dto.getCreatedAt());
		person.setCreatedBy(dto.getCreatedBy());
		person.setDominantHand(dto.getDominantHand());
		person.setFirstName(dto.getFirstName());
		person.setFullName(dto.fullName());
		person.setGender(dto.getGender());
		person.setLastName(dto.getLastName());
		person.setMiddleName(dto.getMiddleName());
		person.setModifiedAt(dto.getModifiedAt());
		person.setModifiedBy(dto.getModifiedBy());
		person.setNickname(dto.getNickname());
		person.setPrefix(dto.getPrefix());
		person.setSuffix(dto.getSuffix());

		personRepository.save(person);
	}

	@Autowired
	public void setPersonRepository(PersonQueryRepository personRepository) {
		this.personRepository = personRepository;
	}
}
