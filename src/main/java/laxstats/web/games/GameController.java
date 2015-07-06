package laxstats.web.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.Region;
import laxstats.api.games.Alignment;
import laxstats.api.games.AthleteStatus;
import laxstats.api.games.AttendeeDTO;
import laxstats.api.games.CreateGame;
import laxstats.api.games.DeleteAttendee;
import laxstats.api.games.DeleteGame;
import laxstats.api.games.GameDTO;
import laxstats.api.games.GameId;
import laxstats.api.games.RegisterAttendee;
import laxstats.api.games.Status;
import laxstats.api.games.UpdateAttendee;
import laxstats.api.games.UpdateGame;
import laxstats.api.players.Role;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.TeamEvent;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GameController extends ApplicationController {
   private static Logger logger = LoggerFactory.getLogger(GameController.class);
   private static String PACKAGE_NAME = GameController.class.getPackage().getName();

   private final GameQueryRepository eventRepository;
   private final SiteQueryRepository siteRepository;
   private final TeamQueryRepository teamRepository;

   @Autowired
   private GameValidator gameValidator;

   @Autowired
   private AttendeeValidator attendeeValidator;

   @Autowired
   protected GameController(GameQueryRepository eventRepository,
      SiteQueryRepository siteRepository, TeamQueryRepository teamRepository,
      UserQueryRepository userRepository, CommandBus commandBus) {
      super(userRepository, commandBus);
      this.eventRepository = eventRepository;
      this.siteRepository = siteRepository;
      this.teamRepository = teamRepository;
   }

   @InitBinder(value = "eventForm")
   protected void initGameBinder(WebDataBinder binder) {
      binder.setValidator(gameValidator);
   }

   @InitBinder(value = "attendeeForm")
   protected void initAttendeeBinder(WebDataBinder binder) {
      binder.setValidator(attendeeValidator);
   }

   /*---------- Action methods ----------*/

   @RequestMapping(value = "/admin/events", method = RequestMethod.GET)
   public String eventIndex(Model model) {
      final Iterable<GameEntry> list = eventRepository.findAll();
      model.addAttribute("events", list);
      return "events/index";
   }

   @RequestMapping(value = "/admin/events/{gameId}/plays", method = RequestMethod.GET)
   public String newRealTimeIndex(@PathVariable("gameId") GameEntry aggregate, Model model) {
      model.addAttribute("game", aggregate);
      model.addAttribute("homeTeam", aggregate.getHomeTeam());
      model.addAttribute("homeTeamEvent", aggregate.getHomeTeamEvent());
      model.addAttribute("visitingTeam", aggregate.getVisitingTeam());
      model.addAttribute("visitingTeamEvent", aggregate.getVisitingTeamEvent());
      return "events/realTime :: realTime";
   }

   @RequestMapping(value = "/admin/events", method = RequestMethod.POST)
   public String createEvent(@Valid GameForm form, BindingResult result, Model model,
      RedirectAttributes redirectAttributes)
   {
      final String proc = GameController.PACKAGE_NAME + ".createEvent.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         form.setTeams(getTeams());
         form.setSites(getSites());
         logger.debug("Returning errors: " + proc + "20");
         return "events/newEvent";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final GameId identifier = new GameId();
      final SiteEntry site = siteRepository.findOne(form.getSite());

      // Set team one
      TeamSeasonEntry teamOneSeason = null;
      Alignment teamOneAlignment = null;
      if (form.getTeamOne() != null) {
         final TeamEntry teamOne = teamRepository.findOne(form.getTeamOne());
         teamOneSeason = teamOne.getSeason(form.getStartsAt());
         if (teamOneSeason == null) {
            result.rejectValue("teamOne", "event.nullTeamOneSeason");
            form.setTeams(getTeams());
            form.setSites(getSites());
            logger.debug("Returning errors: " + proc + "30");
            return "events/newEvent";
         }
         teamOneAlignment = getTeamAlignment(form.getAlignment(), form.isTeamOneHome());
      }
      logger.debug(proc + "40");

      // Set team two
      TeamSeasonEntry teamTwoSeason = null;
      Alignment teamTwoAlignment = null;
      if (form.getTeamTwo() != null) {
         final TeamEntry teamTwo = teamRepository.findOne(form.getTeamTwo());
         teamTwoSeason = teamTwo.getSeason(form.getStartsAt());
         if (teamTwoSeason == null) {
            result.rejectValue("teamTwo", "event.nullTeamTwoSeason");
            form.setTeams(getTeams());
            form.setSites(getSites());
            logger.debug("Returning errors: " + proc + "50");
            return "events/newEvent";
         }
         teamTwoAlignment = getTeamAlignment(form.getAlignment(), form.isTeamTwoHome());
      }
      logger.debug(proc + "60");

      final GameDTO dto =
         new GameDTO(identifier.toString(), site, teamOneSeason, teamTwoSeason, teamOneAlignment,
            teamTwoAlignment, form.getAlignment(), form.getStartsAt(), form.getSchedule(),
            form.getStatus(), form.getWeather(), form.getDescription(), now, user, now, user);
      logger.debug(proc + "70");

      try {
         final CreateGame payload = new CreateGame(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         logger.debug(proc + "80");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "90");
      return "redirect:/admin/events";
   }

   @RequestMapping(value = "/admin/events/{gameId}", method = RequestMethod.GET)
   public String showEvent(@PathVariable("gameId") GameEntry aggregate, Model model) {
      model.addAttribute("item", aggregate);
      return "events/showEvent";
   }

   @RequestMapping(value = "/admin/events/{gameId}", method = RequestMethod.PUT)
   public String updateEvent(@PathVariable String eventId, @Valid GameForm gameForm,
      BindingResult result, Model model, RedirectAttributes redirectAttributes)
   {
      final String proc = GameController.PACKAGE_NAME + ".updateEvent.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         gameForm.setTeams(getTeams());
         gameForm.setSites(getSites());
         logger.debug("Returning errors: " + proc + "20");
         return "events/editEvent";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final GameId identifier = new GameId(eventId);
      final SiteEntry site = siteRepository.findOne(gameForm.getSite());
      TeamSeasonEntry teamOneSeason = null;
      Alignment teamOneAlignment = null;
      TeamSeasonEntry teamTwoSeason = null;
      Alignment teamTwoAlignment = null;

      // Set teams and alignments
      if (gameForm.getTeamOne() != null) {
         final TeamEntry teamOne = teamRepository.findOne(gameForm.getTeamOne());
         teamOneSeason = teamOne.getSeason(gameForm.getStartsAt());
         teamOneAlignment = getTeamAlignment(gameForm.getAlignment(), gameForm.isTeamOneHome());
      }
      if (gameForm.getTeamTwo() != null) {
         final TeamEntry teamTwo = teamRepository.findOne(gameForm.getTeamTwo());
         teamTwoSeason = teamTwo.getSeason(gameForm.getStartsAt());
         teamTwoAlignment = getTeamAlignment(gameForm.getAlignment(), gameForm.isTeamTwoHome());
      }

      final GameDTO dto =
         new GameDTO(identifier.toString(), site, teamOneSeason, teamTwoSeason, teamOneAlignment,
            teamTwoAlignment, gameForm.getAlignment(), gameForm.getStartsAt(),
            gameForm.getSchedule(), gameForm.getStatus(), gameForm.getWeather(),
            gameForm.getDescription(), now, user);
      logger.debug(proc + "30");

      try {
         final UpdateGame payload = new UpdateGame(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/events";
   }

   @RequestMapping(value = "/admin/events/{gameId}", method = RequestMethod.DELETE)
   public String deleteEvent(@PathVariable String eventId) {
      final GameId identifier = new GameId(eventId);

      final DeleteGame payload = new DeleteGame(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events";
   }

   @RequestMapping(value = "/admin/events/new", method = RequestMethod.GET)
   public String newEvent(Model model) {
      final GameForm form = new GameForm();
      form.setStatus(Status.SCHEDULED);
      form.setAlignment(SiteAlignment.HOME);
      form.setTeams(getTeams());
      form.setSites(getSites());

      model.addAttribute("eventForm", form);
      return "events/newEvent";
   }

   @RequestMapping(value = "/admin/events/{gameId}/edit", method = RequestMethod.GET)
   public String editEvent(@PathVariable("gameId") GameEntry aggregate, Model model,
      HttpServletRequest request)
   {
      final TeamEvent teamOne = aggregate.getTeams().get(0);
      final TeamEvent teamTwo = aggregate.getTeams().get(1);

      final GameForm form = new GameForm();
      form.setId(aggregate.getId());
      form.setAlignment(aggregate.getAlignment());
      form.setWeather(aggregate.getConditions());
      form.setDescription(aggregate.getDescription());
      form.setSchedule(aggregate.getSchedule());
      form.setSite(aggregate.getSite().getId());
      form.setStartsAt(aggregate.getStartsAt());
      form.setStatus(aggregate.getStatus());
      form.setTeamOne(teamOne.getTeamSeason().getTeam().getId());
      form.setTeamOneHome(teamOne.getAlignment().equals(Alignment.HOME));
      form.setTeamTwo(teamTwo.getTeamSeason().getTeam().getId());
      form.setTeamTwoHome(teamTwo.getAlignment().equals(Alignment.HOME));
      form.setTeams(getTeams());
      form.setSites(getSites());

      model.addAttribute("eventForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("back", request.getHeader("Referer"));
      return "events/editEvent";
   }

   @RequestMapping(value = "/admin/events/{gameId}/scoring", method = RequestMethod.GET)
   public String realTimeScoring(@PathVariable("gameId") GameEntry aggregate, Model model) {
      model.addAttribute("game", aggregate);
      model.addAttribute("homeTeam", aggregate.getHomeTeam());
      model.addAttribute("homeTeamEvent", aggregate.getHomeTeamEvent());
      model.addAttribute("visitingTeam", aggregate.getVisitingTeam());
      model.addAttribute("visitingTeamEvent", aggregate.getVisitingTeamEvent());
      return "events/realTimeEntry";
   }

   /*---------- Attendee actions ----------*/

   @RequestMapping(value = "/admin/events{gameId}/teamSeasons/{teamSeasonId}",
      method = RequestMethod.GET)
   public String attendeeIndex(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model)
   {
      final List<AttendeeEntry> roster = aggregate.getAttendees().get(teamSeason.getName());

      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      model.addAttribute("roster", roster);
      return "events/attendeeIndex";
   }

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}",
      method = RequestMethod.POST)
   public String createAttendee(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, @Valid AttendeeForm form,
      BindingResult result)
   {
      if (result.hasErrors()) {
         return "events/newAttendee";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String attendeeId = IdentifierFactory.getInstance().generateIdentifier();
      final PlayerEntry player = teamSeason.getPlayer(form.getPlayerId());

      final AttendeeDTO dto =
         new AttendeeDTO(attendeeId, aggregate, player, teamSeason, form.getRole(),
            form.getStatus(), player.getFullName(), player.getJerseyNumber(), now, user, now, user);

      final RegisterAttendee payload =
         new RegisterAttendee(new GameId(aggregate.getId()), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + aggregate.getId() + "/teamSeasons/" + teamSeason.getId();
   }

   @RequestMapping(
      value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}",
      method = RequestMethod.PUT)
   public String updateAttendee(@PathVariable("gameId") GameEntry event,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, @PathVariable String attendeeId,
      @Valid AttendeeForm form, BindingResult result)
   {
      if (result.hasErrors()) {
         return "events/editAttendee";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PlayerEntry player = teamSeason.getPlayer(form.getPlayerId());

      final AttendeeDTO dto =
         new AttendeeDTO(attendeeId, event, player, teamSeason, form.getRole(), form.getStatus(),
            player.getFullName(), player.getJerseyNumber(), now, user);

      final UpdateAttendee payload =
         new UpdateAttendee(new GameId(event.getId()), dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + event.getId() + "/teamSeasons/" + teamSeason.getId();
   }

   @RequestMapping(
      value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}",
      method = RequestMethod.DELETE)
   public String deleteAttendee(@PathVariable String eventId, @PathVariable String teamSeasonId,
      @PathVariable String attendeeId)
   {
      final GameId identifier = new GameId(eventId);
      final DeleteAttendee payload = new DeleteAttendee(identifier, attendeeId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/teamSeasons/" + teamSeasonId;
   }

   @RequestMapping(value = "/admin/events{gameId}/teamSeasons/{teamSeasonId}/attendees/new",
      method = RequestMethod.GET)
   public String newAttendee(@PathVariable String eventId,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model)
   {

      final AttendeeForm form = new AttendeeForm();
      form.setRole(Role.ATHLETE);
      form.setStatus(AthleteStatus.PLAYED);
      form.setRoster(teamSeason.getRosterData());

      model.addAttribute("attendeeForm", form);
      model.addAttribute("gameId", eventId);
      model.addAttribute("teamSeasonId", teamSeason.getId());
      return "events/newAttendee";
   }

   @RequestMapping(
      value = "/admin/events{gameId}/teamSeasons/{teamSeasonId}/attendees/{attendeeId}/edit",
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
      model.addAttribute("gameId", eventId);
      model.addAttribute("teamSeasonId", teamSeason.getId());
      return "events/editAttendee";
   }

   /*----------  Utilities ----------*/

   private Alignment getTeamAlignment(SiteAlignment siteAlignment, boolean isHomeTeam) {
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
      final Iterable<SiteEntry> collection = siteRepository.findAll(getSiteSorter());

      Region currentRegion = null;
      for (final SiteEntry each : collection) {
         final Region targetRegion =
            each.getAddress() == null ? null : each.getAddress().getRegion();
         List<SiteEntry> list = null;
         if (currentRegion == null || (targetRegion != null && !currentRegion.equals(targetRegion))) {
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

      final Iterable<TeamEntry> collection =
         teamRepository.findAll(new Sort(new Sort.Order("region")));

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
