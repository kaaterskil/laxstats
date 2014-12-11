package laxstats.web.people;

import javax.validation.Valid;

import laxstats.api.people.CreatePersonCommand;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.api.people.RegisterAddressCommand;
import laxstats.api.people.RegisterContactCommand;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/people")
public class PersonController {
	private final PersonQueryRepository personRepository;
	private final UserQueryRepository userRepository;
	private final CommandBus commandBus;

	@Autowired
	public PersonController(PersonQueryRepository personRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		this.personRepository = personRepository;
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	@RequestMapping(value = "/{personId}/addresses", method = RequestMethod.POST)
	public String createAddress(@PathVariable String personId,
			@ModelAttribute("form") @Valid AddressForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "people/newAddress";
		}
		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final UserEntry user = userRepository.findByEmail(principal.toString());
		final String addressId = IdentifierFactory.getInstance()
				.generateIdentifier().toString();

		final RegisterAddressCommand command = new RegisterAddressCommand();
		command.setPersonId(new PersonId(personId));
		command.setAddress1(form.getAddress1());
		command.setAddress2(form.getAddress2());
		command.setAddressType(form.getType());
		command.setCity(form.getCity());
		command.setCreatedAt(now);
		command.setCreatedBy(user.getId());
		command.setDoNotUse(false);
		command.setId(addressId);
		command.setModifiedAt(now);
		command.setModifiedBy(user.getId());
		command.setPostalCode(form.getPostalCode());
		command.setRegion(form.getRegion());

		commandBus.dispatch(new GenericCommandMessage<RegisterAddressCommand>(
				command));
		return "redirect:/people/show";
	}

	@RequestMapping(value = "/{personId}/contacts", method = RequestMethod.POST)
	public String createContact(@PathVariable String personId,
			@ModelAttribute("form") @Valid ContactForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "people/newContact";
		}
		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final UserEntry user = userRepository.findByEmail(principal.toString());
		final String contactId = IdentifierFactory.getInstance()
				.generateIdentifier().toString();

		final RegisterContactCommand command = new RegisterContactCommand();
		command.setPersonId(new PersonId(personId));
		command.setCreatedAt(now);
		command.setCreatedBy(user.getId());
		command.setDoNotUse(false);
		command.setId(contactId);
		command.setMethod(form.getMethod());
		command.setModifiedAt(now);
		command.setModifiedBy(user.getId());
		command.setPrimary(form.isPrimary());
		command.setValue(form.getValue());

		commandBus.dispatch(new GenericCommandMessage<RegisterContactCommand>(
				command));
		return "redirect:/people/show";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createPerson(@PathVariable String personId,
			@ModelAttribute("form") @Valid PersonForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "people/new";
		}
		final LocalDateTime now = LocalDateTime.now();
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		final UserEntry user = userRepository.findByEmail(principal.toString());
		final PersonId identifier = new PersonId();

		final PersonDTO dto = new PersonDTO(identifier, form.getPrefix(),
				form.getFirstName(), form.getMiddleName(), form.getLastName(),
				form.getSuffix(), form.getNickname(),
				PersonForm.fullName(form), form.getGender(),
				form.getDominantHand(), false, null, null, form.getBirthdate(),
				null, null, null, user.getId(), now, user.getId(), now);

		final CreatePersonCommand command = new CreatePersonCommand();
		command.setPersonId(identifier);
		command.setPersonDTO(dto);

		commandBus.dispatch(new GenericCommandMessage<CreatePersonCommand>(
				command));
		return "redirect:/people";
	}

	@RequestMapping(value = "/{personId}/addresses/{addressId}/edit", method = RequestMethod.GET)
	public String editAddress(@PathVariable String personId,
			@PathVariable String addressId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final AddressEntry address = person.getAddresses().get(addressId);

		final AddressForm form = new AddressForm();
		form.setAddress1(address.getAddress1());
		form.setAddress2(address.getAddress2());
		form.setType(address.getAddressType());
		form.setCity(address.getCity());
		form.setDoNotUse(address.isDoNotUse());
		form.setPostalCode(address.getPostalCode());
		form.setPrimary(address.isPrimary());
		form.setRegion(address.getRegion());

		model.addAttribute("form", form);
		return "people/editAddress";
	}

	@RequestMapping(value = "/{personId}/contacts/{contactId}/edit", method = RequestMethod.GET)
	public String editContact(@PathVariable String personId,
			@PathVariable String contactId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final ContactEntry contact = person.getContacts().get(contactId);

		final ContactForm form = new ContactForm();
		form.setDoNotUse(contact.isDoNotUse());
		form.setPrimary(contact.isPrimary());
		form.setMethod(contact.getMethod());
		form.setValue(contact.getValue());

		model.addAttribute("form", form);
		return "people/editContact";
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

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", personRepository.findAll());
		return "people/index";
	}

	@RequestMapping(value = "/{personId}/addresses/new", method = RequestMethod.GET)
	public String newAddress(@PathVariable String personId, Model model) {
		final AddressForm form = new AddressForm();
		model.addAttribute("form", form);
		return "people/newAddress";
	}

	@RequestMapping(value = "/{personId}/contacts/new", method = RequestMethod.GET)
	public String newContact(@PathVariable String personId, Model model) {
		final ContactForm form = new ContactForm();
		model.addAttribute("form", form);
		return "people/newContact";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newPerson(Model model) {
		final PersonForm form = new PersonForm();
		model.addAttribute("form", form);
		return "people/new";
	}

	@RequestMapping(value = "/{personId}/addresses/{addressId}", method = RequestMethod.GET)
	public String showAddress(@PathVariable String personId,
			@PathVariable String addressId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final AddressEntry address = person.getAddresses().get(addressId);
		model.addAttribute("address", address);
		return "people/showAddress";
	}

	@RequestMapping(value = "/{personId}/contacts/{contactId}", method = RequestMethod.GET)
	public String showContact(@PathVariable String personId,
			@PathVariable String contactId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		final ContactEntry contact = person.getContacts().get(contactId);
		model.addAttribute("contact", contact);
		return "people/showContact";
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	public String showPerson(@PathVariable String personId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		model.addAttribute("person", person);
		return "people/show";
	}
}
