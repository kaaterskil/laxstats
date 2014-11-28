package laxstats.web.people;

import javax.validation.Valid;

import laxstats.api.people.CreatePersonCommand;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.query.people.Person;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
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
	private PersonQueryRepository personRepository;
	private UserQueryRepository userRepository;
	private CommandBus commandBus;

	@Autowired
	public PersonController(PersonQueryRepository personRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		this.personRepository = personRepository;
		this.userRepository = userRepository;
		this.commandBus = commandBus;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", personRepository.findAll());
		return "people/index";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newPerson(Model model) {
		PersonForm form = new PersonForm();
		model.addAttribute("form", form);
		return "people/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createPerson(@ModelAttribute("form") @Valid PersonForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "people/new";
		}
		LocalDateTime now = LocalDateTime.now();
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserEntry user = userRepository.findByEmail(principal.toString());
		PersonId identifier = new PersonId();

		PersonDTO dto = new PersonDTO(identifier, form.getPrefix(),
				form.getFirstName(), form.getMiddleName(), form.getLastName(),
				form.getSuffix(), form.getNickname(),
				PersonForm.fullName(form), form.getGender(),
				form.getDominantHand(), false, null, null, form.getBirthdate(),
				null, null, null, user.getId(), now, user.getId(), now);

		CreatePersonCommand command = new CreatePersonCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<CreatePersonCommand>(
				command));
		return "redirect:/people";
	}
	
	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	public String showPerson(@PathVariable String personId, Model model) {
		Person person = personRepository.findOne(personId);
		model.addAttribute("item", person);
		return "people/show";
	}
	
}
