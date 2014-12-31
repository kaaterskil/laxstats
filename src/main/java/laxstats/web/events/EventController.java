package laxstats.web.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.events.Alignment;
import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteAttendeeCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventId;
import laxstats.api.events.RegisterAttendeeCommand;
import laxstats.api.events.UpdateAttendeeCommand;
import laxstats.api.events.UpdateEventCommand;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.AttendeeQueryRepository;
import laxstats.query.events.EventEntry;
import laxstats.query.events.EventQueryRepository;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
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
@RequestMapping("/events")
public class EventController extends ApplicationController {
	private final EventQueryRepository eventRepository;
	private final SiteQueryRepository siteRepository;
	private final TeamSeasonQueryRepository teamRepository;
	private final PlayerQueryRepository playerRepository;
	private final AttendeeQueryRepository attendeeRepository;

	@Autowired
	protected EventController(EventQueryRepository eventRepository,
			SiteQueryRepository siteRepository,
			TeamSeasonQueryRepository teamRepository,
			PlayerQueryRepository playerRepository,
			AttendeeQueryRepository attendeeRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.eventRepository = eventRepository;
		this.siteRepository = siteRepository;
		this.teamRepository = teamRepository;
		this.playerRepository = playerRepository;
		this.attendeeRepository = attendeeRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new EventFormValidator());
		binder.setValidator(new AttendeeFormValidator());
	}

	/*---------- Event actions ----------*/

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("items", eventRepository.findAll());
		return "events/index";
	}

	@RequestMapping(method = RequestMethod.POST)
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

	@RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
	public String showEvent(@PathVariable String eventId, Model model) {
		final EventEntry event = eventRepository.findOne(eventId);
		model.addAttribute("event", event);
		return "events/showEvent";
	}

	@RequestMapping(value = "/{eventId}", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
	public String deleteEvent(@PathVariable String eventId) {
		final EventId identifier = new EventId(eventId);
		final DeleteEventCommand payload = new DeleteEventCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newEvent(Model model) {
		final EventForm form = new EventForm(getSiteData(), getTeamData(),
				getTeamData());
		model.addAttribute("form", form);
		return "events/newEvent";
	}

	@RequestMapping(value = "/{eventId}/edit", method = RequestMethod.GET)
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

	/* ---------- Event Attendee actions ---------- */

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamSeasonId}", method = RequestMethod.GET)
	public String attendeeIndex(@PathVariable String eventId,
			@PathVariable String teamSeasonId, Model model) {
		final List<AttendeeEntry> roster = attendeeRepository
				.findByEventAndTeamSeason(eventId, teamSeasonId);
		model.addAttribute("items", roster);
		return "events/attendeeIndex";
	}

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamSeasonId}", method = RequestMethod.POST)
	public String createAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId,
			@ModelAttribute("form") @Valid AttendeeForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/newAttendee";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String attendeeId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final EventEntry event = eventRepository.findOne(eventId);
		final PlayerEntry player = playerRepository.findOne(form.getPlayerId());
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);

		final AttendeeDTO dto = new AttendeeDTO(attendeeId, event, player,
				teamSeason, form.getRole(), form.getStatus(), player
						.getPerson().getFullName(), player.getJerseyNumber(),
				now, user, now, user);
		final RegisterAttendeeCommand payload = new RegisterAttendeeCommand(
				new EventId(eventId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/teamSeasons/" + teamSeasonId;
	}

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}", method = RequestMethod.GET)
	public String showAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId,
			Model model) {
		final AttendeeEntry attendee = attendeeRepository.findOne(attendeeId);
		model.addAttribute("item", attendee);
		return "events/showAttendee";
	}

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}", method = RequestMethod.PUT)
	public String updateAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId,
			@ModelAttribute("form") @Valid AttendeeForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/editAttendee";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final EventEntry event = eventRepository.findOne(eventId);
		final PlayerEntry player = playerRepository.findOne(form.getPlayerId());
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);

		final AttendeeDTO dto = new AttendeeDTO(attendeeId, event, player,
				teamSeason, form.getRole(), form.getStatus(), player
						.getPerson().getFullName(), player.getJerseyNumber(),
				now, user);
		final UpdateAttendeeCommand payload = new UpdateAttendeeCommand(
				new EventId(eventId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/teamSeason/" + teamSeasonId;
	}

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}", method = RequestMethod.DELETE)
	public String deleteAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId) {
		final EventId identifier = new EventId(eventId);
		final DeleteAttendeeCommand payload = new DeleteAttendeeCommand(
				identifier, attendeeId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/teamSeason/" + teamSeasonId;
	}

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamseasonId}/new", method = RequestMethod.GET)
	public String newAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, Model model) {
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);

		final AttendeeForm form = new AttendeeForm();
		form.setTeamSeasonId(teamSeasonId);
		form.setPlayerData(teamSeason.getRosterData());

		model.addAttribute("form", form);
		return "events/newAttendee";
	}

	@RequestMapping(value = "/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}/edit", method = RequestMethod.GET)
	public String editAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId,
			Model model) {
		final AttendeeEntry attendee = attendeeRepository.findOne(attendeeId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);

		final AttendeeForm form = new AttendeeForm();
		form.setPlayerId(attendee.getPlayer().getId());
		form.setRole(attendee.getRole());
		form.setStatus(attendee.getStatus());
		form.setTeamSeasonId(teamSeasonId);
		form.setPlayerData(teamSeason.getRosterData());

		model.addAttribute("form", form);
		return "events/editAttendee";
	}

	/* ---------- Utilities ---------- */

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
