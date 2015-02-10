package laxstats.web.relationships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.relationships.CreateRelationshipCommand;
import laxstats.api.relationships.DeleteRelationshipCommand;
import laxstats.api.relationships.RelationshipDTO;
import laxstats.api.relationships.RelationshipId;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.relationships.RelationshipQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/people")
public class RelationshipController extends ApplicationController {
	private final PersonQueryRepository personRepository;
	private Map<String, String> peopleData = new HashMap<>();

	@Autowired
	public RelationshipController(UserQueryRepository userRepository,
			CommandBus commandBus, RelationshipQueryRepository repository,
			PersonQueryRepository personRepository) {
		super(userRepository, commandBus);
		this.personRepository = personRepository;
	}

	@RequestMapping(value = "/{personId}/relationships", method = RequestMethod.GET)
	public String relationshipIndex(@PathVariable String personId, Model model) {
		final PersonEntry person = personRepository.findOne(personId);
		model.addAttribute("children", person.getChildren(null));
		model.addAttribute("parents", person.getParents(null));
		return "relationships/index";
	}

	@RequestMapping(value = "/{personId}/relationships", method = RequestMethod.POST)
	public String createRelationship(@PathVariable String personId,
			@ModelAttribute("form") @Valid RelationshipForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "relationships/newRelationship";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final PersonEntry parent = personRepository.findOne(form.getParentId());
		final PersonEntry child = personRepository.findOne(form.getChildId());
		final RelationshipId identifier = new RelationshipId();

		final RelationshipDTO dto = new RelationshipDTO(identifier, parent,
				child, form.getType(), now, user);
		final CreateRelationshipCommand payload = new CreateRelationshipCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/admin/people/" + personId + "/relationships";
	}

	@RequestMapping(value = "/{personId}/relationships/{relationshipId}", method = RequestMethod.DELETE)
	public String deleteRelationship(@PathVariable String personId,
			@PathVariable String relationshipId, Model model) {
		final RelationshipId identifier = new RelationshipId(relationshipId);
		final DeleteRelationshipCommand payload = new DeleteRelationshipCommand(
				identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/admin/people/" + personId + "/relationships";
	}

	@RequestMapping(value = "/{personId}/relationships/new", method = RequestMethod.GET)
	public String newRelationship(@PathVariable String personId, Model model) {
		final RelationshipForm form = new RelationshipForm(getPeopleData(),
				getPeopleData());
		model.addAttribute("form", form);
		return "relationships/newRelationship";
	}

	private Map<String, String> getPeopleData() {
		if (peopleData.size() == 0) {
			final Map<String, String> data = new HashMap<>();
			final Iterable<PersonEntry> list = personRepository
					.findAll(getPeopleSorter());
			for (final PersonEntry each : list) {
				final StringBuilder sb = new StringBuilder();
				sb.append(each.getFullName());
				if (each.getAddresses().size() > 0) {
					sb.append(", ").append(each.primaryAddress().fullAddress());
				}
				data.put(each.getId(), sb.toString());
			}
			peopleData = data;
		}
		return peopleData;
	}

	private Sort getPeopleSorter() {
		final List<Sort.Order> orderBy = new ArrayList<>();
		orderBy.add(new Sort.Order("lastName"));
		orderBy.add(new Sort.Order("firstName"));
		return new Sort(orderBy);
	}
}
