package laxstats.web.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.events.EventId;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlayRole;
import laxstats.api.events.PlayType;
import laxstats.api.events.PlayUtils;
import laxstats.api.events.RecordClearCommand;
import laxstats.api.events.RecordGoalCommand;
import laxstats.api.events.RecordGroundBallCommand;
import laxstats.api.events.RecordShotCommand;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.ClearEntry;
import laxstats.query.events.EventEntry;
import laxstats.query.events.EventQueryRepository;
import laxstats.query.events.FaceOffEntry;
import laxstats.query.events.GoalEntry;
import laxstats.query.events.GroundBallEntry;
import laxstats.query.events.PlayEntry;
import laxstats.query.events.ShotEntry;
import laxstats.query.events.TeamEvent;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PlayController extends ApplicationController {
	private final EventQueryRepository eventRepository;
	private final TeamSeasonQueryRepository teamRepository;

	@Autowired
	public PlayController(UserQueryRepository userRepository,
			CommandBus commandBus, EventQueryRepository eventRepository,
			TeamSeasonQueryRepository teamRepository) {
		super(userRepository, commandBus);
		this.eventRepository = eventRepository;
		this.teamRepository = teamRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ClearFormValidator());
		binder.setValidator(new FaceOffFormValidator());
		binder.setValidator(new GoalFormValidator());
		binder.setValidator(new GroundBallFormValidator());
		binder.setValidator(new ShotFormValidator());
	}

	/*---------- Goal actions ----------*/

	@RequestMapping(value = "events/{eventId}/goals", method = RequestMethod.GET)
	public String goalIndex(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.GOAL, aggregate,
				aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.GOAL, aggregate,
				aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/goalIndex";
	}

	@RequestMapping(value = "events/{eventId}/goals/new", method = RequestMethod.GET)
	public String newGoal(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final GoalForm form = new GoalForm();
		form.setTeams(getTeams(aggregate));
		model.addAttribute("form", form);
		return "events/newGoal";
	}

	@RequestMapping(value = "events/{eventId}/goals", method = RequestMethod.POST)
	public String createGoal(@PathVariable String eventId,
			@ModelAttribute("form") @Valid GoalForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/newGoal";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final EventEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create scorer
		final String scorerId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry scorer = event.getAttendee(form.getScorerId());
		final PlayParticipantDTO scorerDTO = new PlayParticipantDTO(scorerId,
				playId, scorer, teamSeason, PlayRole.SCORER, true, now, user,
				now, user);
		participants.add(scorerDTO);

		// Create assist
		final AttendeeEntry assist = event.getAttendee(form.getAssistId());
		if (assist != null) {
			final String assistId = IdentifierFactory.getInstance()
					.generateIdentifier();
			final PlayParticipantDTO assistDTO = new PlayParticipantDTO(
					assistId, playId, assist, teamSeason, PlayRole.ASSIST,
					true, now, user, now, user);
			participants.add(assistDTO);
		}

		// Create goal
		final PlayDTO dto = new PlayDTO(playId, PlayType.GOAL, PlayKey.GOAL,
				event, teamSeason, form.getPeriod(), form.getElapsedTime(),
				null, PlayResult.GOAL, form.getComment(), now, user, now, user,
				participants);

		final RecordGoalCommand payload = new RecordGoalCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/goals";
	}

	/*---------- Shot actions ----------*/

	@RequestMapping(value = "events/{eventId}/shots", method = RequestMethod.GET)
	public String shotIndex(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.SHOT, aggregate,
				aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.SHOT, aggregate,
				aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/shotIndex";
	}

	@RequestMapping(value = "events/{eventId}/shots/new", method = RequestMethod.GET)
	public String newShot(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final ShotForm form = new ShotForm();
		form.setTeams(getTeams(aggregate));
		model.addAttribute("form", form);
		return "events/newShot";
	}

	@RequestMapping(value = "events/{eventId}/shots", method = RequestMethod.POST)
	public String createShot(@PathVariable String eventId,
			@ModelAttribute("form") @Valid ShotForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/newShot";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final EventEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create shooter
		final String shooterId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry shooter = event.getAttendee(form.getPlayerId());
		final PlayParticipantDTO shooterDTO = new PlayParticipantDTO(shooterId,
				playId, shooter, teamSeason, PlayRole.SHOOTER, false, now,
				user, now, user);
		participants.add(shooterDTO);

		// Create shot
		final PlayDTO dto = new PlayDTO(playId, PlayType.SHOT, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null,
				form.getAttemptType(), form.getResult(), form.getComment(),
				now, user, now, user, participants);

		final RecordShotCommand payload = new RecordShotCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/shots";
	}

	/*---------- Ground Ball actions ----------*/

	@RequestMapping(value = "events/{eventId}/groundBalls", method = RequestMethod.GET)
	public String groundBallIndex(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.GROUND_BALL,
				aggregate, aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.GROUND_BALL,
				aggregate, aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/groundBallIndex";
	}

	@RequestMapping(value = "events/{eventId}/groundBalls/new", method = RequestMethod.GET)
	public String newGroundBall(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final GroundBallForm form = new GroundBallForm();
		form.setTeams(getTeams(aggregate));
		model.addAttribute("form", form);
		return "events/newGroundBall";
	}

	@RequestMapping(value = "events/{eventId}/groundBalls", method = RequestMethod.POST)
	public String createGroundBall(@PathVariable String eventId,
			@ModelAttribute("form") @Valid GroundBallForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/newGroundBall";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final EventEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create player
		final String playerId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry player = event.getAttendee(form.getPlayerId());
		final PlayParticipantDTO shooterDTO = new PlayParticipantDTO(playerId,
				playId, player, teamSeason, PlayRole.GROUND_BALL, false, now,
				user, now, user);
		participants.add(shooterDTO);

		// Create ground ball play
		final PlayDTO dto = new PlayDTO(playId, PlayType.GROUND_BALL,
				PlayKey.PLAY, event, teamSeason, form.getPeriod(), null, null,
				null, form.getComment(), now, user, now, user, participants);

		final RecordGroundBallCommand payload = new RecordGroundBallCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/groundBalls";
	}

	/*---------- Clear actions ----------*/

	@RequestMapping(value = "events/{eventId}/clears", method = RequestMethod.GET)
	public String clearIndex(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.CLEAR,
				aggregate, aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.CLEAR,
				aggregate, aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/clearIndex";
	}

	@RequestMapping(value = "events/{eventId}/clears/new", method = RequestMethod.GET)
	public String newClear(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final ClearForm form = new ClearForm();
		form.setTeams(getTeams(aggregate));
		model.addAttribute("form", form);
		return "events/newClear";
	}

	@RequestMapping(value = "events/{eventId}/clears", method = RequestMethod.POST)
	public String createClear(@PathVariable String eventId,
			@ModelAttribute("form") @Valid ClearForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/newClear";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final EventEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create clear
		final PlayDTO dto = new PlayDTO(playId, PlayType.CLEAR, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null, null,
				form.getResult(), form.getComment(), now, user, now, user,
				participants);

		final RecordClearCommand payload = new RecordClearCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/clears";
	}

	/*---------- Faceoff actions ----------*/

	@RequestMapping(value = "events/{eventId}/faceOffs", method = RequestMethod.GET)
	public String faceOffIndex(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final List<FaceOffEntry> faceOffs = getFaceoffs(aggregate);
		model.addAttribute("event", aggregate);
		model.addAttribute("faceOffs", faceOffs);
		return "events/faceOffIndex";
	}

	@RequestMapping(value = "events/{eventId}/faceOffs/new", method = RequestMethod.GET)
	public String newFaceOff(@PathVariable String eventId, Model model) {
		final EventEntry aggregate = eventRepository.findOne(eventId);
		final FaceOffForm form = new FaceOffForm();
		form.setTeams(getTeams(aggregate));
		model.addAttribute("form", form);
		return "events/newFaceOff";
	}

	@RequestMapping(value = "events/{eventId}/faceOffs", method = RequestMethod.POST)
	public String createFaceOff(@PathVariable String eventId,
			@ModelAttribute("form") @Valid FaceOffForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/newFaceOff";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final EventEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create winner
		final String winnerId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry winner = event.getAttendee(form.getWinnerId());
		final PlayParticipantDTO winnerDTO = new PlayParticipantDTO(winnerId,
				playId, winner, winner.getTeamSeason(),
				PlayRole.FACEOFF_WINNER, true, now, user, now, user);
		participants.add(winnerDTO);

		// Create loser
		final String loserId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry loser = event.getAttendee(form.getLoserId());
		final PlayParticipantDTO loserDTO = new PlayParticipantDTO(loserId,
				playId, loser, loser.getTeamSeason(), PlayRole.FACEOFF_LOSER,
				true, now, user, now, user);
		participants.add(loserDTO);

		// Create faceoff
		final PlayDTO dto = new PlayDTO(playId, PlayType.FACEOFF, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null, null, null,
				form.getComment(), now, user, now, user, participants);

		final RecordGoalCommand payload = new RecordGoalCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/faceOffs";
	}

	/*---------- Methods ----------*/

	private Map<String, String> getTeams(EventEntry event) {
		final Map<String, String> result = new HashMap<>();
		for (final TeamEvent each : event.getTeams()) {
			result.put(each.getTeamSeason().getId(), each.getTeamSeason()
					.getTeam().getName());
		}
		return result;
	}

	private List<FaceOffEntry> getFaceoffs(EventEntry event) {
		final List<FaceOffEntry> list = new ArrayList<>();
		for (final PlayEntry each : event.getPlays()) {
			if (each instanceof FaceOffEntry) {
				list.add((FaceOffEntry) each);
			}
		}
		Collections.sort(list, new PlayComparator());
		return list;
	}

	private List<PlayEntry> getPlays(String type, EventEntry event,
			String teamSeasonId) {
		final List<PlayEntry> list = new ArrayList<>();
		for (final PlayEntry each : event.getPlays()) {
			if (each.getTeam().getId().equals(teamSeasonId)) {
				switch (type) {
				case PlayType.CLEAR:
					if (each instanceof ClearEntry) {
						list.add(each);
					}
					break;
				case PlayType.FACEOFF:
					if (each instanceof FaceOffEntry) {
						list.add(each);
					}
					break;
				case PlayType.GOAL:
					if (each instanceof GoalEntry) {
						list.add(each);
					}
					break;
				case PlayType.GROUND_BALL:
					if (each instanceof GroundBallEntry) {
						list.add(each);
					}
					break;
				case PlayType.SHOT:
					if (each instanceof ShotEntry) {
						list.add(each);
					}
					break;
				}
			}
		}
		if (type.equals(PlayType.GOAL)) {
			Collections.sort(list, new GoalComparator());
		} else {
			Collections.sort(list, new PlayComparator());
		}
		return list;
	}

	private static class GoalComparator implements Comparator<PlayEntry> {
		@Override
		public int compare(PlayEntry o1, PlayEntry o2) {
			final LocalTime t1 = PlayUtils.getTotalElapsedTime(o1.getPeriod(),
					o1.getElapsedTime());
			final LocalTime t2 = PlayUtils.getTotalElapsedTime(o2.getPeriod(),
					o2.getElapsedTime());
			return t1.isBefore(t2) ? -1 : (t1.isAfter(t2) ? 1 : 0);
		}
	}

	private static class PlayComparator implements Comparator<PlayEntry> {
		@Override
		public int compare(PlayEntry o1, PlayEntry o2) {
			final int p1 = o1.getPeriod();
			final int p2 = o2.getPeriod();
			return p1 < p2 ? -1 : (p1 > p2 ? 1 : 0);
		}

	}
}
