package laxstats.web.teamSeasons;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teams.EditTeamSeasonCommand;
import laxstats.api.teams.RegisterTeamSeasonCommand;
import laxstats.api.teams.TeamId;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
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
public class TeamSeasonController extends ApplicationController {
	private final TeamQueryRepository teamRepository;
	private final SeasonQueryRepository seasonRepository;
	private final TeamSeasonQueryRepository teamSeasonRepository;

	@Autowired
	public TeamSeasonController(UserQueryRepository userRepository,
			CommandBus commandBus, TeamQueryRepository teamRepository,
			SeasonQueryRepository seasonRepository,
			TeamSeasonQueryRepository teamSeasonRepository) {
		super(userRepository, commandBus);
		this.teamRepository = teamRepository;
		this.seasonRepository = seasonRepository;
		this.teamSeasonRepository = teamSeasonRepository;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new TeamSeasonFormValidator());
	}

	@RequestMapping(value = "/teams/{teamId}/seasons", method = RequestMethod.GET)
	public String index(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);
		model.addAttribute("items", team.getSeasons());
		return "/teamSeasons/index";
	}

	@RequestMapping(value = "/teams/{teamId}/seasons", method = RequestMethod.POST)
	public String createTeamSeason(@PathVariable String teamId,
			@ModelAttribute("form") @Valid TeamSeasonForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "seasons/newTeamSeason";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamEntry team = teamRepository.findOne(teamId);
		final SeasonEntry season = seasonRepository.findOne(form.getSeasonId());
		final TeamSeasonId identifier = new TeamSeasonId();

		final TeamSeasonDTO dto = new TeamSeasonDTO(identifier, team, season,
				form.getStartsOn(), form.getEndsOn(), form.getStatus(), user,
				now, user, now);
		final RegisterTeamSeasonCommand payload = new RegisterTeamSeasonCommand(
				new TeamId(teamId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/teams/" + teamId + "/seasons";
	}

	@RequestMapping(value = "/teams/{teamId}/seasons/{teamSeasonId}", method = RequestMethod.GET)
	public String showTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId, Model model) {
		final TeamSeasonEntry teamSeason = teamSeasonRepository
				.findOne(teamSeasonId);
		model.addAttribute("item", teamSeason);
		return "teamSeasons/showTeamSeason";
	}

	@RequestMapping(value = "/teams/{teamId}/seasons/{teamSeasonId}", method = RequestMethod.PUT)
	public String updateTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId,
			@ModelAttribute("form") @Valid TeamSeasonForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "teamSeasons/editTeamSeason";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamEntry team = teamRepository.findOne(teamId);
		final SeasonEntry season = seasonRepository.findOne(form.getSeasonId());
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);

		final TeamSeasonDTO dto = new TeamSeasonDTO(identifier, team, season,
				form.getStartsOn(), form.getEndsOn(), form.getStatus(), user,
				now);
		final EditTeamSeasonCommand payload = new EditTeamSeasonCommand(
				new TeamId(teamId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/teams/" + teamId + "/seasons";
	}

	@RequestMapping(value = "/teams/{teamId}/seasons/{teamSeasonId}", method = RequestMethod.DELETE)
	public String deleteTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId) {
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);
		final DeleteTeamSeasonCommand payload = new DeleteTeamSeasonCommand(
				identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:";
	}

	@RequestMapping(value = "/teams/{teamId}/seasons/new", method = RequestMethod.GET)
	public String newTeamSeason(@PathVariable String teamId, Model model) {
		final TeamSeasonForm form = new TeamSeasonForm();
		form.setSeasons(getSeasonData());

		model.addAttribute("form", form);
		model.addAttribute("teamId", teamId);
		return "seasons/newTeamSeason";
	}

	@RequestMapping(value = "/teams/{teamId}/seasons/{teamSeasonId}/edit", method = RequestMethod.GET)
	public String editTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId, Model model) {
		final TeamSeasonEntry teamSeason = teamSeasonRepository
				.findOne(teamSeasonId);

		final TeamSeasonForm form = new TeamSeasonForm();
		form.setSeasonId(teamSeason.getSeason().getId());
		form.setStartsOn(teamSeason.getStartsOn());
		form.setEndsOn(teamSeason.getEndsOn());
		form.setStatus(teamSeason.getStatus());
		form.setSeasons(getSeasonData());

		model.addAttribute("form", form);
		model.addAttribute("teamId", teamId);
		return "seasons/editTeamSeason";
	}

	private Map<String, String> getSeasonData() {
		final Map<String, String> result = new HashMap<>();
		final Iterable<SeasonEntry> seasons = seasonRepository
				.findAll(new Sort(new Sort.Order("startsOn")));
		for (final SeasonEntry each : seasons) {
			result.put(each.getId(), each.getDescription());
		}
		return result;
	}
}
