package laxstats.web.teamSeasons;

import java.util.List;

import javax.validation.Valid;

import laxstats.api.teamSeasons.DeleteTeamSeasonCommand;
import laxstats.api.teamSeasons.TeamSeasonDTO;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.teams.EditTeamSeasonCommand;
import laxstats.api.teams.RegisterTeamSeasonCommand;
import laxstats.api.teams.TeamId;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.seasons.SeasonQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.people.SearchPeopleForm;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/teams/{teamId}")
public class TeamSeasonController extends ApplicationController {
	private final TeamQueryRepository teamRepository;
	private final SeasonQueryRepository seasonRepository;
	private TeamSeasonFormValidator teamSeasonValidator;

	@InitBinder("TeamSeasonForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(teamSeasonValidator);
	}

	@Autowired
	public TeamSeasonController(UserQueryRepository userRepository,
			CommandBus commandBus, TeamQueryRepository teamRepository,
			SeasonQueryRepository seasonRepository) {
		super(userRepository, commandBus);
		this.teamRepository = teamRepository;
		this.seasonRepository = seasonRepository;
	}

	@Autowired
	public void setTeamSeasonValidator(
			TeamSeasonFormValidator teamSeasonValidator) {
		this.teamSeasonValidator = teamSeasonValidator;
	}

	/*---------- Action methods ----------*/

	@RequestMapping(value = "/seasons", method = RequestMethod.GET)
	public String index(@PathVariable("teamId") TeamEntry team, Model model) {
		model.addAttribute("team", team);
		return "/teamSeasons/index";
	}

	@RequestMapping(value = "/seasons", method = RequestMethod.POST)
	public String createTeamSeason(@PathVariable("teamId") TeamEntry team,
			@Valid TeamSeasonForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			form.setSeasons(getSeasons());
			return "teamSeasons/newTeamSeason";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final SeasonEntry season = seasonRepository.findOne(form.getSeason());
		final TeamSeasonId identifier = new TeamSeasonId();

		final TeamSeasonDTO dto = new TeamSeasonDTO(identifier, team, season,
				null, form.getStartsOn(), form.getEndsOn(), form.getName(),
				form.getStatus(), user, now, user, now);

		final RegisterTeamSeasonCommand payload = new RegisterTeamSeasonCommand(
				new TeamId(team.getId()), dto);
		try {
			commandBus.dispatch(new GenericCommandMessage<>(payload));
		} catch (final Exception e) {
			result.reject(e.getMessage());
			return "teamSeasons/newTeamSeason";
		}
		return "redirect:/admin/teams/" + team.getId() + "/seasons";
	}

	@RequestMapping(value = "/seasons/{teamSeasonId}", method = RequestMethod.GET)
	public String showTeamSeason(
			@PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
			Model model) {
		final SearchPeopleForm searchForm = new SearchPeopleForm();

		model.addAttribute("teamSeason", teamSeason);
		model.addAttribute("searchForm", searchForm);
		return "teamSeasons/showTeamSeason";
	}

	@RequestMapping(value = "/seasons/{teamSeasonId}", method = RequestMethod.PUT)
	public String updateTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId, @Valid TeamSeasonForm form,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			form.setSeasons(getSeasons());
			return "teamSeasons/editTeamSeason";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamEntry team = teamRepository.findOne(teamId);
		final SeasonEntry season = seasonRepository.findOne(form.getSeason());
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);

		final TeamSeasonDTO dto = new TeamSeasonDTO(identifier, team, season,
				null, form.getStartsOn(), form.getEndsOn(), form.getName(),
				form.getStatus(), user, now);

		final EditTeamSeasonCommand payload = new EditTeamSeasonCommand(
				new TeamId(teamId), dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/admin/teams/" + teamId + "/seasons";
	}

	@RequestMapping(value = "/seasons/{teamSeasonId}", method = RequestMethod.DELETE)
	public String deleteTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId) {
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);
		final DeleteTeamSeasonCommand payload = new DeleteTeamSeasonCommand(
				identifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/admin/teams/" + teamId + "/seasons";
	}

	@RequestMapping(value = "/seasons/new", method = RequestMethod.GET)
	public String newTeamSeason(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);

		final TeamSeasonForm form = new TeamSeasonForm();
		form.setTeam(team.getId());
		form.setTeamTitle(team.getTitle());
		form.setName(team.getName());
		form.setStatus(TeamStatus.ACTIVE);
		if (team.getAffiliation() != null) {
			form.setAffiliation(team.getAffiliation().getId());
		}
		form.setSeasons(getSeasons());

		model.addAttribute("teamSeasonForm", form);
		model.addAttribute("teamId", teamId);
		return "teamSeasons/newTeamSeason";
	}

	@RequestMapping(value = "/seasons/{teamSeasonId}/edit", method = RequestMethod.GET)
	public String editTeamSeason(@PathVariable String teamId,
			@PathVariable String teamSeasonId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);
		final TeamSeasonEntry teamSeason = team.getSeason(teamSeasonId);

		final TeamSeasonForm form = new TeamSeasonForm();
		form.setTeam(teamId);
		form.setTeamTitle(team.getTitle());
		form.setName(teamSeason.getName());
		form.setSeason(teamSeason.getSeason().getId());
		form.setStartsOn(teamSeason.getStartsOn());
		form.setEndsOn(teamSeason.getEndsOn());
		form.setStatus(teamSeason.getStatus());
		if (teamSeason.getAffiliation() != null) {
			form.setAffiliation(teamSeason.getAffiliation().getId());
		}
		form.setSeasons(getSeasons());

		model.addAttribute("teamSeasonForm", form);
		model.addAttribute("teamId", teamId);
		model.addAttribute("teamSeasonId", teamSeasonId);
		return "teamSeasons/editTeamSeason";
	}

	/*---------- Utilities ----------*/

	private List<SeasonEntry> getSeasons() {
		return (List<SeasonEntry>) seasonRepository.findAll(new Sort(
				Direction.DESC, "startsOn"));
	}
}
