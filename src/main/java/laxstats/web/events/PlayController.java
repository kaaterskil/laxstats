package laxstats.web.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.events.DeleteClearCommand;
import laxstats.api.events.DeleteFaceOffCommand;
import laxstats.api.events.DeleteGoalCommand;
import laxstats.api.events.DeleteGroundBallCommand;
import laxstats.api.events.DeletePenaltyCommand;
import laxstats.api.events.DeleteShotCommand;
import laxstats.api.events.EventId;
import laxstats.api.events.PlayDTO;
import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayParticipantDTO;
import laxstats.api.events.PlayResult;
import laxstats.api.events.PlayRole;
import laxstats.api.events.PlayType;
import laxstats.api.events.PlayUtils;
import laxstats.api.events.RecordClearCommand;
import laxstats.api.events.RecordFaceoffCommand;
import laxstats.api.events.RecordGoalCommand;
import laxstats.api.events.RecordGroundBallCommand;
import laxstats.api.events.RecordPenaltyCommand;
import laxstats.api.events.RecordShotCommand;
import laxstats.api.events.ScoreAttemptType;
import laxstats.api.events.UpdateClearCommand;
import laxstats.api.events.UpdateFaceOffCommand;
import laxstats.api.events.UpdateGoalCommand;
import laxstats.api.events.UpdateGroundBallCommand;
import laxstats.api.events.UpdatePenaltyCommand;
import laxstats.api.events.UpdateShotCommand;
import laxstats.api.violations.PenaltyCategory;
import laxstats.query.events.AttendeeEntry;
import laxstats.query.events.ClearEntry;
import laxstats.query.events.GameEntry;
import laxstats.query.events.GameQueryRepository;
import laxstats.query.events.FaceOffEntry;
import laxstats.query.events.GoalEntry;
import laxstats.query.events.GroundBallEntry;
import laxstats.query.events.PenaltyEntry;
import laxstats.query.events.PlayEntry;
import laxstats.query.events.PlayParticipantEntry;
import laxstats.query.events.ShotEntry;
import laxstats.query.events.TeamEvent;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;
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
	private final GameQueryRepository eventRepository;
	private final TeamSeasonQueryRepository teamRepository;
	private final ViolationQueryRepository violationRepository;
	private Map<String, String> violations;

	@Autowired
	public PlayController(UserQueryRepository userRepository,
			CommandBus commandBus, GameQueryRepository eventRepository,
			TeamSeasonQueryRepository teamRepository,
			ViolationQueryRepository violationRepository) {
		super(userRepository, commandBus);
		this.eventRepository = eventRepository;
		this.teamRepository = teamRepository;
		this.violationRepository = violationRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ClearFormValidator());
		binder.setValidator(new FaceOffFormValidator());
		binder.setValidator(new GoalFormValidator());
		binder.setValidator(new GroundBallFormValidator());
		binder.setValidator(new PenaltyFormValidator());
		binder.setValidator(new ShotFormValidator());
	}

	/*---------- Clear actions ----------*/

	@RequestMapping(value = "/events/{eventId}/clears", method = RequestMethod.GET)
	public String clearIndex(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.CLEAR,
				aggregate, aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.CLEAR,
				aggregate, aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/clears/index";
	}

	@RequestMapping(value = "/events/{eventId}/clears/new", method = RequestMethod.GET)
	public String newClear(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);

		final ClearForm form = new ClearForm();
		form.setTeams(getTeams(aggregate));
		form.setResults(getClearResults());

		model.addAttribute("clearForm", form);
		model.addAttribute("event", aggregate);
		return "events/clears/newClear";
	}

	@RequestMapping(value = "/events/{eventId}/clears", method = RequestMethod.POST)
	public String createClear(@PathVariable String eventId,
			@ModelAttribute("clearForm") @Valid ClearForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/clears/newClear";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final GameEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create clear
		final PlayDTO dto = new PlayDTO(playId, PlayType.CLEAR, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null, null,
				form.getResult(), null, 0, form.getComment(), now, user, now,
				user, participants);

		final RecordClearCommand payload = new RecordClearCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/clears";
	}

	@RequestMapping(value = "/events/{eventId}/clears/{playId}", method = RequestMethod.DELETE)
	public String deleteClear(@PathVariable String eventId,
			@PathVariable String playId) {
		final EventId identifier = new EventId(eventId);
		final DeleteClearCommand payload = new DeleteClearCommand(identifier,
				playId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/clears";
	}

	@RequestMapping(value = "/events/{eventId}/clears/{playId}", method = RequestMethod.PUT)
	public String updateClear(@PathVariable String eventId,
			@PathVariable String playId,
			@ModelAttribute("clearForm") @Valid ClearForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/clears/editClear";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final GameEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Edit clear
		final PlayDTO dto = new PlayDTO(playId, PlayType.CLEAR, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null, null,
				form.getResult(), null, 0, form.getComment(), now, user,
				participants);

		final UpdateClearCommand payload = new UpdateClearCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/clears";
	}

	@RequestMapping(value = "/events/{eventId}/clears/{playId}/edit", method = RequestMethod.GET)
	public String editClear(@PathVariable String eventId,
			@PathVariable String playId, Model model) {
		final Map<String, Object> attributes = new HashMap<>();

		final GameEntry aggregate = eventRepository.findOne(eventId);
		attributes.put("event", aggregate);

		final PlayEntry play = aggregate.getPlays().get(playId);
		attributes.put("play", play);

		final ClearForm form = new ClearForm();
		form.setTeamSeasonId(play.getTeam().getId());
		form.setPeriod(play.getPeriod());
		form.setResult(play.getResult());
		form.setComment(play.getComment());
		form.setTeams(getTeams(aggregate));
		attributes.put("clearForm", form);

		model.addAllAttributes(attributes);
		return "events/clears/editClear";
	}

	/*---------- Faceoff actions ----------*/

	@RequestMapping(value = "/events/{eventId}/faceOffs", method = RequestMethod.GET)
	public String faceOffIndex(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);
		final List<FaceOffEntry> faceOffs = getFaceoffs(aggregate);
		model.addAttribute("event", aggregate);
		model.addAttribute("faceOffs", faceOffs);
		return "events/faceOffs/index";
	}

	@RequestMapping(value = "/events/{eventId}/faceOffs/new", method = RequestMethod.GET)
	public String newFaceOff(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);

		final FaceOffForm form = new FaceOffForm();
		form.setAttendees(aggregate.getAttendees());

		model.addAttribute("faceOffForm", form);
		model.addAttribute("event", aggregate);
		return "events/faceOffs/newFaceOff";
	}

	@RequestMapping(value = "/events/{eventId}/faceOffs", method = RequestMethod.POST)
	public String createFaceOff(@PathVariable String eventId,
			@ModelAttribute("faceOffForm") @Valid FaceOffForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/faceOffs/newFaceOff";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final GameEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create winner
		final String winnerId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry winner = event.getAttendee(form.getWinnerId());
		final PlayParticipantDTO winnerDTO = new PlayParticipantDTO(winnerId,
				playId, winner, winner.getTeamSeason(),
				PlayRole.FACEOFF_WINNER, false, now, user, now, user);
		participants.add(winnerDTO);

		// Create loser
		final String loserId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry loser = event.getAttendee(form.getLoserId());
		final PlayParticipantDTO loserDTO = new PlayParticipantDTO(loserId,
				playId, loser, loser.getTeamSeason(), PlayRole.FACEOFF_LOSER,
				false, now, user, now, user);
		participants.add(loserDTO);

		// Create faceoff
		final PlayDTO dto = new PlayDTO(playId, PlayType.FACEOFF, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null, null, null, null, 0,
				form.getComment(), now, user, now, user, participants);

		final RecordFaceoffCommand payload = new RecordFaceoffCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/faceOffs";
	}

	@RequestMapping(value = "/events/{eventId}/faceOffs/{playId}", method = RequestMethod.DELETE)
	public String deleteFaceOff(@PathVariable String eventId,
			@PathVariable String playId) {
		final EventId identifier = new EventId(eventId);
		final DeleteFaceOffCommand payload = new DeleteFaceOffCommand(
				identifier, playId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/faceOffs";
	}

	@RequestMapping(value = "/events/{eventId}/faceOffs/{playId}", method = RequestMethod.PUT)
	public String updateFaceOff(@PathVariable String eventId,
			@PathVariable String playId,
			@ModelAttribute("form") @Valid FaceOffForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/faceOffs/editFaceOff";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();

		final GameEntry event = eventRepository.findOne(eventId);
		final PlayEntry play = event.getPlays().get(playId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());

		// Edit players
		final List<PlayParticipantDTO> participants = new ArrayList<>();
		for (final PlayParticipantEntry participant : play.getParticipants()) {
			AttendeeEntry attendee = participant.getAttendee();
			PlayRole role = null;
			if (participant.getRole().equals(PlayRole.FACEOFF_WINNER)) {
				role = PlayRole.FACEOFF_WINNER;
				if (!form.getWinnerId().equals(attendee.getId())) {
					attendee = event.getAttendee(form.getWinnerId());
				}
			} else if (participant.getRole().equals(PlayRole.FACEOFF_LOSER)) {
				role = PlayRole.FACEOFF_LOSER;
				if (!form.getLoserId().equals(attendee.getId())) {
					attendee = event.getAttendee(form.getLoserId());
				}
			}
			final PlayParticipantDTO participantDTO = new PlayParticipantDTO(
					participant.getId(), playId, attendee,
					attendee.getTeamSeason(), role, false, now, user);
			participants.add(participantDTO);

		}
		// Edit faceoff play
		final PlayDTO dto = new PlayDTO(playId, PlayType.FACEOFF, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null, null, null, null, 0,
				form.getComment(), now, user, participants);

		final UpdateFaceOffCommand payload = new UpdateFaceOffCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/faceOffs";
	}

	@RequestMapping(value = "/events/{eventId}/faceOffs/{playId}/edit", method = RequestMethod.GET)
	public String editFaceOff(@PathVariable String eventId,
			@PathVariable String playId, Model model) {
		final Map<String, Object> attributes = new HashMap<>();

		final GameEntry aggregate = eventRepository.findOne(eventId);
		attributes.put("event", aggregate);

		final FaceOffEntry play = (FaceOffEntry) aggregate.getPlays().get(
				playId);
		attributes.put("play", play);

		final FaceOffForm form = new FaceOffForm();
		form.setTeamSeasonId(play.getTeam().getId());
		form.setPeriod(play.getPeriod());
		form.setElapsedTime(play.getElapsedTime());
		for (final PlayParticipantEntry player : play.getParticipants()) {
			if (player.getRole().equals(PlayRole.FACEOFF_LOSER)) {
				form.setLoserId(player.getAttendee().getId());
			} else if (player.getRole().equals(PlayRole.FACEOFF_WINNER)) {
				form.setWinnerId(player.getAttendee().getId());
			}
		}
		form.setComment(play.getComment());
		form.setAttendees(aggregate.getAttendees());
		attributes.put("faceOffForm", form);

		model.addAllAttributes(attributes);
		return "events/faceOffs/editFaceOff";
	}

	/*---------- Goal actions ----------*/

	@RequestMapping(value = "/events/{eventId}/goals", method = RequestMethod.GET)
	public String goalIndex(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.GOAL, aggregate,
				aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.GOAL, aggregate,
				aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/goals/index";
	}

	@RequestMapping(value = "/events/{eventId}/goals/new", method = RequestMethod.GET)
	public String newGoal(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);

		final GoalForm form = new GoalForm();
		form.setAttemptType(ScoreAttemptType.REGULAR);
		form.setAttendees(aggregate.getAttendees());

		model.addAttribute("goalForm", form);
		model.addAttribute("event", aggregate);
		return "events/goals/newGoal";
	}

	@RequestMapping(value = "/events/{eventId}/goals", method = RequestMethod.POST)
	public String createGoal(@PathVariable String eventId,
			@ModelAttribute("goalForm") @Valid GoalForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/goals/newGoal";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final GameEntry event = eventRepository.findOne(eventId);
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
				form.getAttemptType(), PlayResult.GOAL, null, 0,
				form.getComment(), now, user, now, user, participants);

		final RecordGoalCommand payload = new RecordGoalCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/goals";
	}

	@RequestMapping(value = "/events/{eventId}/goals/{playId}", method = RequestMethod.DELETE)
	public String deleteGoal(@PathVariable String eventId,
			@PathVariable String playId) {
		final EventId identifier = new EventId(eventId);
		final DeleteGoalCommand payload = new DeleteGoalCommand(identifier,
				playId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/goals";
	}

	@RequestMapping(value = "/events/{eventId}/goals/{playId}", method = RequestMethod.PUT)
	public String updateGoal(@PathVariable String eventId,
			@PathVariable String playId,
			@ModelAttribute("form") @Valid GoalForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/goals/editGoal";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();

		final GameEntry event = eventRepository.findOne(eventId);
		final PlayEntry play = event.getPlays().get(playId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Edit scorer
		for (final PlayParticipantEntry participant : play.getParticipants()) {
			if (participant.getRole().equals(PlayRole.SCORER)) {
				AttendeeEntry scorer = participant.getAttendee();
				if (!form.getScorerId().equals(scorer.getId())) {
					scorer = event.getAttendee(form.getScorerId());
				}
				final PlayParticipantDTO scorerDTO = new PlayParticipantDTO(
						participant.getId(), playId, scorer, teamSeason,
						PlayRole.SCORER, true, now, user);
				participants.add(scorerDTO);
			}
		}

		// Edit assist
		// Edit assist -> null
		// Edit assist -> new assist
		boolean foundAssist = false;
		AttendeeEntry assist = null;
		PlayParticipantDTO assistDTO = null;
		for (final PlayParticipantEntry participant : play.getParticipants()) {
			if (participant.getRole().equals(PlayRole.ASSIST)) {
				foundAssist = true;
				if (form.getAssistId() == null) {
					// We'll take care of deleted assists in the model.
				} else {
					assist = participant.getAttendee();
					if (!form.getAssistId().equals(assist.getId())) {
						assist = event.getAttendee(form.getAssistId());
					}
					assistDTO = new PlayParticipantDTO(participant.getId(),
							playId, assist, teamSeason, PlayRole.ASSIST, true,
							now, user);
					participants.add(assistDTO);
				}
			}
		}
		// Edit null -> assist
		if (!foundAssist && form.getAssistId() != null) {
			final String id = IdentifierFactory.getInstance()
					.generateIdentifier();
			assist = event.getAttendee(form.getAssistId());
			assistDTO = new PlayParticipantDTO(id, playId, assist, teamSeason,
					PlayRole.ASSIST, true, now, user);
			participants.add(assistDTO);
		}

		// Create goal
		final PlayDTO dto = new PlayDTO(playId, PlayType.GOAL, PlayKey.GOAL,
				event, teamSeason, form.getPeriod(), form.getElapsedTime(),
				form.getAttemptType(), PlayResult.GOAL, null, 0,
				form.getComment(), now, user, participants);

		final UpdateGoalCommand payload = new UpdateGoalCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/goals";
	}

	@RequestMapping(value = "/events/{eventId}/goals/{playId}/edit", method = RequestMethod.GET)
	public String editGoal(@PathVariable String eventId,
			@PathVariable String playId, Model model) {
		final Map<String, Object> attributes = new HashMap<>();

		final GameEntry aggregate = eventRepository.findOne(eventId);
		attributes.put("event", aggregate);

		final GoalEntry play = (GoalEntry) aggregate.getPlays().get(playId);
		attributes.put("play", play);

		final GoalForm form = new GoalForm();
		form.setTeamSeasonId(play.getTeam().getId());
		form.setPeriod(play.getPeriod());
		form.setElapsedTime(play.getElapsedTime());
		for (final PlayParticipantEntry player : play.getParticipants()) {
			if (player.getRole().equals(PlayRole.SCORER)) {
				form.setScorerId(player.getAttendee().getId());
			} else if (player.getRole().equals(PlayRole.ASSIST)) {
				form.setAssistId(player.getAttendee().getId());
			}
		}
		form.setAttemptType(play.getScoreAttemptType());
		form.setComment(play.getComment());
		form.setAttendees(aggregate.getAttendees());
		attributes.put("goalForm", form);

		model.addAllAttributes(attributes);
		return "events/goals/editGoal";
	}

	/*---------- Ground Ball actions ----------*/

	@RequestMapping(value = "/events/{eventId}/groundBalls", method = RequestMethod.GET)
	public String groundBallIndex(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.GROUND_BALL,
				aggregate, aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.GROUND_BALL,
				aggregate, aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/groundBalls/index";
	}

	@RequestMapping(value = "/events/{eventId}/groundBalls/new", method = RequestMethod.GET)
	public String newGroundBall(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);

		final GroundBallForm form = new GroundBallForm();
		form.setAttendees(aggregate.getAttendees());

		model.addAttribute("groundBallForm", form);
		model.addAttribute("event", aggregate);
		return "events/groundBalls/newGroundBall";
	}

	@RequestMapping(value = "/events/{eventId}/groundBalls", method = RequestMethod.POST)
	public String createGroundBall(@PathVariable String eventId,
			@ModelAttribute("groundBallForm") @Valid GroundBallForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/groundBalls/newGroundBall";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final GameEntry event = eventRepository.findOne(eventId);
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
				null, null, 0, form.getComment(), now, user, now, user,
				participants);

		final RecordGroundBallCommand payload = new RecordGroundBallCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/groundBalls";
	}

	@RequestMapping(value = "/events/{eventId}/groundBalls/{playId}", method = RequestMethod.DELETE)
	public String deleteGroundBall(@PathVariable String eventId,
			@PathVariable String playId) {
		final EventId identifier = new EventId(eventId);
		final DeleteGroundBallCommand payload = new DeleteGroundBallCommand(
				identifier, playId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/groundBalls";
	}

	@RequestMapping(value = "/events/{eventId}/groundBalls/{playId}", method = RequestMethod.PUT)
	public String updateGroundBall(@PathVariable String eventId,
			@PathVariable String playId,
			@ModelAttribute("form") @Valid GroundBallForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/groundBalls/editGroundBall";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();

		final GameEntry event = eventRepository.findOne(eventId);
		final PlayEntry play = event.getPlays().get(playId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Edit player
		final PlayParticipantEntry participant = play.getParticipants().get(0);
		AttendeeEntry attendee = participant.getAttendee();
		if (!form.getPlayerId().equals(attendee.getId())) {
			attendee = event.getAttendee(form.getPlayerId());
		}
		final PlayParticipantDTO participantDTO = new PlayParticipantDTO(
				participant.getId(), playId, attendee, teamSeason,
				PlayRole.GROUND_BALL, false, now, user);
		participants.add(participantDTO);

		// Edit ground ball play
		final PlayDTO dto = new PlayDTO(playId, PlayType.GROUND_BALL,
				PlayKey.PLAY, event, teamSeason, form.getPeriod(), null, null,
				null, null, 0, form.getComment(), now, user, participants);

		final UpdateGroundBallCommand payload = new UpdateGroundBallCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/groundBalls";
	}

	@RequestMapping(value = "/events/{eventId}/groundBalls/{playId}/edit", method = RequestMethod.GET)
	public String editGroundBall(@PathVariable String eventId,
			@PathVariable String playId, Model model) {
		final Map<String, Object> attributes = new HashMap<>();

		final GameEntry aggregate = eventRepository.findOne(eventId);
		attributes.put("event", aggregate);

		final GroundBallEntry play = (GroundBallEntry) aggregate.getPlays()
				.get(playId);
		attributes.put("play", play);

		final GroundBallForm form = new GroundBallForm();
		form.setTeamSeasonId(play.getTeam().getId());
		form.setPeriod(play.getPeriod());
		for (final PlayParticipantEntry player : play.getParticipants()) {
			form.setPlayerId(player.getAttendee().getId());
		}
		form.setComment(play.getComment());
		form.setAttendees(aggregate.getAttendees());
		attributes.put("groundBallForm", form);

		model.addAllAttributes(attributes);
		return "events/groundBalls/editGoal";
	}

	/*---------- Penalty actions ----------*/

	@RequestMapping(value = "/events/{eventId}/penalties", method = RequestMethod.GET)
	public String penaltyIndex(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.PENALTY,
				aggregate, aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.PENALTY,
				aggregate, aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/penalties/index";
	}

	@RequestMapping(value = "/events/{eventId}/penalties/new", method = RequestMethod.GET)
	public String newPenalty(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);

		final PenaltyForm form = new PenaltyForm();
		form.setTeams(getTeams(aggregate));
		form.setAttendees(aggregate.getAttendees());
		form.setViolationData(getViolations());

		model.addAttribute("penaltyForm", form);
		model.addAttribute("event", aggregate);
		return "events/penalties/newPenalty";
	}

	@RequestMapping(value = "/events/{eventId}/penalties", method = RequestMethod.POST)
	public String createPenalty(@PathVariable String eventId,
			@ModelAttribute("penaltyForm") @Valid PenaltyForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/penalties/newPenalty";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final GameEntry event = eventRepository.findOne(eventId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Create violator
		final String violatorId = IdentifierFactory.getInstance()
				.generateIdentifier();
		final AttendeeEntry violator = event.getAttendee(form
				.getCommittedById());
		final PlayParticipantDTO violatorDTO = new PlayParticipantDTO(
				violatorId, playId, violator, teamSeason,
				PlayRole.PENALTY_COMMITTED_BY, false, now, user, now, user);
		participants.add(violatorDTO);

		// Create against
		final AttendeeEntry against = event.getAttendee(form
				.getCommittedAgainstId());
		if (against != null) {
			final String againstId = IdentifierFactory.getInstance()
					.generateIdentifier();
			final PlayParticipantDTO againstDTO = new PlayParticipantDTO(
					againstId, playId, against, teamSeason,
					PlayRole.PENALTY_COMMITTED_AGAINST, false, now, user, now,
					user);
			participants.add(againstDTO);
		}

		// Create penalty
		final ViolationEntry violation = violationRepository.findOne(form
				.getViolationId());
		final PlayDTO dto = new PlayDTO(playId, PlayType.PENALTY, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), form.getElapsedTime(),
				null, null, violation, form.getDuration(), form.getComment(),
				now, user, now, user, participants);

		final RecordPenaltyCommand payload = new RecordPenaltyCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/penalties";
	}

	@RequestMapping(value = "/events/{eventId}/penalties/{playId}", method = RequestMethod.PUT)
	public String updatePenalty(@PathVariable String eventId,
			@PathVariable String playId,
			@ModelAttribute("penaltyForm") @Valid PenaltyForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "events/penalties/editPenalty";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();

		final GameEntry event = eventRepository.findOne(eventId);
		final PenaltyEntry play = (PenaltyEntry) event.getPlays().get(playId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Edit violator
		final PlayParticipantEntry violator = play.getCommittedBy();
		AttendeeEntry attendee = violator.getAttendee();
		if (!form.getCommittedById().equals(attendee.getId())) {
			attendee = event.getAttendee(form.getCommittedById());
		}
		final PlayParticipantDTO violatorDTO = new PlayParticipantDTO(
				violator.getId(), playId, attendee, teamSeason,
				PlayRole.PENALTY_COMMITTED_BY, false, now, user, now, user);
		participants.add(violatorDTO);

		// Edit/add against
		boolean foundAgainst = false;
		AttendeeEntry against = null;
		PlayParticipantDTO againstDTO = null;
		for (final PlayParticipantEntry participant : play.getParticipants()) {
			final PlayRole role = participant.getRole();
			if (role.equals(PlayRole.PENALTY_COMMITTED_AGAINST)) {
				foundAgainst = true;
				if (form.getCommittedAgainstId() == null) {
					// Handle this in the model
				} else {
					against = participant.getAttendee();
					if (!form.getCommittedAgainstId().equals(against.getId())) {
						against = event.getAttendee(form
								.getCommittedAgainstId());
					}
					againstDTO = new PlayParticipantDTO(participant.getId(),
							playId, against, teamSeason,
							PlayRole.PENALTY_COMMITTED_AGAINST, false, now,
							user);
					participants.add(againstDTO);
				}
			}
		}
		if (!foundAgainst && form.getCommittedAgainstId() != null) {
			final String id = IdentifierFactory.getInstance()
					.generateIdentifier();
			against = event.getAttendee(form.getCommittedAgainstId());
			againstDTO = new PlayParticipantDTO(id, playId, against,
					teamSeason, PlayRole.PENALTY_COMMITTED_AGAINST, false, now,
					user);
			participants.add(againstDTO);
		}

		// Edit penalty
		final ViolationEntry violation = violationRepository.findOne(form
				.getViolationId());
		final PlayDTO dto = new PlayDTO(playId, PlayType.PENALTY, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), form.getElapsedTime(),
				null, null, violation, form.getDuration(), form.getComment(),
				now, user, participants);

		final UpdatePenaltyCommand payload = new UpdatePenaltyCommand(
				new EventId(eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/penalties";
	}

	@RequestMapping(value = "/events/{eventId}/penalties/{playId}", method = RequestMethod.DELETE)
	public String deletePenalty(@PathVariable String eventId,
			@PathVariable String playId) {
		final EventId identifier = new EventId(eventId);
		final DeletePenaltyCommand payload = new DeletePenaltyCommand(
				identifier, playId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/penalties";
	}

	@RequestMapping(value = "/events/{eventId}/penalties/{playId}/edit", method = RequestMethod.GET)
	public String editPenalty(@PathVariable String eventId,
			@PathVariable String playId, Model model) {
		final Map<String, Object> attributes = new HashMap<>();

		final GameEntry aggregate = eventRepository.findOne(eventId);
		attributes.put("event", aggregate);

		final PenaltyEntry play = (PenaltyEntry) aggregate.getPlays().get(
				playId);
		attributes.put("play", play);

		final PenaltyForm form = new PenaltyForm();
		form.setPeriod(play.getPeriod());
		form.setElapsedTime(play.getElapsedTime());
		form.setViolationId(play.getViolation().getId());
		for (final PlayParticipantEntry player : play.getParticipants()) {
			final PlayRole role = player.getRole();
			if (role.equals(PlayRole.PENALTY_COMMITTED_BY)) {
				form.setCommittedById(player.getAttendee().getId());
			} else if (role.equals(PlayRole.PENALTY_COMMITTED_AGAINST)) {
				form.setCommittedAgainstId(player.getAttendee().getId());
			}
		}
		form.setDuration(play.getDuration());
		form.setComment(play.getComment());
		form.setAttendees(aggregate.getAttendees());
		form.setViolationData(getViolations());
		attributes.put("penaltyForm", form);

		model.addAllAttributes(attributes);
		return "events/penalties/editPenalty";
	}

	/*---------- Shot actions ----------*/

	@RequestMapping(value = "/events/{eventId}/shots", method = RequestMethod.GET)
	public String shotIndex(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);
		final List<PlayEntry> teamOnePlays = getPlays(PlayType.SHOT, aggregate,
				aggregate.getTeams().get(0).getId());
		final List<PlayEntry> teamTwoPlays = getPlays(PlayType.SHOT, aggregate,
				aggregate.getTeams().get(1).getId());
		model.addAttribute("event", aggregate);
		model.addAttribute("teamOnePlays", teamOnePlays);
		model.addAttribute("teamTwoPlays", teamTwoPlays);
		return "events/shots/index";
	}

	@RequestMapping(value = "/events/{eventId}/shots/new", method = RequestMethod.GET)
	public String newShot(@PathVariable String eventId, Model model) {
		final GameEntry aggregate = eventRepository.findOne(eventId);

		final ShotForm form = new ShotForm();
		form.setAttendees(aggregate.getAttendees());
		form.setResults(getShotResults());

		model.addAttribute("shotForm", form);
		model.addAttribute("event", aggregate);
		return "events/shots/newShot";
	}

	@RequestMapping(value = "/events/{eventId}/shots", method = RequestMethod.POST)
	public String createShot(@PathVariable String eventId,
			@ModelAttribute("form") @Valid ShotForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/shots/newShot";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final String playId = IdentifierFactory.getInstance()
				.generateIdentifier();

		final GameEntry event = eventRepository.findOne(eventId);
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
				form.getAttemptType(), form.getResult(), null, 0,
				form.getComment(), now, user, now, user, participants);

		final RecordShotCommand payload = new RecordShotCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/shots";
	}

	@RequestMapping(value = "/events/{eventId}/shots/{playId}", method = RequestMethod.PUT)
	public String updateShot(@PathVariable String eventId,
			@PathVariable String playId,
			@ModelAttribute("form") @Valid ShotForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "events/shots/editShot";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();

		final GameEntry event = eventRepository.findOne(eventId);
		final PlayEntry play = event.getPlays().get(playId);
		final TeamSeasonEntry teamSeason = teamRepository.findOne(form
				.getTeamSeasonId());
		final List<PlayParticipantDTO> participants = new ArrayList<>();

		// Edit shooter
		final PlayParticipantEntry participant = play.getParticipants().get(0);
		AttendeeEntry attendee = participant.getAttendee();
		if (!form.getPlayerId().equals(attendee.getId())) {
			attendee = event.getAttendee(form.getPlayerId());
		}
		final PlayParticipantDTO shooterDTO = new PlayParticipantDTO(
				participant.getId(), playId, attendee, teamSeason,
				PlayRole.SHOOTER, false, now, user);
		participants.add(shooterDTO);

		// Edit shot
		final PlayDTO dto = new PlayDTO(playId, PlayType.SHOT, PlayKey.PLAY,
				event, teamSeason, form.getPeriod(), null,
				form.getAttemptType(), form.getResult(), null, 0,
				form.getComment(), now, user, participants);

		final UpdateShotCommand payload = new UpdateShotCommand(new EventId(
				eventId), playId, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/shots";
	}

	@RequestMapping(value = "/events/{eventId}/shots/{playId}", method = RequestMethod.DELETE)
	public String deleteShot(@PathVariable String eventId,
			@PathVariable String playId) {
		final EventId identifier = new EventId(eventId);
		final DeleteShotCommand payload = new DeleteShotCommand(identifier,
				playId);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/events/" + eventId + "/shots";
	}

	@RequestMapping(value = "/events/{eventId}/shots/{playId}/edit", method = RequestMethod.GET)
	public String editShot(@PathVariable String eventId,
			@PathVariable String playId, Model model) {
		final Map<String, Object> attributes = new HashMap<>();

		final GameEntry aggregate = eventRepository.findOne(eventId);
		attributes.put("event", aggregate);

		final ShotEntry play = (ShotEntry) aggregate.getPlays().get(playId);
		attributes.put("play", play);

		final ShotForm form = new ShotForm();
		form.setTeamSeasonId(play.getTeam().getId());
		form.setPeriod(play.getPeriod());
		for (final PlayParticipantEntry player : play.getParticipants()) {
			form.setPlayerId(player.getAttendee().getId());
		}
		form.setAttemptType(play.getScoreAttemptType());
		form.setResult(play.getResult());
		form.setComment(play.getComment());
		form.setAttendees(aggregate.getAttendees());
		attributes.put("shotForm", form);

		model.addAllAttributes(attributes);
		return "events/shots/editShot";
	}

	/*---------- Methods ----------*/

	private Map<String, String> getTeams(GameEntry event) {
		final Map<String, String> result = new HashMap<>();
		for (final TeamEvent each : event.getTeams()) {
			result.put(each.getTeamSeason().getId(), each.getTeamSeason()
					.getTeam().getName());
		}
		return result;
	}

	private List<FaceOffEntry> getFaceoffs(GameEntry event) {
		final List<FaceOffEntry> list = new ArrayList<>();
		for (final PlayEntry each : event.getPlays().values()) {
			if (each instanceof FaceOffEntry) {
				list.add((FaceOffEntry) each);
			}
		}
		Collections.sort(list, new PlayComparator());
		return list;
	}

	private Map<String, String> getViolations() {
		if (violations == null) {
			final Map<String, String> result = new HashMap<>();
			final List<ViolationEntry> list = (List<ViolationEntry>) violationRepository
					.findAll();
			Collections.sort(list, new ViolationComparator());
			for (final ViolationEntry each : list) {
				result.put(each.getId(), each.getName());
			}
			violations = result;
		}
		return violations;
	}

	private List<PlayResult> getClearResults() {
		final List<PlayResult> list = new ArrayList<>();
		final PlayResult[] values = PlayResult.values();
		for (int i = 0; i < values.length; i++) {
			final PlayResult each = values[i];
			if (each.equals(PlayResult.CLEAR_FAILED)
					|| each.equals(PlayResult.CLEAR_SUCCEEDED)) {
				list.add(each);
			}
		}
		return list;
	}

	private List<PlayResult> getShotResults() {
		final List<PlayResult> list = new ArrayList<>();
		final PlayResult[] values = PlayResult.values();
		for (int i = 0; i < values.length; i++) {
			final PlayResult each = values[i];
			if (!each.equals(PlayResult.CLEAR_FAILED)
					&& !each.equals(PlayResult.CLEAR_SUCCEEDED)) {
				list.add(each);
			}
		}
		return list;
	}

	private List<PlayEntry> getPlays(String type, GameEntry event,
			String teamSeasonId) {
		final List<PlayEntry> list = new ArrayList<>();
		for (final PlayEntry each : event.getPlays().values()) {
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

	private static class ViolationComparator implements
			Comparator<ViolationEntry> {
		@Override
		public int compare(ViolationEntry o1, ViolationEntry o2) {
			final PenaltyCategory p1 = o1.getCategory();
			final String n1 = o1.getName();
			final PenaltyCategory p2 = o2.getCategory();
			final String n2 = o2.getName();

			final int result = p1.compareTo(p2);
			return result == 0 ? n1.compareTo(n2) : result;
		}
	}
}
