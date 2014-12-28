package laxstats.web.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.events.Alignment;
import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventId;
import laxstats.api.events.UpdateEventCommand;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.events.EventEntry;
import laxstats.query.events.EventQueryRepository;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EventController extends ApplicationController {
	private final EventQueryRepository eventRepository;
	private final SiteQueryRepository siteRepository;
	private final TeamSeasonQueryRepository teamRepository;

	@Autowired
	protected EventController(EventQueryRepository eventRepository,
			SiteQueryRepository siteRepository,
			TeamSeasonQueryRepository teamRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.eventRepository = eventRepository;
		this.siteRepository = siteRepository;
		this.teamRepository = teamRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new EventFormValidator());
	}

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", eventRepository.findAll());
		return "events/index";
	}

	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public String createEvent(@ModelAttribute("form") @Valid EventForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/newEvent";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final EventId identifier = new EventId();
		final SiteEntry site = siteRepository.findOne(form.getSiteId());
		final TeamSeasonEntry teamOne = teamRepository.findOne(form
				.getTeamOneId());
		final TeamSeasonEntry teamTwo = teamRepository.findOne(form
				.getTeamTwoId());
		final Alignment teamOneAlignment = setTeamAlignment(
				form.isTeamOneHome(), form.getAlignment());
		final Alignment teamTwoAlignment = setTeamAlignment(
				form.isTeamTwoHome(), form.getAlignment());

		final EventDTO dto = new EventDTO(identifier.toString(), site, teamOne,
				teamTwo, teamOneAlignment, teamTwoAlignment,
				form.getAlignment(), form.getStartsAt(), form.getSchedule(),
				form.getStatus(), form.getConditions(), form.getDescription(),
				now, user, now, user);

		final CreateEventCommand payload = new CreateEventCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events";
	}

	@RequestMapping(value = "/events/{eventId}", method = RequestMethod.GET)
	public String showEvent(@PathVariable String eventId, Model model) {
		final EventEntry event = eventRepository.findOne(eventId);
		model.addAttribute("event", event);
		return "events/showEvent";
	}

	@RequestMapping(value = "/events/{eventId}", method = RequestMethod.PUT)
	public String updateEvent(@PathVariable String eventId,
			@ModelAttribute("form") @Valid EventForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/editEvent";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final EventId identifier = new EventId(eventId);
		final SiteEntry site = siteRepository.findOne(form.getSiteId());
		final TeamSeasonEntry teamOne = teamRepository.findOne(form
				.getTeamOneId());
		final TeamSeasonEntry teamTwo = teamRepository.findOne(form
				.getTeamTwoId());
		final Alignment teamOneAlignment = setTeamAlignment(
				form.isTeamOneHome(), form.getAlignment());
		final Alignment teamTwoAlignment = setTeamAlignment(
				form.isTeamTwoHome(), form.getAlignment());

		final EventDTO dto = new EventDTO(eventId, site, teamOne, teamTwo,
				teamOneAlignment, teamTwoAlignment, form.getAlignment(),
				form.getStartsAt(), form.getSchedule(), form.getStatus(),
				form.getConditions(), form.getDescription(), now, user);

		final UpdateEventCommand payload = new UpdateEventCommand(identifier,
				dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events";
	}

	@RequestMapping(value = "/events/{eventId}", method = RequestMethod.DELETE)
	public String deleteEvent(@PathVariable String eventId) {
		final EventId identifier = new EventId(eventId);
		final DeleteEventCommand payload = new DeleteEventCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events";
	}

	@RequestMapping(value = "/events/new", method = RequestMethod.GET)
	public String newEvent(Model model) {
		final EventForm form = new EventForm(getSiteData(), getTeamData(),
				getTeamData());
		model.addAttribute("form", form);
		return "events/newEvent";
	}

	@RequestMapping(value = "/events/{eventId}/edit", method = RequestMethod.GET)
	public String editEvent(@PathVariable String eventId, Model model) {
		final EventEntry event = eventRepository.findOne(eventId);
		final EventForm form = new EventForm(getSiteData(), getTeamData(),
				getTeamData());
		form.setSiteId(event.getId());
		form.setAlignment(event.getAlignment());
		form.setConditions(event.getConditions());
		form.setDescription(event.getDescription());
		form.setSchedule(event.getSchedule());
		form.setStartsAt(event.getStartsAt());
		form.setStatus(event.getStatus());

		model.addAttribute("form", form);
		return "events/editEvent";
	}

	private Alignment setTeamAlignment(boolean isTeamHome,
			SiteAlignment siteAlignment) {
		Alignment result = null;
		if (siteAlignment == SiteAlignment.NEUTRAL) {
			result = Alignment.NEUTRAL;
		} else {
			result = isTeamHome ? Alignment.HOME : Alignment.AWAY;
		}
		return result;
	}

	private Map<String, String> getSiteData() {
		final Sort sort = getSiteSorter();
		final Iterable<SiteEntry> sites = siteRepository.findAll(sort);
		final Map<String, String> result = new HashMap<>();
		for (final SiteEntry each : sites) {
			result.put(each.getId(), each.getName());
		}
		return result;
	}

	private Sort getSiteSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("address.region.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}

	private Map<String, String> getTeamData() {
		// TODO Need teamSeasonRepository to find all teams within a given
		// season
		final Sort sort = getTeamSorter();
		final Iterable<TeamSeasonEntry> teams = teamRepository.findAll(sort);
		final Map<String, String> result = new HashMap<>();
		for (final TeamSeasonEntry each : teams) {
			result.put(each.getId(), each.getTeam().getName());
		}
		return result;
	}

	private Sort getTeamSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("team.affiliation.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}
}
