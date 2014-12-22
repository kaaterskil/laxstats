package laxstats.web.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import laxstats.api.teams.CreateTeamCommand;
import laxstats.api.teams.CreateTeamPasswordCommand;
import laxstats.api.teams.DeleteTeamCommand;
import laxstats.api.teams.TeamDTO;
import laxstats.api.teams.TeamId;
import laxstats.api.teams.UpdateTeamCommand;
import laxstats.api.teams.UpdateTeamPasswordCommand;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.leagues.LeagueQueryRepository;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/teams")
public class TeamController extends ApplicationController {
	private final TeamQueryRepository teamRepository;
	private final LeagueQueryRepository leagueRepository;
	private final SiteQueryRepository siteRepository;

	@Autowired
	public TeamController(TeamQueryRepository teamRepository,
			LeagueQueryRepository leagueRepository,
			SiteQueryRepository siteRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.teamRepository = teamRepository;
		this.leagueRepository = leagueRepository;
		this.siteRepository = siteRepository;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		final Sort sort = getTeamSorter();
		final Iterable<TeamEntry> teams = teamRepository.findAll(sort);
		model.addAttribute("items", teams);
		return "teams/index";
	}

	@RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
	public String show(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);
		model.addAttribute("team", team);
		return "teams/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newTeam(Model model) {
		final TeamForm form = new TeamForm();
		form.setSites(getSiteData());
		model.addAttribute("form", form);
		return "teams/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createTeam(@ModelAttribute("form") @Valid TeamForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "teams/new";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId();
		final String siteId = form.getSiteId();
		final SiteEntry homeSite = siteId == null ? null : siteRepository
				.findOne(siteId);

		final TeamDTO dto = new TeamDTO(identifier, form.getName(),
				form.getGender(), homeSite, now, user, now, user);
		final CreateTeamCommand command = new CreateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/teams";
	}

	@RequestMapping(value = "/{teamId}/edit", method = RequestMethod.GET)
	public String editTeam(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);
		final TeamForm form = new TeamForm();
		form.setName(team.getName());
		form.setGender(team.getGender());
		form.setSiteId(team.getHomeSite().getId());
		form.setSites(getSiteData());
		model.addAttribute("form", form);
		return "teams/edit";
	}

	@RequestMapping(value = "/{teamId}", method = RequestMethod.PUT)
	public String updateTeam(@PathVariable String teamId,
			@ModelAttribute("form") @Valid TeamForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "teams/edit";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId(teamId);
		final String siteId = form.getSiteId();
		final SiteEntry homeSite = siteId == null ? null : siteRepository
				.findOne(siteId);

		final TeamDTO dto = new TeamDTO(identifier, form.getName(),
				form.getGender(), homeSite, now, user);
		final UpdateTeamCommand command = new UpdateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:";
	}

	@RequestMapping(value = "/{teamId}", method = RequestMethod.DELETE)
	public String deleteTeam(@PathVariable String teamId) {
		final TeamId identifier = new TeamId(teamId);
		final DeleteTeamCommand command = new DeleteTeamCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:";
	}

	// ---------- Password actions ---------- //

	@RequestMapping(value = "/{teamId}/newPassword", method = RequestMethod.GET)
	public String newPassword(@PathVariable String teamId, Model model) {
		final TeamPasswordForm form = new TeamPasswordForm();
		model.addAttribute("teamId", teamId);
		model.addAttribute("form", form);
		return "teams/newPassword";
	}

	@RequestMapping(value = "/{teamId}/newPassword", method = RequestMethod.PUT)
	public String createPassword(@PathVariable String teamId,
			@ModelAttribute("form") TeamPasswordForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "teams/newPassword";
		}
		if (form.getPassword() != form.getConfirmPassword()) {
			return "teams/newPassword";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId(teamId);
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		final TeamDTO dto = new TeamDTO(identifier, encodedPassword, now, user);
		final CreateTeamPasswordCommand payload = new CreateTeamPasswordCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "teams/show";
	}

	@RequestMapping(value = "/{teamId}/changePassword", method = RequestMethod.GET)
	public String changePassword(@PathVariable String teamId, Model model) {
		final TeamPasswordForm form = new TeamPasswordForm();
		model.addAttribute("teamId", teamId);
		model.addAttribute("form", form);
		return "/teams/changePassword";
	}

	@RequestMapping(value = "/{teamId}/changePassword", method = RequestMethod.PUT)
	public String updatePassword(@PathVariable String teamId,
			@ModelAttribute("form") TeamPasswordForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "teams/changePassword";
		}
		if (form.getPassword() != form.getConfirmPassword()) {
			return "teams/changePassword";
		}

		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId(teamId);
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		final String encodedPassword = encoder.encode(form.getPassword());

		final TeamDTO dto = new TeamDTO(identifier, encodedPassword, now, user);
		final UpdateTeamPasswordCommand payload = new UpdateTeamPasswordCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "teams/show";
	}

	private Map<String, String> getLeagueData() {
		final Map<String, String> result = new HashMap<>();
		final Iterable<LeagueEntry> leagues = leagueRepository.findAll();
		for (final LeagueEntry each : leagues) {
			result.put(each.getId(), each.getName());
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

	private Sort getTeamSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("gender"));
		sort.add(new Sort.Order("homeSite.address.region.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);

	}

	private Sort getSiteSorter() {
		final List<Sort.Order> sort = new ArrayList<>();
		sort.add(new Sort.Order("address.region.name"));
		sort.add(new Sort.Order("name"));
		return new Sort(sort);
	}
}
