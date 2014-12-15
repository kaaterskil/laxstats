package laxstats.web.events;

import javax.validation.Valid;

import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventId;
import laxstats.api.events.UpdateEventCommand;
import laxstats.query.events.EventEntry;
import laxstats.query.events.EventQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
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
@RequestMapping("/events")
public class EventController extends ApplicationController {
	private final EventQueryRepository eventRepository;

	@Autowired
	protected EventController(EventQueryRepository eventRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.eventRepository = eventRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", eventRepository.findAll());
		return "events/index";
	}

	@RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
	public String showEvent(@PathVariable String eventId, Model model) {
		final EventEntry event = eventRepository.findOne(eventId);
		model.addAttribute("event", event);
		return "events/showEvent";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newEvent(Model model) {
		model.addAttribute("form", new EventForm());
		return "events/newEvent";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createEvent(@ModelAttribute("form") @Valid EventForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "events/newEvent";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final EventId identifier = new EventId();

		final EventDTO dto = new EventDTO();
		dto.setSite(form.getSite());
		dto.setAlignment(form.getAlignment());
		dto.setStartsAt(form.getStartsAt());
		dto.setSchedule(form.getSchedule());
		dto.setStatus(form.getStatus());
		dto.setConditions(dto.getConditions());
		dto.setDescription(form.getDescription());
		dto.setCreatedAt(now);
		dto.setCreatedBy(user);
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		final CreateEventCommand payload = new CreateEventCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/index";
	}

	@RequestMapping(value = "/{eventId}/edit", method = RequestMethod.GET)
	public String editEvent(@PathVariable String eventId, Model model) {
		final EventEntry event = eventRepository.findOne(eventId);

		final EventForm form = new EventForm();
		form.setAlignment(event.getAlignment());
		form.setConditions(event.getConditions());
		form.setDescription(event.getDescription());
		form.setSchedule(event.getSchedule());
		form.setSite(event.getSite());
		form.setStartsAt(event.getStartsAt());
		form.setStatus(event.getStatus());

		model.addAttribute("form", form);
		return "events/editEvent";
	}

	@RequestMapping(value = "/{eventId}", method = RequestMethod.PUT)
	public String updateEvent(@PathVariable String eventId,
			@ModelAttribute("form") @Valid EventForm form,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "events/editEvent";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final EventId identifier = new EventId(eventId);

		final EventDTO dto = new EventDTO();
		dto.setSite(form.getSite());
		dto.setAlignment(form.getAlignment());
		dto.setStartsAt(form.getStartsAt());
		dto.setSchedule(form.getSchedule());
		dto.setStatus(form.getStatus());
		dto.setConditions(dto.getConditions());
		dto.setDescription(form.getDescription());
		dto.setModifiedAt(now);
		dto.setModifiedBy(user);

		final UpdateEventCommand payload = new UpdateEventCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/index";
	}

	@RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
	public String deleteEvent(@PathVariable String eventId) {
		final EventId identifier = new EventId(eventId);
		final DeleteEventCommand payload = new DeleteEventCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/index";
	}
}
