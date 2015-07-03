package laxstats.web.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.games.DeleteClear;
import laxstats.api.games.DeleteFaceOff;
import laxstats.api.games.DeleteGoal;
import laxstats.api.games.DeleteGroundBall;
import laxstats.api.games.DeletePenalty;
import laxstats.api.games.DeleteShot;
import laxstats.api.games.GameId;
import laxstats.api.games.PlayDTO;
import laxstats.api.games.PlayKey;
import laxstats.api.games.PlayParticipantDTO;
import laxstats.api.games.PlayResult;
import laxstats.api.games.PlayRole;
import laxstats.api.games.PlayType;
import laxstats.api.games.RecordClear;
import laxstats.api.games.RecordFaceoff;
import laxstats.api.games.RecordGoal;
import laxstats.api.games.RecordGroundBall;
import laxstats.api.games.RecordPenalty;
import laxstats.api.games.RecordShot;
import laxstats.api.games.ScoreAttemptType;
import laxstats.api.games.UpdateClear;
import laxstats.api.games.UpdateFaceOff;
import laxstats.api.games.UpdateGoal;
import laxstats.api.games.UpdateGroundBall;
import laxstats.api.games.UpdatePenalty;
import laxstats.api.games.UpdateShot;
import laxstats.api.violations.PenaltyCategory;
import laxstats.query.games.AttendeeEntry;
import laxstats.query.games.FaceOffEntry;
import laxstats.query.games.GameEntry;
import laxstats.query.games.GameQueryRepository;
import laxstats.query.games.GoalEntry;
import laxstats.query.games.GroundBallEntry;
import laxstats.query.games.PenaltyEntry;
import laxstats.query.games.PlayEntry;
import laxstats.query.games.PlayParticipantEntry;
import laxstats.query.games.ShotEntry;
import laxstats.query.games.TeamEvent;
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
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayController extends ApplicationController {
   private final GameQueryRepository eventRepository;
   private final TeamSeasonQueryRepository teamRepository;
   private final ViolationQueryRepository violationRepository;
   private Map<String, String> violations;

   @Autowired
   private ClearFormValidator clearValidator;
   @Autowired
   private FaceOffFormValidator faceOffValidator;
   @Autowired
   private GoalFormValidator goalValidator;
   @Autowired
   private GroundBallFormValidator groundBallValidator;
   @Autowired
   private PenaltyFormValidator penaltyValidator;
   @Autowired
   private ShotFormValidator shotValidator;

   @Autowired
   public PlayController(UserQueryRepository userRepository, CommandBus commandBus,
      GameQueryRepository eventRepository, TeamSeasonQueryRepository teamRepository,
      ViolationQueryRepository violationRepository) {
      super(userRepository, commandBus);
      this.eventRepository = eventRepository;
      this.teamRepository = teamRepository;
      this.violationRepository = violationRepository;
   }

   @InitBinder("clearForm")
   protected void initClearBinder(WebDataBinder binder) {
      binder.setValidator(clearValidator);
   }

   @InitBinder("faceOffForm")
   protected void initFaceOffBinder(WebDataBinder binder) {
      binder.setValidator(faceOffValidator);
   }

   @InitBinder("goalForm")
   protected void initGoalBinder(WebDataBinder binder) {
      binder.setValidator(goalValidator);
   }

   @InitBinder("groundBallForm")
   protected void initGroundBallBinder(WebDataBinder binder) {
      binder.setValidator(groundBallValidator);
   }

   @InitBinder("penaltyForm")
   protected void initPenaltyBinder(WebDataBinder binder) {
      binder.setValidator(penaltyValidator);
   }

   @InitBinder("shotForm")
   protected void initShotBinder(WebDataBinder binder) {
      binder.setValidator(shotValidator);
   }

   /*---------- Clear actions ----------*/

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/clears/new",
      method = RequestMethod.GET)
   public String newClear(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model,
      HttpServletRequest request) {
      final int period = Integer.parseInt(request.getParameter("p"));

      final ClearForm form = new ClearForm();
      form.setGameId(aggregate.getId());
      form.setTeamName(teamSeason.getFullName());
      form.setTeamSeasonId(teamSeason.getId());
      form.setPeriod(period);

      model.addAttribute("clearForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/clears/newRealTimeClear :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/clears", method = RequestMethod.POST)
   public String createClear(@PathVariable("gameId") String eventId,
      @Valid @RequestBody ClearForm clearForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/clears/newRealTimeClear :: content";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String playId = IdentifierFactory.getInstance().generateIdentifier();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = aggregate.getTeam(clearForm.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = aggregate.getPlays().size();

      // Create clear
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.CLEAR, PlayKey.PLAY, aggregate, teamSeason,
            clearForm.getPeriod(), null, null, clearForm.getResult(), null, null,
            clearForm.getComment(), sequence, now, user, now, user, participants);

      final RecordClear payload =
         new RecordClear(new GameId(aggregate.getId()), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/clears/{playId}", method = RequestMethod.DELETE)
   public String deleteClear(@PathVariable("gameId") String eventId,
      @PathVariable("playId") String playId, Model model) {
      final GameId identifier = new GameId(eventId);
      final DeleteClear payload = new DeleteClear(identifier, playId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/clears/{playId}", method = RequestMethod.PUT)
   public String updateClear(@PathVariable("gameId") String eventId,
      @PathVariable("playId") String playId, @Valid @RequestBody ClearForm clearForm,
      BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/clears/editClear";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final GameEntry aggregate = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = aggregate.getTeam(clearForm.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = aggregate.getPlays().size();

      // Edit clear
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.CLEAR, PlayKey.PLAY, aggregate, teamSeason,
            clearForm.getPeriod(), null, null, clearForm.getResult(), null, null,
            clearForm.getComment(), sequence, now, user, participants);

      final UpdateClear payload = new UpdateClear(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/clears/{playId}/edit",
      method = RequestMethod.GET)
   public String editClear(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("playId") String playId, Model model) {
      final PlayEntry play = aggregate.getPlays().get(playId);
      final TeamSeasonEntry teamSeason = play.getTeam();

      final ClearForm form = new ClearForm();
      form.setPlayId(playId);
      form.setGameId(aggregate.getId());
      form.setTeamName(teamSeason.getFullName());
      form.setTeamSeasonId(play.getTeam().getId());
      form.setPeriod(play.getPeriod());
      form.setResult(play.getResult());
      form.setComment(play.getComment());

      model.addAttribute("clearForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/clears/editClear :: content";
   }

   /*---------- Faceoff actions ----------*/

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/faceOffs/new",
      method = RequestMethod.GET)
   public String newFaceOff(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model,
      HttpServletRequest request) {
      final int period = Integer.parseInt(request.getParameter("p"));
      final int elapsedSeconds = Integer.parseInt(request.getParameter("t"));
      final Period elapsedTime = new Period(elapsedSeconds * 1000);

      final FaceOffForm form = new FaceOffForm();
      form.setGameId(aggregate.getId());
      for (final TeamEvent te : aggregate.getTeams()) {
         final TeamSeasonEntry tse = te.getTeamSeason();
         if (tse.getId().equals(teamSeason.getId())) {
            form.setWinners(aggregate.getAttendees(tse));
         } else {
            form.setLosers(aggregate.getAttendees(tse));
         }
      }
      form.setPeriod(period);
      form.setElapsedTime(elapsedTime);

      model.addAttribute("faceOffForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/faceOffs/newRealTimeFaceOff :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/faceOffs", method = RequestMethod.POST)
   public String createFaceOff(@PathVariable("gameId") String eventId,
      @Valid @RequestBody FaceOffForm form, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/faceOffs/newRealTimeFaceOff :: content";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String playId = IdentifierFactory.getInstance().generateIdentifier();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = aggregate.getPlays().size();

      // Create winner
      final String winnerId = IdentifierFactory.getInstance().generateIdentifier();
      final AttendeeEntry winner = aggregate.getAttendee(form.getWinnerId());
      final PlayParticipantDTO winnerDTO =
         new PlayParticipantDTO(winnerId, playId, winner, winner.getTeamSeason(),
            PlayRole.FACEOFF_WINNER, false, now, user, now, user);
      participants.add(winnerDTO);

      // Create loser
      final String loserId = IdentifierFactory.getInstance().generateIdentifier();
      final AttendeeEntry loser = aggregate.getAttendee(form.getLoserId());
      final PlayParticipantDTO loserDTO =
         new PlayParticipantDTO(loserId, playId, loser, loser.getTeamSeason(),
            PlayRole.FACEOFF_LOSER, false, now, user, now, user);
      participants.add(loserDTO);

      // Create faceoff
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.FACEOFF, PlayKey.PLAY, aggregate, teamSeason,
            form.getPeriod(), null, null, null, null, null, form.getComment(), sequence, now, user,
            now, user, participants);

      final RecordFaceoff payload =
         new RecordFaceoff(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/faceOffs/{playId}",
      method = RequestMethod.DELETE)
   public String deleteFaceOff(@PathVariable("gameId") String eventId,
      @PathVariable("gameId") String playId, Model model) {
      final GameId identifier = new GameId(eventId);
      final DeleteFaceOff payload = new DeleteFaceOff(identifier, playId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/faceOffs/{playId}", method = RequestMethod.PUT)
   public String updateFaceOff(@PathVariable String eventId, @PathVariable String playId,
      @ModelAttribute("form") @Valid FaceOffForm form, BindingResult result) {
      if (result.hasErrors()) {
         return "events/faceOffs/editFaceOff";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();

      final GameEntry event = eventRepository.findOne(eventId);
      final PlayEntry play = event.getPlays().get(playId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final int sequence = event.getPlays().size();

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
         final PlayParticipantDTO participantDTO =
            new PlayParticipantDTO(participant.getId(), playId, attendee, attendee.getTeamSeason(),
               role, false, now, user);
         participants.add(participantDTO);

      }
      // Edit faceoff play
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.FACEOFF, PlayKey.PLAY, event, teamSeason, form.getPeriod(),
            null, null, null, null, null, form.getComment(), sequence, now, user, participants);

      final UpdateFaceOff payload =
         new UpdateFaceOff(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/faceOffs";
   }

   @RequestMapping(value = "/admin/events/{gameId}/faceOffs/{playId}/edit",
      method = RequestMethod.GET)
   public String editFaceOff(@PathVariable String eventId, @PathVariable String playId, Model model) {
      final Map<String, Object> attributes = new HashMap<>();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      attributes.put("event", aggregate);

      final FaceOffEntry play = (FaceOffEntry)aggregate.getPlays().get(playId);
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

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/goals/new",
      method = RequestMethod.GET)
   public String newGoal(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model,
      HttpServletRequest request) {
      final int period = Integer.parseInt(request.getParameter("p"));
      final int elapsedSeconds = Integer.parseInt(request.getParameter("t"));
      final Period elapsedTime = new Period(elapsedSeconds * 1000);

      final GoalForm form = new GoalForm();
      form.setGameId(aggregate.getId());
      form.setTeamSeasonId(teamSeason.getId());
      form.setPeriod(period);
      form.setElapsedTime(elapsedTime);
      form.setAttemptType(ScoreAttemptType.REGULAR);
      form.setAttendees(aggregate.getAttendees(teamSeason));

      model.addAttribute("goalForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/goals/newRealTimeGoal :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/goals", method = RequestMethod.POST)
   public String createGoal(@PathVariable("gameId") String eventId,
      @Valid @RequestBody GoalForm form, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/goals/newRealTimeGoal :: content";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String playId = IdentifierFactory.getInstance().generateIdentifier();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = aggregate.getPlays().size();

      // Create scorer
      final String scorerId = IdentifierFactory.getInstance().generateIdentifier();
      final AttendeeEntry scorer = aggregate.getAttendee(form.getScorerId());
      final PlayParticipantDTO scorerDTO =
         new PlayParticipantDTO(scorerId, playId, scorer, teamSeason, PlayRole.SCORER, true, now,
            user, now, user);
      participants.add(scorerDTO);

      // Create assist
      final AttendeeEntry assist = aggregate.getAttendee(form.getAssistId());
      if (assist != null) {
         final String assistId = IdentifierFactory.getInstance().generateIdentifier();
         final PlayParticipantDTO assistDTO =
            new PlayParticipantDTO(assistId, playId, assist, teamSeason, PlayRole.ASSIST, true, now,
               user, now, user);
         participants.add(assistDTO);
      }

      // Create goal
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.GOAL, PlayKey.GOAL, aggregate, teamSeason, form.getPeriod(),
            form.getElapsedTime(), form.getAttemptType(), PlayResult.GOAL, null, null,
            form.getComment(), sequence, now, user, now, user, participants);

      final RecordGoal payload = new RecordGoal(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/goals/{playId}", method = RequestMethod.DELETE)
   public String deleteGoal(@PathVariable("gameId") String eventId,
      @PathVariable("playId") String playId, Model model) {
      final GameId identifier = new GameId(eventId);
      final DeleteGoal payload = new DeleteGoal(identifier, playId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/goals/{playId}", method = RequestMethod.PUT)
   public String updateGoal(@PathVariable String eventId, @PathVariable String playId,
      @ModelAttribute("form") @Valid GoalForm form, BindingResult result) {
      if (result.hasErrors()) {
         return "events/goals/editGoal";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();

      final GameEntry event = eventRepository.findOne(eventId);
      final PlayEntry play = event.getPlays().get(playId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = event.getPlays().size();

      // Edit scorer
      for (final PlayParticipantEntry participant : play.getParticipants()) {
         if (participant.getRole().equals(PlayRole.SCORER)) {
            AttendeeEntry scorer = participant.getAttendee();
            if (!form.getScorerId().equals(scorer.getId())) {
               scorer = event.getAttendee(form.getScorerId());
            }
            final PlayParticipantDTO scorerDTO =
               new PlayParticipantDTO(participant.getId(), playId, scorer, teamSeason,
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
               assistDTO =
                  new PlayParticipantDTO(participant.getId(), playId, assist, teamSeason,
                     PlayRole.ASSIST, true, now, user);
               participants.add(assistDTO);
            }
         }
      }
      // Edit null -> assist
      if (!foundAssist && form.getAssistId() != null) {
         final String id = IdentifierFactory.getInstance().generateIdentifier();
         assist = event.getAttendee(form.getAssistId());
         assistDTO =
            new PlayParticipantDTO(id, playId, assist, teamSeason, PlayRole.ASSIST, true, now, user);
         participants.add(assistDTO);
      }

      // Create goal
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.GOAL, PlayKey.GOAL, event, teamSeason, form.getPeriod(),
            form.getElapsedTime(), form.getAttemptType(), PlayResult.GOAL, null, null,
            form.getComment(), sequence, now, user, participants);

      final UpdateGoal payload = new UpdateGoal(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/goals";
   }

   @RequestMapping(value = "/admin/events/{gameId}/goals/{playId}/edit", method = RequestMethod.GET)
   public
      String editGoal(@PathVariable String eventId, @PathVariable String playId, Model model) {
      final Map<String, Object> attributes = new HashMap<>();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      attributes.put("event", aggregate);

      final GoalEntry play = (GoalEntry)aggregate.getPlays().get(playId);
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

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/groundBalls/new",
      method = RequestMethod.GET)
   public String newGroundBall(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model,
      HttpServletRequest request) {
      final int period = Integer.parseInt(request.getParameter("p"));

      final GroundBallForm form = new GroundBallForm();
      form.setGameId(aggregate.getId());
      form.setPeriod(period);
      form.setAttendees(aggregate.getAttendees(teamSeason));

      model.addAttribute("groundBallForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/groundBalls/newRealTimeGroundBall :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/groundBalls", method = RequestMethod.POST)
   public String createGroundBall(@PathVariable("gameId") String eventId,
      @Valid @RequestBody GroundBallForm form, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/groundBalls/newRealTimeGroundBall :: content";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String playId = IdentifierFactory.getInstance().generateIdentifier();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = aggregate.getPlays().size();

      // Create player
      final String playerId = IdentifierFactory.getInstance().generateIdentifier();
      final AttendeeEntry player = aggregate.getAttendee(form.getPlayerId());
      final PlayParticipantDTO shooterDTO =
         new PlayParticipantDTO(playerId, playId, player, teamSeason, PlayRole.GROUND_BALL, false,
            now, user, now, user);
      participants.add(shooterDTO);

      // Create ground ball play
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.GROUND_BALL, PlayKey.PLAY, aggregate, teamSeason,
            form.getPeriod(), null, null, null, null, null, form.getComment(), sequence, now, user,
            now, user, participants);

      final RecordGroundBall payload =
         new RecordGroundBall(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/groundBalls/{playId}",
      method = RequestMethod.DELETE)
   public String deleteGroundBall(@PathVariable("gameId") String eventId,
      @PathVariable("playId") String playId, Model model) {
      final GameId identifier = new GameId(eventId);
      final DeleteGroundBall payload = new DeleteGroundBall(identifier, playId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/groundBalls/{playId}",
      method = RequestMethod.PUT)
   public String updateGroundBall(@PathVariable String eventId, @PathVariable String playId,
      @ModelAttribute("form") @Valid GroundBallForm form, BindingResult result) {
      if (result.hasErrors()) {
         return "events/groundBalls/editGroundBall";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();

      final GameEntry event = eventRepository.findOne(eventId);
      final PlayEntry play = event.getPlays().get(playId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = event.getPlays().size();

      // Edit player
      final PlayParticipantEntry participant = play.getParticipants().get(0);
      AttendeeEntry attendee = participant.getAttendee();
      if (!form.getPlayerId().equals(attendee.getId())) {
         attendee = event.getAttendee(form.getPlayerId());
      }
      final PlayParticipantDTO participantDTO =
         new PlayParticipantDTO(participant.getId(), playId, attendee, teamSeason,
            PlayRole.GROUND_BALL, false, now, user);
      participants.add(participantDTO);

      // Edit ground ball play
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.GROUND_BALL, PlayKey.PLAY, event, teamSeason,
            form.getPeriod(), null, null, null, null, null, form.getComment(), sequence, now, user,
            participants);

      final UpdateGroundBall payload =
         new UpdateGroundBall(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/groundBalls";
   }

   @RequestMapping(value = "/admin/events/{gameId}/groundBalls/{playId}/edit",
      method = RequestMethod.GET)
   public String editGroundBall(@PathVariable String eventId, @PathVariable String playId,
      Model model) {
      final Map<String, Object> attributes = new HashMap<>();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      attributes.put("event", aggregate);

      final GroundBallEntry play = (GroundBallEntry)aggregate.getPlays().get(playId);
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

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/penalties/new",
      method = RequestMethod.GET)
   public String newPenalty(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model,
      HttpServletRequest request) {
      final int period = Integer.parseInt(request.getParameter("p"));
      final int elapsedSeconds = Integer.parseInt(request.getParameter("t"));
      final Period elapsedTime = new Period(elapsedSeconds * 1000);

      final PenaltyForm form = new PenaltyForm();
      form.setGameId(aggregate.getId());
      form.setTeamSeasonId(teamSeason.getId());
      form.setPeriod(period);
      form.setElapsedTime(elapsedTime);
      form.setViolationData(getViolations());
      for (final TeamEvent te : aggregate.getTeams()) {
         final TeamSeasonEntry tse = te.getTeamSeason();
         if (tse.getId().equals(teamSeason.getId())) {
            form.setViolators(aggregate.getAttendees(tse));
         } else {
            form.setOpponents(aggregate.getAttendees(tse));
         }
      }

      model.addAttribute("penaltyForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/penalties/newRealTimePenalty :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/penalties", method = RequestMethod.POST)
   public String createPenalty(@PathVariable("gameId") String eventId,
      @Valid @RequestBody PenaltyForm form, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/penalties/newRealTimePenalty :: content";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String playId = IdentifierFactory.getInstance().generateIdentifier();

      final GameEntry event = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = event.getPlays().size();

      // Create violator
      final String violatorId = IdentifierFactory.getInstance().generateIdentifier();
      final AttendeeEntry violator = event.getAttendee(form.getCommittedById());
      final PlayParticipantDTO violatorDTO =
         new PlayParticipantDTO(violatorId, playId, violator, teamSeason,
            PlayRole.PENALTY_COMMITTED_BY, false, now, user, now, user);
      participants.add(violatorDTO);

      // Create against
      final AttendeeEntry against = event.getAttendee(form.getCommittedAgainstId());
      if (against != null) {
         final String againstId = IdentifierFactory.getInstance().generateIdentifier();
         final PlayParticipantDTO againstDTO =
            new PlayParticipantDTO(againstId, playId, against, teamSeason,
               PlayRole.PENALTY_COMMITTED_AGAINST, false, now, user, now, user);
         participants.add(againstDTO);
      }

      // Create penalty
      final ViolationEntry violation = violationRepository.findOne(form.getViolationId());
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.PENALTY, PlayKey.PLAY, event, teamSeason, form.getPeriod(),
            form.getElapsedTime(), null, null, violation, form.getDuration(), form.getComment(),
            sequence, now, user, now, user, participants);

      final RecordPenalty payload =
         new RecordPenalty(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/penalties/{playId}", method = RequestMethod.PUT)
   public String updatePenalty(@PathVariable String eventId, @PathVariable String playId,
      @ModelAttribute("penaltyForm") @Valid PenaltyForm form, BindingResult result) {
      if (result.hasErrors()) {
         return "events/penalties/editPenalty";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();

      final GameEntry event = eventRepository.findOne(eventId);
      final PenaltyEntry play = (PenaltyEntry)event.getPlays().get(playId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = event.getPlays().size();

      // Edit violator
      final PlayParticipantEntry violator = play.getCommittedBy();
      AttendeeEntry attendee = violator.getAttendee();
      if (!form.getCommittedById().equals(attendee.getId())) {
         attendee = event.getAttendee(form.getCommittedById());
      }
      final PlayParticipantDTO violatorDTO =
         new PlayParticipantDTO(violator.getId(), playId, attendee, teamSeason,
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
                  against = event.getAttendee(form.getCommittedAgainstId());
               }
               againstDTO =
                  new PlayParticipantDTO(participant.getId(), playId, against, teamSeason,
                     PlayRole.PENALTY_COMMITTED_AGAINST, false, now, user);
               participants.add(againstDTO);
            }
         }
      }
      if (!foundAgainst && form.getCommittedAgainstId() != null) {
         final String id = IdentifierFactory.getInstance().generateIdentifier();
         against = event.getAttendee(form.getCommittedAgainstId());
         againstDTO =
            new PlayParticipantDTO(id, playId, against, teamSeason,
               PlayRole.PENALTY_COMMITTED_AGAINST, false, now, user);
         participants.add(againstDTO);
      }

      // Edit penalty
      final ViolationEntry violation = violationRepository.findOne(form.getViolationId());
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.PENALTY, PlayKey.PLAY, event, teamSeason, form.getPeriod(),
            form.getElapsedTime(), null, null, violation, form.getDuration(), form.getComment(),
            sequence, now, user, participants);

      final UpdatePenalty payload =
         new UpdatePenalty(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/penalties";
   }

   @RequestMapping(value = "/admin/events/{gameId}/penalties/{playId}",
      method = RequestMethod.DELETE)
   public String deletePenalty(@PathVariable("gameId") String eventId,
      @PathVariable("playId") String playId, Model model) {
      final GameId identifier = new GameId(eventId);
      final DeletePenalty payload = new DeletePenalty(identifier, playId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/penalties/{playId}/edit",
      method = RequestMethod.GET)
   public String editPenalty(@PathVariable String eventId, @PathVariable String playId, Model model) {
      final Map<String, Object> attributes = new HashMap<>();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      attributes.put("event", aggregate);

      final PenaltyEntry play = (PenaltyEntry)aggregate.getPlays().get(playId);
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

   @RequestMapping(value = "/admin/events/{gameId}/teamSeasons/{teamSeasonId}/shots/new",
      method = RequestMethod.GET)
   public String newShot(@PathVariable("gameId") GameEntry aggregate,
      @PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model,
      HttpServletRequest request) {
      final int period = Integer.parseInt(request.getParameter("p"));

      final ShotForm form = new ShotForm();
      form.setGameId(aggregate.getId());
      form.setTeamSeasonId(teamSeason.getId());
      form.setPeriod(period);
      form.setAttendees(aggregate.getAttendees(teamSeason));
      form.setAttemptType(ScoreAttemptType.REGULAR);

      model.addAttribute("shotForm", form);
      model.addAttribute("event", aggregate);
      model.addAttribute("teamSeason", teamSeason);
      return "events/shots/newRealTimeShot :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/shots", method = RequestMethod.POST)
   public String createShot(@PathVariable("gameId") String eventId,
      @Valid @RequestBody ShotForm form, BindingResult result, Model model) {
      if (result.hasErrors()) {
         return "events/shots/newRealTimeShot :: content";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final String playId = IdentifierFactory.getInstance().generateIdentifier();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = aggregate.getPlays().size();

      // Create shooter
      final String shooterId = IdentifierFactory.getInstance().generateIdentifier();
      final AttendeeEntry shooter = aggregate.getAttendee(form.getPlayerId());
      final PlayParticipantDTO shooterDTO =
         new PlayParticipantDTO(shooterId, playId, shooter, teamSeason, PlayRole.SHOOTER, false,
            now, user, now, user);
      participants.add(shooterDTO);

      // Create shot
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.SHOT, PlayKey.PLAY, aggregate, teamSeason, form.getPeriod(),
            null, form.getAttemptType(), form.getResult(), null, null, form.getComment(), sequence,
            now, user, now, user, participants);

      final RecordShot payload = new RecordShot(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/shots/{playId}", method = RequestMethod.PUT)
   public String updateShot(@PathVariable String eventId, @PathVariable String playId,
      @ModelAttribute("form") @Valid ShotForm form, BindingResult result) {
      if (result.hasErrors()) {
         return "events/shots/editShot";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();

      final GameEntry event = eventRepository.findOne(eventId);
      final PlayEntry play = event.getPlays().get(playId);
      final TeamSeasonEntry teamSeason = teamRepository.findOne(form.getTeamSeasonId());
      final List<PlayParticipantDTO> participants = new ArrayList<>();
      final int sequence = event.getPlays().size();

      // Edit shooter
      final PlayParticipantEntry participant = play.getParticipants().get(0);
      AttendeeEntry attendee = participant.getAttendee();
      if (!form.getPlayerId().equals(attendee.getId())) {
         attendee = event.getAttendee(form.getPlayerId());
      }
      final PlayParticipantDTO shooterDTO =
         new PlayParticipantDTO(participant.getId(), playId, attendee, teamSeason, PlayRole.SHOOTER,
            false, now, user);
      participants.add(shooterDTO);

      // Edit shot
      final PlayDTO dto =
         new PlayDTO(playId, PlayType.SHOT, PlayKey.PLAY, event, teamSeason, form.getPeriod(), null,
            form.getAttemptType(), form.getResult(), null, null, form.getComment(), sequence, now,
            user, participants);

      final UpdateShot payload = new UpdateShot(new GameId(eventId), playId, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/events/" + eventId + "/shots";
   }

   @RequestMapping(value = "/admin/events/{gameId}/shots/{playId}", method = RequestMethod.DELETE)
   public String deleteShot(@PathVariable("gameId") String eventId,
      @PathVariable("playId") String playId, Model model) {
      final GameId identifier = new GameId(eventId);
      final DeleteShot payload = new DeleteShot(identifier, playId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      final GameEntry refreshedAggregate = eventRepository.findOne(eventId);
      model.addAttribute("game", refreshedAggregate);
      return "events/realTimePlayIndex :: content";
   }

   @RequestMapping(value = "/admin/events/{gameId}/shots/{playId}/edit", method = RequestMethod.GET)
   public
      String editShot(@PathVariable String eventId, @PathVariable String playId, Model model) {
      final Map<String, Object> attributes = new HashMap<>();

      final GameEntry aggregate = eventRepository.findOne(eventId);
      attributes.put("event", aggregate);

      final ShotEntry play = (ShotEntry)aggregate.getPlays().get(playId);
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

   private Map<String, String> getViolations() {
      if (violations == null) {
         final Map<String, String> result = new HashMap<>();
         final List<ViolationEntry> list = (List<ViolationEntry>)violationRepository.findAll();
         Collections.sort(list, new ViolationComparator());
         for (final ViolationEntry each : list) {
            result.put(each.getId(), each.getName());
         }
         violations = result;
      }
      return violations;
   }

   private static class ViolationComparator implements Comparator<ViolationEntry> {
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
