package laxstats.query.people;

import laxstats.api.people.*;

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
		PersonDTO dto = event.getPersonDTO();

		PersonEntry person = new PersonEntry();
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

	//---------- Addresses ----------//

	@EventHandler
	protected void handle(AddressAddedEvent event) {
		AddressDTO dto = event.getAddress();
		PersonEntry person = personRepository.findOne(event.getPersonId()
				.toString());

		AddressEntry address = new AddressEntry();
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

		person.addAddress(address);
		personRepository.save(person);
	}

	@EventHandler
	protected void handle(AddressChangedEvent event) {
		PersonId identifier = event.getPersonId();
		AddressDTO dto = event.getAddressDTO();
		PersonEntry person = personRepository.findOne(identifier.toString());

		AddressEntry address = person.getAddresses().get(dto.getId());
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

		personRepository.save(person);
	}

	//---------- Contacts ----------//

	@EventHandler
	protected void handle(ContactAddedEvent event) {
		PersonId identifier = event.getPersonId();
		ContactDTO dto = event.getContact();
		PersonEntry person = personRepository.findOne(identifier.toString());

		ContactEntry contact = new ContactEntry();
		contact.setMethod(dto.getMethod());
		contact.setValue(dto.getValue());
		contact.setPrimary(dto.isPrimary());
		contact.setDoNotUse(dto.isDoNotUse());
		contact.setCreatedAt(dto.getCreatedAt());
		contact.setCreatedBy(dto.getCreatedBy());
		contact.setModifiedAt(dto.getModifiedAt());
		contact.setModifiedBy(dto.getModifiedBy());

		person.addContact(contact);
		personRepository.save(person);
	}

	@EventHandler
	protected void handle(ContactChangedEvent event) {
		PersonId identifier = event.getPersonId();
		ContactDTO dto = event.getContact();
		PersonEntry person = personRepository.findOne(identifier.toString());

		ContactEntry contact = person.getContacts().get(dto.getId());
		contact.setMethod(dto.getMethod());
		contact.setValue(dto.getValue());
		contact.setPrimary(dto.isPrimary());
		contact.setDoNotUse(dto.isDoNotUse());
		contact.setModifiedAt(dto.getModifiedAt());
		contact.setModifiedBy(dto.getModifiedBy());

		personRepository.save(person);
	}
}
