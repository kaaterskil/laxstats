package laxstats.web.events;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.events.Alignment;
import laxstats.api.events.AthleteStatus;
import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.CreateEventCommand;
import laxstats.api.events.DeleteAttendeeCommand;
import laxstats.api.events.DeleteEventCommand;
import laxstats.api.events.EventDTO;
import laxstats.api.events.EventId;
import laxstats.api.events.RegisterAttendeeCommand;
import laxstats.api.events.Status;
import laxstats.api.events.UpdateAttendeeCommand;
import laxstats.api.events.UpdateEventCommand;
import laxstats.api.players.Role;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.AttendeeQueryRepository;
import laxstats.query.events.EventEntry;
import laxstats.query.events.EventQueryRepository;
import laxstats.query.events.TeamEvent;
import laxstats.query.players.PlayerEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventController extends ApplicationController {
	private final EventQueryRepository eventRepository;
	private final SeasonQueryRepository seasonRepository;
	private final SiteQueryRepository siteRepository;
	private final TeamSeasonQueryRepository teamRepository;
	private final AttendeeQueryRepository attendeeRepository;

	@Autowired
	protected EventController(EventQueryRepository eventRepository,
			SeasonQueryRepository seasonRepository,
			SiteQueryRepository siteRepository,
			TeamSeasonQueryRepository teamRepository,
			AttendeeQueryRepository attendeeRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.eventRepository = eventRepository;
		this.seasonRepository = seasonRepository;
		this.siteRepository = siteRepository;
		this.teamRepository = teamRepository;
		this.attendeeRepository = attendeeRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new EventFormValidator());
		binder.setValidator(new AttendeeFormValidator());
	}

	/*---------- Event actions ----------*/

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String eventIndex(Model model) {
		final Iterable<EventEntry> list = eventRepository.findAll();
		model.addAttribute("events", list);
		return "events/index";
	}

	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public String createEvent(
			@ModelAttribute("eventForm") @Valid EventForm form,
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

		Alignment teamOneAlignment = null;
		Alignment teamTwoAlignment = null;
		if (form.getAlignment().equals(SiteAlignment.NEUTRAL)) {
			teamOneAlignment = Alignment.NEUTRAL;
			teamTwoAlignment = Alignment.NEUTRAL;
		} else {
			teamOneAlignment = form.isTeamOneHome() ? Alignment.HOME
					: Alignment.AWAY;
			teamTwoAlignment = form.isTeamTwoHome() ? Alignment.HOME
					: Alignment.AWAY;
		}

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
		final EventEntry aggregate = eventRepository.findOne(eventId);
		model.addAttribute("item", aggregate);
		return "events/showEvent";
	}

	@RequestMapping(value = "/events/{eventId}", method = RequestMethod.PUT)
	public String updateEvent(@PathVariable String eventId,
			@ModelAttribute("eventForm") @Valid EventForm form,
			BindingResult result) {
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

		Alignment teamOneAlignment = null;
		Alignment teamTwoAlignment = null;
		if (form.getAlignment().equals(SiteAlignment.NEUTRAL)) {
			teamOneAlignment = Alignment.NEUTRAL;
			teamTwoAlignment = Alignment.NEUTRAL;
		} else {
			teamOneAlignment = form.isTeamOneHome() ? Alignment.HOME
					: Alignment.AWAY;
			teamTwoAlignment = form.isTeamTwoHome() ? Alignment.HOME
					: Alignment.AWAY;
		}

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
		final EventForm form = new EventForm();
		form.setStatus(Status.SCHEDULED);
		form.setSeasons(getSeasons());
		form.setSites(getSites());

		model.addAttribute("eventForm", form);
		return "event/newEvent";
	}

	@RequestMapping(value = "/events/{eventId}/edit", method = RequestMethod.GET)
	public String editEvent(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final TeamEvent teamOne = aggregate.getTeams().get(0);
		final TeamEvent teamTwo = aggregate.getTeams().get(1);
		final SeasonEntry season = teamOne.getTeamSeason().getSeason();

		final EventForm form = new EventForm();
		form.setAlignment(aggregate.getAlignment());
		form.setConditions(aggregate.getConditions());
		form.setDescription(aggregate.getDescription());
		form.setSchedule(aggregate.getSchedule());
		form.setSiteId(aggregate.getSite().getId());
		form.setStartsAt(aggregate.getStartsAt());
		form.setStatus(aggregate.getStatus());
		form.setTeamOneId(teamOne.getTeamSeason().getId());
		form.setTeamOneHome(teamOne.getAlignment().equals(Alignment.HOME));
		form.setTeamTwoId(teamTwo.getTeamSeason().getId());
		form.setTeamOneHome(teamTwo.getAlignment().equals(Alignment.HOME));
		form.setTeams(getTeams(season.getId()));
		form.setSeasons(getSeasons());
		form.setSites(getSites());

		model.addAttribute("eventForm", form);
		model.addAttribute("event", aggregate);
		return "events/editEvent";
	}

	/*---------- Attendee actions ----------*/

	@RequestMapping(value = "/events{eventId}/teamSeasons/{teamSeasonId}", method = RequestMethod.GET)
	public String attendeeIndex(@PathVariable String eventId,
			@PathVariable String teamSeasonId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);
		final List<AttendeeEntry> roster = aggregate.getAttendees().get(
				teamSeason.getName());

		model.addAttribute("event", aggregate);
		model.addAttribute("teamSeason", teamSeason);
		model.addAttribute("roster", roster);
		return "events/attendeeIndex";
	}

	@RequestMapping(value = "/events/{eventId}/teamSeasons/{teamSeasonId}", method = RequestMethod.POST)
	public String createAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId,
			@ModelAttribute("AttendeeForm") @Valid AttendeeForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/newAttendee";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String attendeeId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);
		final PlayerEntry player = teamSeason.getPlayer(form.getPlayerId());

		final AttendeeDTO dto = new AttendeeDTO(attendeeId, aggregate, player,
				teamSeason, form.getRole(), form.getStatus(),
				player.getFullName(), player.getJerseyNumber(), now, user, now,
				user);

		final RegisterAttendeeCommand payload = new RegisterAttendeeCommand(
				new EventId(eventId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/teamSeasons/" + teamSeasonId;
	}

	@RequestMapping(value = "/events/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}", method = RequestMethod.PUT)
	public String updateAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId,
			@ModelAttribute("AttendeeForm") @Valid AttendeeForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/editAttendee";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final EventEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);
		final PlayerEntry player = teamSeason.getPlayer(form.getPlayerId());

		final AttendeeDTO dto = new AttendeeDTO(attendeeId, event, player,
				teamSeason, form.getRole(), form.getStatus(),
				player.getFullName(), player.getJerseyNumber(), now, user);

		final UpdateAttendeeCommand payload = new UpdateAttendeeCommand(
				new EventId(eventId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/teamSeasons/" + teamSeasonId;
	}

	@RequestMapping(value = "/events/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}", method = RequestMethod.DELETE)
	public String deleteAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId) {
		final EventId identifier = new EventId(eventId);
		final DeleteAttendeeCommand payload = new DeleteAttendeeCommand(
				identifier, attendeeId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/teamSeasons/" + teamSeasonId;
	}

	@RequestMapping(value = "/events{eventId}/teamSeasons/{teamSeasonId}/attendees/new", method = RequestMethod.GET)
	public String newAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, Model model) {
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);

		final AttendeeForm form = new AttendeeForm();
		form.setRole(Role.ATHLETE);
		form.setStatus(AthleteStatus.PLAYED);
		form.setRoster(teamSeason.getRosterData());

		model.addAttribute("attendeeForm", form);
		model.addAttribute("eventId", eventId);
		model.addAttribute("teamSeasonId", teamSeasonId);
		return "events/newAttendee";
	}

	@RequestMapping(value = "/events{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}/edit", method = RequestMethod.GET)
	public String editAttendee(@PathVariable String eventId,
			@PathVariable String teamSeasonId, @PathVariable String attendeeId,
			Model model) {
		final AttendeeEntry entity = attendeeRepository.findOne(attendeeId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(teamSeasonId);

		final AttendeeForm form = new AttendeeForm();
		form.setPlayerId(entity.getPlayer().getId());
		form.setRole(entity.getRole());
		form.setStatus(entity.getStatus());
		form.setRoster(teamSeason.getRosterData());

		model.addAttribute("attendeeForm", form);
		model.addAttribute("eventId", eventId);
		model.addAttribute("teamSeasonId", teamSeasonId);
		return "events/editAttendee";
	}

	/*---------- Ajax methods ----------*/

	@RequestMapping(value = "/rest/events/teams/{seasonId}", method = RequestMethod.GET)
	public @ResponseBody List<TeamSeasonSelect> ajaxGetTeams(
			@RequestParam(value = "seasonId", required = true) String seasonId) {
		return getTeams(seasonId);
	}

	/*----------  Utilities ----------*/

	private List<SeasonEntry> getSeasons() {
		return (List<SeasonEntry>) seasonRepository.findAll();
	}

	private List<SiteEntry> getSites() {
		final Sort sort = getSiteSorter();
		return (List<SiteEntry>) siteRepository.findAll(sort);
	}

	private Sort getSiteSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("address.region.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}

	private List<TeamSeasonSelect> getTeams(String seasonId) {
		final List<TeamSeasonEntry> teams = teamRepository.findBySeasonId(
				seasonId, getTeamSorter());

		final List<TeamSeasonSelect> list = new ArrayList<>();
		for (final TeamSeasonEntry each : teams) {
			final TeamSeasonSelect entry = new TeamSeasonSelect(each.getId(),
					each.getName());
			list.add(entry);
		}
		return list;
	}

	private Sort getTeamSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("affiliation.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}
}
