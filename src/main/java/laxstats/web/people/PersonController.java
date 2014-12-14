package laxstats.web.people;

import javax.validation.Valid;

import laxstats.api.people.*;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import laxstats.web.ApplicationController;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/people")
public class PersonController extends ApplicationController {
	private final PersonQueryRepository personRepository;

	@Autowired
	public PersonController(PersonQueryRepository personRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.personRepository = personRepository;
	}

	//---------- Person actions ----------//

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", personRepository.findAll());
		return "people/index";
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	public String showPerson(@PathVariable String personId, Model model) {
		PersonEntry person = personRepository.findOne(personId);
		model.addAttribute("person", person);
		return "people/showPerson";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newPerson(Model model) {
		final PersonForm form = new PersonForm();
		model.addAttribute("form", form);
		return "people/newPerson";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createPerson(@ModelAttribute("form") @Valid PersonForm form,
														 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/newPerson";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonId identifier = new PersonId();

		final PersonDTO dto = new PersonDTO(identifier, form.getPrefix(),
				form.getFirstName(), form.getMiddleName(), form.getLastName(),
				form.getSuffix(), form.getNickname(),
				PersonForm.fullName(form), form.getGender(),
				form.getDominantHand(), false, null, null, form.getBirthdate(),
				null, null, null, user.getId(), now, user.getId(), now);

		final CreatePersonCommand command = new CreatePersonCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/people";
	}

	@RequestMapping(value = "/{personId}/edit", method = RequestMethod.GET)
	public String editPerson(@PathVariable String personId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);

		final PersonForm form = new PersonForm();
		form.setBirthdate(person.getBirthdate());
		form.setDominantHand(person.getDominantHand());
		form.setFirstName(person.getFirstName());
		form.setGender(person.getGender());
		form.setLastName(person.getLastName());
		form.setMiddleName(person.getMiddleName());
		form.setNickname(person.getNickname());
		form.setPrefix(person.getPrefix());
		form.setSuffix(person.getSuffix());

		model.addAttribute("form", form);
		return "people/editPerson";
	}

	//---------- Address actions ----------//

	@RequestMapping(value = "/{personId}/addresses/{addressId}", method = RequestMethod.GET)
	public String showAddress(@PathVariable String personId,
							  @PathVariable String addressId,
							  Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final AddressEntry address = person.getAddresses().get(addressId);
		model.addAttribute("personId", personId);
		model.addAttribute("address", address);
		return "people/showAddress";
	}

	@RequestMapping(value = "/{personId}/addresses/new", method = RequestMethod.GET)
	public String newAddress(@PathVariable String personId, Model model) {
		final AddressForm form = new AddressForm();
		model.addAttribute("personId", personId);
		model.addAttribute("form", form);
		return "people/newAddress";
	}

	@RequestMapping(value = "/{personId}/addresses", method = RequestMethod.POST)
	public String createAddress(@PathVariable String personId,
								@ModelAttribute("form") @Valid AddressForm form,
								BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/newAddress";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		PersonEntry person = personRepository.findOne(personId);
		PersonId identifier = new PersonId(personId);
		String addressId = IdentifierFactory.getInstance().generateIdentifier();

		AddressDTO dto = new AddressDTO();
		dto.setId(addressId);
		dto.setPerson(person);
		dto.setAddress1(form.getAddress1());
		dto.setAddress2(form.getAddress2());
		dto.setCity(form.getCity());
		dto.setRegion(form.getRegion());
		dto.setPostalCode(form.getPostalCode());
		dto.setAddressType(form.getType());
		dto.setPrimary(form.isPrimary());
		dto.setDoNotUse(false);
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		RegisterAddressCommand payload = new RegisterAddressCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/people/showPerson";
	}

	@RequestMapping(value = "/{personId}/addresses/{addressId}/edit", method = RequestMethod.GET)
	public String editAddress(@PathVariable String personId,
							  @PathVariable String addressId,
							  Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final AddressEntry address = person.getAddresses().get(addressId);

		final AddressForm form = new AddressForm();
		form.setAddress1(address.getAddress1());
		form.setAddress2(address.getAddress2());
		form.setCity(address.getCity());
		form.setDoNotUse(address.isDoNotUse());
		form.setPostalCode(address.getPostalCode());
		form.setType(address.getAddressType());
		form.setPrimary(address.isPrimary());
		form.setRegion(address.getRegion());

		model.addAttribute("personId", personId);
		model.addAttribute("addressId", addressId);
		model.addAttribute("form", form);
		return "people/editAddress";
	}

	@RequestMapping(value = "/{personId}/addresses/{addressId}", method = RequestMethod.PUT)
	public String updateAddress(@PathVariable String personId,
								@PathVariable String addressId,
								@ModelAttribute("form") @Valid AddressForm form,
								BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/people/editAddress";
		}
		LocalDateTime now = LocalDateTime.now();
		UserEntry user = getCurrentUser();
		PersonEntry person = personRepository.findOne(personId);
		PersonId identifier = new PersonId(personId);

		AddressDTO dto = new AddressDTO();
		dto.setId(addressId);
		dto.setPerson(person);
		dto.setAddress1(form.getAddress1());
		dto.setAddress2(form.getAddress2());
		dto.setCity(form.getCity());
		dto.setRegion(form.getRegion());
		dto.setPostalCode(form.getPostalCode());
		dto.setAddressType(form.getType());
		dto.setPrimary(form.isPrimary());
		dto.setDoNotUse(form.isDoNotUse());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		UpdateAddressCommand payload = new UpdateAddressCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/people/showPerson";
	}

	//---------- Contact actions ----------//

	@RequestMapping(value = "/{personId}/contacts/{contactId}", method = RequestMethod.GET)
	public String showContact(@PathVariable String personId,
							  @PathVariable String contactId,
							  Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final ContactEntry contact = person.getContacts().get(contactId);
		model.addAttribute("personId", personId);
		model.addAttribute("contact", contact);
		return "people/showContact";
	}

	@RequestMapping(value = "/{personId}/contacts/new", method = RequestMethod.GET)
	public String newContact(@PathVariable String personId, Model model) {
		final ContactForm form = new ContactForm();
		model.addAttribute("personId", personId);
		model.addAttribute("form", form);
		return "people/newContact";
	}

	@RequestMapping(value = "/{personId}/contacts", method = RequestMethod.POST)
	public String createContact(@PathVariable String personId,
								@ModelAttribute("form") @Valid ContactForm form,
								BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/newContact";
		}
		LocalDateTime now = LocalDateTime.now();
		UserEntry user = getCurrentUser();
		PersonEntry person = personRepository.findOne(personId);
		PersonId identifier = new PersonId(personId);
		String contactId = IdentifierFactory.getInstance().generateIdentifier();

		ContactDTO dto = new ContactDTO();
		dto.setId(contactId);
		dto.setPerson(person);
		dto.setMethod(form.getMethod());
		dto.setValue(form.getValue());
		dto.setPrimary(form.isPrimary());
		dto.setDoNotUse(false);
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		RegisterContactCommand payload = new RegisterContactCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/people/showPerson";
	}

	@RequestMapping(value = "/{personId}/contacts/{contactId}/edit", method = RequestMethod.GET)
	public String editContact(@PathVariable String personId,
							  @PathVariable String contactId,
							  Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final ContactEntry contact = person.getContacts().get(contactId);

		final ContactForm form = new ContactForm();
		form.setMethod(contact.getMethod());
		form.setValue(contact.getValue());
		form.setDoNotUse(contact.isDoNotUse());
		form.setPrimary(contact.isPrimary());

		model.addAttribute("personId", personId);
		model.addAttribute("contactId", contactId);
		model.addAttribute("form", form);
		return "people/editContact";
	}

	@RequestMapping(value = "/{personId}/contacts/{contactId}", method = RequestMethod.PUT)
	public String updateContact(@PathVariable String personId,
								@PathVariable String contactId,
								@ModelAttribute("form") @Valid ContactForm form,
								BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "/people/editContact";
		}
		LocalDateTime now = LocalDateTime.now();
		UserEntry user = getCurrentUser();
		PersonId identifier = new PersonId(personId);

		ContactDTO dto = new ContactDTO();
		dto.setId(contactId);
		dto.setMethod(form.getMethod());
		dto.setValue(form.getValue());
		dto.setPrimary(form.isPrimary());
		dto.setDoNotUse(form.isDoNotUse());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		UpdateContactCommand payload = new UpdateContactCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect/people/showPerson";
	}
}
