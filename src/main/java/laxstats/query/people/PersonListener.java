package laxstats.query.people;

import laxstats.api.people.PersonCreatedEvent;
import laxstats.api.people.PersonDTO;

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
	protected final void handle(PersonCreatedEvent event) {
		PersonDTO dto = event.getPersonDTO();
		
		Person person = new Person();
		person.setId(event.getPersonId().toString());
		
		person.setBirthdate(dto.getBirthdate());
		person.setCollege(dto.getCollege());
		person.setCollegeUrl(dto.getCollegeUrl());
		person.setCreatedAt(dto.getCreatedAt());
		person.setCreatedBy(dto.getCreatedBy());
		person.setDominantHand(dto.getDominantHand());
		person.setFirstName(dto.getFirstName());
		person.setFullName(dto.getFullName());
		person.setGender(dto.getGender());
		person.setLastName(dto.getLastName());
		person.setMiddleName(dto.getMiddleName());
		person.setModifiedAt(dto.getModifiedAt());
		person.setModifiedBy(dto.getModifiedBy());
		person.setNickname(dto.getNickname());
		person.setParentReleased(dto.isParentReleased());
		person.setParentReleaseReceivedOn(dto.getParentReleaseReceivedOn());
		person.setParentReleaseSentOn(dto.getParentReleaseSentOn());
		person.setPhoto(dto.getPhoto());
		person.setPrefix(dto.getPrefix());
		person.setSuffix(dto.getSuffix());
		
		personRepository.save(person);
	}
}
