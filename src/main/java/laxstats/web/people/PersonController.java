package laxstats.web.people;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.people.AddressDTO;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.CreatePersonCommand;
import laxstats.api.people.DeletePersonCommand;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.api.people.RegisterAddressCommand;
import laxstats.api.people.RegisterContactCommand;
import laxstats.api.people.UpdateAddressCommand;
import laxstats.api.people.UpdateContactCommand;
import laxstats.api.people.UpdatePersonCommand;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.people.PersonSpecifications;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonController extends ApplicationController {
	private final PersonQueryRepository personRepository;

	@Autowired
	public PersonController(PersonQueryRepository personRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.personRepository = personRepository;
	}

	// ---------- Person actions ----------//

	@RequestMapping(value = "/people", method = RequestMethod.GET)
	public String index(Model model) {
		final SearchPeopleForm form = new SearchPeopleForm();
		model.addAttribute("searchForm", form);
		return "people/index";
	}

	@RequestMapping(value = "/people", method = RequestMethod.POST)
	public String createPerson(@Valid PersonForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/newPerson";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonId identifier = new PersonId();

		final PersonDTO dto = new PersonDTO(identifier, form.getPrefix(),
				form.getFirstName(), form.getMiddleName(), form.getLastName(),
				form.getSuffix(), form.getNickname(), form.getFullName(),
				form.getGender(), form.getDominantHand(), form.isReleased(),
				form.getParentReleaseSentOn(),
				form.getParentReleaseReceivedOn(), form.getBirthdate(),
				form.getCollege(), user, now, user, now);

		final CreatePersonCommand command = new CreatePersonCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/people";
	}

	@RequestMapping(value = "/people/{personId}", method = RequestMethod.GET)
	public String showPerson(@PathVariable("personId") PersonEntry person,
			Model model) {
		model.addAttribute("person", person);
		return "people/showPerson";
	}

	@RequestMapping(value = "/people/{personId}", method = RequestMethod.PUT)
	public String updatePerson(@PathVariable String personId,
			@Valid PersonForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "/people/editPerson";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonId identifier = new PersonId(personId);

		final PersonDTO dto = new PersonDTO(identifier, form.getPrefix(),
				form.getFirstName(), form.getMiddleName(), form.getLastName(),
				form.getSuffix(), form.getNickname(), form.getFullName(),
				form.getGender(), form.getDominantHand(), form.isReleased(),
				form.getParentReleaseSentOn(),
				form.getParentReleaseReceivedOn(), form.getBirthdate(),
				form.getCollege(), user, now);

		final UpdatePersonCommand payload = new UpdatePersonCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/people";
	}

	@RequestMapping(value = "people/{personId}", method = RequestMethod.DELETE)
	public String deletePerson(@PathVariable String personId) {
		final PersonId identifier = new PersonId(personId);
		final DeletePersonCommand payload = new DeletePersonCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/people";
	}

	@RequestMapping(value = "/people/new", method = RequestMethod.GET)
	public String newPerson(Model model) {
		final PersonForm form = new PersonForm();
		model.addAttribute("personForm", form);
		return "people/newPerson";
	}

	@RequestMapping(value = "/people/{personId}/edit", method = RequestMethod.GET)
	public String editPerson(@PathVariable("personId") PersonEntry person,
			Model model) {
		final PersonForm form = new PersonForm();

		form.setPrefix(person.getPrefix());
		form.setFirstName(person.getFirstName());
		form.setMiddleName(person.getMiddleName());
		form.setLastName(person.getLastName());
		form.setSuffix(person.getSuffix());
		form.setNickname(person.getNickname());

		form.setGender(person.getGender());
		form.setDominantHand(person.getDominantHand());
		form.setBirthdate(person.getBirthdate());
		form.setReleased(person.isParentReleased());
		form.setParentReleaseSentOn(person.getParentReleaseSentOn());
		form.setParentReleaseReceivedOn(person.getParentReleaseReceivedOn());
		form.setCollege(person.getCollege());

		model.addAttribute("personForm", form);
		return "people/editPerson";
	}

	/*---------- Address actions ----------*/

	@RequestMapping(value = "/people/{personId}/addresses", method = RequestMethod.POST)
	public String createAddress(@PathVariable String personId,
			@Valid AddressForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "people/newAddress";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonEntry person = personRepository.findOne(personId);
		final PersonId identifier = new PersonId(personId);
		final String addressId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final AddressDTO dto = new AddressDTO(addressId, null, person,
				form.getType(), form.getAddress1(), form.getAddress2(),
				form.getCity(), form.getRegion(), form.getPostalCode(),
				form.isPrimary(), form.isDoNotUse(), user, now, user, now);

		final RegisterAddressCommand payload = new RegisterAddressCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/people/" + personId;
	}

	@RequestMapping(value = "/{personId}/addresses/{addressId}", method = RequestMethod.PUT)
	public String updateAddress(@PathVariable String personId,
			@PathVariable String addressId, @Valid AddressForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/people/editAddress";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonEntry person = personRepository.findOne(personId);
		final PersonId identifier = new PersonId(personId);

		final AddressDTO dto = new AddressDTO(addressId, null, person,
				form.getType(), form.getAddress1(), form.getAddress2(),
				form.getCity(), form.getRegion(), form.getPostalCode(),
				form.isPrimary(), form.isDoNotUse(), user, now);

		final UpdateAddressCommand payload = new UpdateAddressCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/" + personId;
	}

	@RequestMapping(value = "/people/{personId}/addresses/new", method = RequestMethod.GET)
	public String newAddress(@PathVariable("personId") PersonEntry person,
			Model model) {
		final AddressForm form = new AddressForm();
		model.addAttribute("person", person);
		model.addAttribute("addressForm", form);
		return "people/newAddress";
	}

	@RequestMapping(value = "/people/{personId}/addresses/{addressId}/edit", method = RequestMethod.GET)
	public String editAddress(@PathVariable("personId") PersonEntry person,
			@PathVariable String addressId, Model model) {
		final AddressEntry address = person.getAddress(addressId);

		final AddressForm form = new AddressForm();
		form.setAddress1(address.getAddress1());
		form.setAddress2(address.getAddress2());
		form.setCity(address.getCity());
		form.setDoNotUse(address.isDoNotUse());
		form.setPostalCode(address.getPostalCode());
		form.setType(address.getAddressType());
		form.setPrimary(address.isPrimary());
		form.setRegion(address.getRegion());

		model.addAttribute("person", person);
		model.addAttribute("addressId", addressId);
		model.addAttribute("addressForm", form);
		return "people/editAddress";
	}

	/*---------- Contact actions ----------*/

	@RequestMapping(value = "/people/{personId}/contacts", method = RequestMethod.POST)
	public String createContact(@PathVariable("personId") PersonEntry person,
			@Valid ContactForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "people/newContact";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonId identifier = new PersonId(person.getId());
		final String contactId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final ContactDTO dto = new ContactDTO(contactId, person,
				form.getMethod(), form.getValue(), form.isPrimary(),
				form.isDoNotUse(), now, user, now, user);

		final RegisterContactCommand payload = new RegisterContactCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/" + person.getId();
	}

	@RequestMapping(value = "/people/{personId}/contacts/{contactId}", method = RequestMethod.PUT)
	public String updateContact(@PathVariable("personId") PersonEntry person,
			@PathVariable String contactId, @Valid ContactForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "/people/editContact";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonId identifier = new PersonId(person.getId());

		final ContactDTO dto = new ContactDTO(contactId, person,
				form.getMethod(), form.getValue(), form.isPrimary(),
				form.isDoNotUse(), now, user);

		final UpdateContactCommand payload = new UpdateContactCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/" + person.getId();
	}

	@RequestMapping(value = "/people/{personId}/contacts/new", method = RequestMethod.GET)
	public String newContact(@PathVariable("personId") PersonEntry person,
			Model model) {
		final ContactForm form = new ContactForm();
		model.addAttribute("person", person);
		model.addAttribute("contactForm", form);
		return "people/newContact";
	}

	@RequestMapping(value = "/people/{personId}/contacts/{contactId}/edit", method = RequestMethod.GET)
	public String editContact(@PathVariable("personId") PersonEntry person,
			@PathVariable String contactId, Model model) {
		final ContactEntry contact = person.getContact(contactId);

		final ContactForm form = new ContactForm();
		form.setMethod(contact.getMethod());
		form.setValue(contact.getValue());
		form.setDoNotUse(contact.isDoNotUse());
		form.setPrimary(contact.isPrimary());

		model.addAttribute("person", person);
		model.addAttribute("contactId", contactId);
		model.addAttribute("contactForm", form);
		return "people/editContact";
	}

	/*---------- Ajax methods ----------*/

	@RequestMapping(value = "/api/people/search", method = RequestMethod.POST)
	@ResponseBody
	public List<SearchResult> searchPeople(@RequestBody SearchPeopleForm form) {
		final List<PersonEntry> list = personRepository.findAll(
				PersonSpecifications.search(form), searchSort());

		final List<SearchResult> results = new ArrayList<>();
		for (final PersonEntry each : list) {
			final SearchResult item = new SearchResult(each.getId(),
					each.getFullName());

			if (each.primaryAddress() != null) {
				final AddressEntry address = each.primaryAddress();
				item.setCity(address.getCity());
				item.setRegion(address.getRegion().getAbbreviation());
				item.setPostalCode(address.getPostalCode());
			}
			if (each.primaryContact() != null) {
				final ContactEntry contact = each.primaryContact();
				item.setContact(contact.getValue());
			}
			results.add(item);
		}
		return results;
	}

	private Sort searchSort() {
		final List<Order> orders = new ArrayList<>();
		orders.add(new Order(Sort.Direction.ASC, "lastName"));
		orders.add(new Order(Sort.Direction.ASC, "firstName"));
		return new Sort(orders);
	}
}
