package laxstats.web.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.Region;
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
import laxstats.query.events.GameEntry;
import laxstats.query.events.GameQueryRepository;
import laxstats.query.events.TeamEvent;
import laxstats.query.players.PlayerEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EventController extends ApplicationController {
   private final GameQueryRepository eventRepository;
   private final SiteQueryRepository siteRepository;
   private final TeamQueryRepository teamRepository;

   @Autowired
   private EventFormValidator eventFormValidator;

   @Autowired
   private AttendeeFormValidator attendeeFormValidator;

   @Autowired
   protected EventController(GameQueryRepository eventRepository,
      SiteQueryRepository siteRepository,
      TeamQueryRepository teamRepository,
      UserQueryRepository userRepository, CommandBus commandBus)
   {
      super(userRepository, commandBus);
      this.eventRepository = eventRepository;
      this.siteRepository = siteRepository;
      this.teamRepository = teamRepository;
   }

   @InitBinder(value = "eventForm")
   protected void initGameBinder(WebDataBinder binder) {
      binder.setValidator(eventFormValidator);
   }

   @InitBinder(value = "attendeeForm")
   protected void initAttendeeBinder(WebDataBinder binder) {
      binder.setValidator(attendeeFormValidator);
   }

   /*---------- Action methods ----------*/

   @RequestMapping(value = "/admin/events", method = RequestMethod.GET)
   public String eventIndex(Model model) {
      final Iterable<GameEntry> list = eventRepository.findAll();
      model.addAttribute("events", list);
      return "events/index";
   }

   @RequestMapping(value = "/admin/events", method = RequestMethod.POST)
   public String createEvent(@Valid EventForm form, BindingResult result,
      Model model)
   {
      if (result.hasErrors()) {
         form.setTeams(getTeams());
         form.setSites(getSites());
         return "events/newEvent";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final EventId identifier = new EventId();
      final SiteEntry site = siteRepository.findOne(form.getSite());

      // Set team one
      TeamSeasonEntry teamOneSeason = null;
      Alignment teamOneAlignment = null;
      if (form.getTeamOne() != null) {
         final TeamEntry teamOne = teamRepository.findOne(form.getTeamOne());
         teamOneSeason = teamOne.getSeason(form.getStartsAt());
         if (teamOneSeason == null) {
            result.rejectValue("teamOne", "event.nullTeamOneSeason");
            return "events/newEvent";
         }
         teamOneAlignment = getTeamAlignment(form.getAlignment(),
            form.isTeamOneHome());
      }

      // Set team two
      TeamSeasonEntry teamTwoSeason = null;
      Alignment teamTwoAlignment = null;
      if (form.getTeamTwo() != null) {
         final TeamEntry teamTwo = teamRepository.findOne(form.getTeamTwo());
         teamTwoSeason = teamTwo.getSeason(form.getStartsAt());
         if (teamTwoSeason == null) {
            result.rejectValue("teamTwo", "event.nullTeamTwoSeason");
            return "events/newEvent";
         }
         teamTwoAlignment = getTeamAlignment(form.getAlignment(),
            form.isTeamTwoHome());
      }

      final EventDTO dto = new EventDTO(identifier.toString(), site,
         teamOneSeason, teamTwoSeason, teamOneAlignment,
         teamTwoAlignment, form.getAlignment(), form.getStartsAt(),
         form.getSchedule(), form.getStatus(), form.getWeather(),
         form.getDescription(), now, user, now, user);

      final CreateEventCommand payload = new CreateEventCommand(identifier,
         dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events";
   }

   @RequestMapping(value = "/admin/events/{eventId}", method = RequestMethod.GET)
   public String showEvent(@PathVariable("eventId") GameEntry aggregate,
      Model model)
   {
      model.addAttribute("item", aggregate);
      return "events/showEvent";
   }

   @RequestMapping(value = "/admin/events/{eventId}", method = RequestMethod.PUT)
   public String updateEvent(@PathVariable String eventId,
      @Valid EventForm form, BindingResult result, Model model)
   {
      if (result.hasErrors()) {
         form.setTeams(getTeams());
         form.setSites(getSites());
         return "events/editEvent";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final EventId identifier = new EventId(eventId);
      final SiteEntry site = siteRepository.findOne(form.getSite());

      // Set team one
      TeamSeasonEntry teamOneSeason = null;
      Alignment teamOneAlignment = null;
      if (form.getTeamOne() != null) {
         final TeamEntry teamOne = teamRepository.findOne(form.getTeamOne());
         teamOneSeason = teamOne.getSeason(form.getStartsAt());
         if (teamOneSeason == null) {
            result.rejectValue("teamOne", "event.nullTeamOneSeason");
            return "events/editEvent";
         }
         teamOneAlignment = getTeamAlignment(form.getAlignment(),
            form.isTeamOneHome());
      }

      // Set team two
      TeamSeasonEntry teamTwoSeason = null;
      Alignment teamTwoAlignment = null;
      if (form.getTeamTwo() != null) {
         final TeamEntry teamTwo = teamRepository.findOne(form.getTeamTwo());
         teamTwoSeason = teamTwo.getSeason(form.getStartsAt());
         if (teamTwoSeason == null) {
            result.rejectValue("teamTwo", "event.nullTeamTwoSeason");
            return "events/editEvent";
         }
         teamTwoAlignment = getTeamAlignment(form.getAlignment(),
            form.isTeamTwoHome());
      }

      final EventDTO dto = new EventDTO(identifier.toString(), site,
         teamOneSeason, teamTwoSeason, teamOneAlignment,
         teamTwoAlignment, form.getAlignment(), form.getStartsAt(),
         form.getSchedule(), form.getStatus(), form.getWeather(),
         form.getDescription(), now, user);

      final UpdateEventCommand payload = new UpdateEventCommand(identifier,
         dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events";
   }

   @RequestMapping(value = "/admin/events/{eventId}", method = RequestMethod.DELETE)
   public String deleteEvent(@PathVariable String eventId) {
      final EventId identifier = new EventId(eventId);

      final DeleteEventCommand payload = new DeleteEventCommand(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events";
   }

   @RequestMapping(value = "/admin/events/new", method = RequestMethod.GET)
   public String newEvent(Model model) {
      final EventForm form = new EventForm();
      form.setStatus(Status.SCHEDULED);
      form.setAlignment(SiteAlignment.HOME);
      form.setTeams(getTeams());
      form.setSites(getSites());

      model.addAttribute("eventForm", form);
      return "events/newEvent";
   }

   @RequestMapping(value = "/admin/events/{eventId}/edit", method = RequestMethod.GET)
   public String editEvent(@PathVariable("eventId") GameEntry aggregate,
      Model model)
   {
      final TeamEvent teamOne = aggregate.getTeams().get(0);
      final TeamEvent teamTwo = aggregate.getTeams().get(1);

      final EventForm form = new EventForm();
      form.setAlignment(aggregate.getAlignment());
      form.setWeather(aggregate.getConditions());
      form.setDescription(aggregate.getDescription());
      form.setSchedule(aggregate.getSchedule());
      form.setSite(aggregate.getSite().getId());
      form.setStartsAt(aggregate.getStartsAt());
      form.setStatus(aggregate.getStatus());
      form.setTeamOne(teamOne.getTeamSeason().getId());
      form.setTeamOneHome(teamOne.getAlignment().equals(Alignment.HOME));
      form.setTeamTwo(teamTwo.getTeamSeason().getId());
      form.setTeamOneHome(teamTwo.getAlignment().equals(Alignment.HOME));
      form.setTeams(getTeams());
      form.setSites(getSites());

      model.addAttribute("eventForm", form);
      model.addAttribute("event", aggregate);
      return "events/editEvent";
   }

   @RequestMapping(value = "/admin/events/{eventId}/scoring", method = RequestMethod.GET)
   public String realTimeScoring(@PathVariable("eventId") GameEntry aggregate, Model model) {
      model.addAttribute("game", aggregate);
      model.addAttribute("homeTeam", aggregate.getHomeTeam());
      model.addAttribute("visitingTeam", aggregate.getVisitingTeam());
      return "events/realTimeEntry";
   }

   /*---------- Attendee actions ----------*/

   @RequestMapping(value = "/admin/events{eventId}/teamSeasons/{teamSeasonId}",
      method = RequestMethod.GET)
   public String attendeeIndex(@PathVariable("eventId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      Model model)
   {
      final List<AttendeeEntry> roster = aggregate.getAttendees().get(
         teamSeason.getName());

      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      model.addAttribute("roster", roster);
      return "events/attendeeIndex";
   }

   @RequestMapping(value = "/admin/events/{eventId}/teamSeasons/{teamSeasonId}",
      method = RequestMethod.POST)
   public String createAttendee(@PathVariable("eventId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @Valid AttendeeForm form, BindingResult result)
   {
      if (result.hasErrors()) {
         return "events/newAttendee";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String attendeeId = IdentifierFactory.getInstance()
         .generateIdentifier();
      final PlayerEntry player = teamSeason.getPlayer(form.getPlayerId());

      final AttendeeDTO dto = new AttendeeDTO(attendeeId, aggregate, player,
         teamSeason, form.getRole(), form.getStatus(),
         player.getFullName(), player.getJerseyNumber(), now, user, now,
         user);

      final RegisterAttendeeCommand payload = new RegisterAttendeeCommand(
         new EventId(aggregate.getId()), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + aggregate.getId() + "/teamSeasons/"
         + teamSeason.getId();
   }

   @RequestMapping(
      value = "/admin/events/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}",
      method = RequestMethod.PUT)
   public String updateAttendee(@PathVariable("eventId") GameEntry event,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @PathVariable String attendeeId, @Valid AttendeeForm form,
      BindingResult result)
   {
      if (result.hasErrors()) {
         return "events/editAttendee";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PlayerEntry player = teamSeason.getPlayer(form.getPlayerId());

      final AttendeeDTO dto = new AttendeeDTO(attendeeId, event, player,
         teamSeason, form.getRole(), form.getStatus(),
         player.getFullName(), player.getJerseyNumber(), now, user);

      final UpdateAttendeeCommand payload = new UpdateAttendeeCommand(
         new EventId(event.getId()), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + event.getId() + "/teamSeasons/"
         + teamSeason.getId();
   }

   @RequestMapping(
      value = "/admin/events/{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}",
      method = RequestMethod.DELETE)
   public String deleteAttendee(@PathVariable String eventId,
      @PathVariable String teamSeasonId, @PathVariable String attendeeId)
   {
      final EventId identifier = new EventId(eventId);
      final DeleteAttendeeCommand payload = new DeleteAttendeeCommand(
         identifier, attendeeId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/teamSeasons/"
         + teamSeasonId;
   }

   @RequestMapping(value = "/admin/events{eventId}/teamSeasons/{teamSeasonId}/attendees/new",
      method = RequestMethod.GET)
   public String newAttendee(@PathVariable String eventId,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      Model model)
   {

      final AttendeeForm form = new AttendeeForm();
      form.setRole(Role.ATHLETE);
      form.setStatus(AthleteStatus.PLAYED);
      form.setRoster(teamSeason.getRosterData());

      model.addAttribute("attendeeForm", form);
      model.addAttribute("eventId", eventId);
      model.addAttribute("teamSeasonId", teamSeason.getId());
      return "events/newAttendee";
   }

   @RequestMapping(
      value = "/admin/events{eventId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}/edit",
      method = RequestMethod.GET)
   public String editAttendee(@PathVariable String eventId,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @PathVariable("attendeeId") AttendeeEntry entity, Model model)
   {

      final AttendeeForm form = new AttendeeForm();
      form.setPlayerId(entity.getPlayer().getId());
      form.setRole(entity.getRole());
      form.setStatus(entity.getStatus());
      form.setRoster(teamSeason.getRosterData());

      model.addAttribute("attendeeForm", form);
      model.addAttribute("eventId", eventId);
      model.addAttribute("teamSeasonId", teamSeason.getId());
      return "events/editAttendee";
   }

   /*----------  Utilities ----------*/

   private Alignment getTeamAlignment(SiteAlignment siteAlignment,
      boolean isHomeTeam)
   {
      Alignment result = null;
      if (siteAlignment.equals(SiteAlignment.NEUTRAL)) {
         result = Alignment.NEUTRAL;
      }
      else {
         result = isHomeTeam ? Alignment.HOME : Alignment.AWAY;
      }
      return result;
   }

   private Map<Region, List<SiteEntry>> getSites() {
      final Map<Region, List<SiteEntry>> result = new HashMap<>();
      final Iterable<SiteEntry> collection = siteRepository
         .findAll(getSiteSorter());

      Region currentRegion = null;
      for (final SiteEntry each : collection) {
         final Region targetRegion = each.getAddress() == null ? null : each
            .getAddress().getRegion();
         List<SiteEntry> list = null;
         if (currentRegion == null
            || (targetRegion != null && !currentRegion
               .equals(targetRegion)))
         {
            currentRegion = targetRegion;
            list = new ArrayList<SiteEntry>();
            result.put(targetRegion, list);
         }
         else {
            list = result.get(targetRegion);
         }
         list.add(each);
      }
      return result;
   }

   private Sort getSiteSorter() {
      final List<Sort.Order> sort = new ArrayList<>();
      sort.add(new Sort.Order("address.region"));
      sort.add(new Sort.Order("name"));
      return new Sort(sort);
   }

   private Map<Region, List<TeamEntry>> getTeams() {
      final Map<Region, List<TeamEntry>> result = new HashMap<>();

      final Iterable<TeamEntry> collection = teamRepository.findAll(new Sort(
         new Sort.Order("region")));

      Region currentRegion = null;
      for (final TeamEntry each : collection) {
         final Region targetRegion = each.getRegion();
         List<TeamEntry> list = null;
         if (currentRegion == null || !currentRegion.equals(targetRegion)) {
            list = new ArrayList<>();
            result.put(targetRegion, list);
            currentRegion = targetRegion;
         }
         else {
            list = result.get(targetRegion);
         }
         list.add(each);
      }
      return result;
   }
}
