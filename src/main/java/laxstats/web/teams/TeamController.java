package laxstats.web.teams;

import java.util.List;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TeamController extends ApplicationController {
	private final TeamQueryRepository teamRepository;
	private final SiteQueryRepository siteRepository;
	private final LeagueQueryRepository leagueRepository;
	private TeamFormValidator teamValidator;

	@InitBinder("TeamForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(teamValidator);
	}

	@Autowired
	public TeamController(TeamQueryRepository teamRepository,
			SiteQueryRepository siteRepository,
			LeagueQueryRepository leagueRepository,
			UserQueryRepository userRepository, CommandBus commandBus) {
		super(userRepository, commandBus);
		this.teamRepository = teamRepository;
		this.siteRepository = siteRepository;
		this.leagueRepository = leagueRepository;
	}

	@Autowired
	public void setTeamValidator(TeamFormValidator teamValidator) {
		this.teamValidator = teamValidator;
	}

	/*---------- Action methods ----------*/

	@RequestMapping(value = "/teams", method = RequestMethod.GET)
	public String index(Model model) {
		final Iterable<TeamEntry> teams = teamRepository.findAll(new Sort(
				"region", "sponsor"));
		model.addAttribute("items", teams);
		return "teams/index";
	}

	@RequestMapping(value = "/teams", method = RequestMethod.POST)
	public String createTeam(@Valid TeamForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "teams/newTeam";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId();

		final String siteId = form.getHomeSite();
		final SiteEntry homeSite = siteId == null ? null : siteRepository
				.findOne(siteId);

		final String leagueId = form.getAffiliation();
		final LeagueEntry league = leagueId == null ? null : leagueRepository
				.findOne(leagueId);

		final TeamDTO dto = new TeamDTO(identifier, form.getSponsor(),
				form.getName(), form.getAbbreviation(), form.getGender(),
				form.getLetter(), form.getRegion(), league, homeSite, now,
				user, now, user);

		final CreateTeamCommand command = new CreateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/teams";
	}

	@RequestMapping(value = "/teams/{teamId}", method = RequestMethod.GET)
	public String show(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);
		model.addAttribute("team", team);
		return "teams/show";
	}

	@RequestMapping(value = "/teams/{teamId}", method = RequestMethod.PUT)
	public String updateTeam(@PathVariable String teamId, @Valid TeamForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			return "teams/editTeam";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamId identifier = new TeamId(teamId);

		final String siteId = form.getHomeSite();
		final SiteEntry homeSite = siteId == null ? null : siteRepository
				.findOne(siteId);

		final String leagueId = form.getAffiliation();
		final LeagueEntry league = leagueId == null ? null : leagueRepository
				.findOne(leagueId);

		final TeamDTO dto = new TeamDTO(identifier, form.getSponsor(),
				form.getName(), form.getAbbreviation(), form.getGender(),
				form.getLetter(), form.getRegion(), league, homeSite, now, user);

		final UpdateTeamCommand command = new UpdateTeamCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/teams";
	}

	@RequestMapping(value = "/teams/{teamId}", method = RequestMethod.DELETE)
	public String deleteTeam(@PathVariable String teamId) {
		final TeamId identifier = new TeamId(teamId);
		final DeleteTeamCommand command = new DeleteTeamCommand(identifier);
		commandBus.dispatch(new GenericCommandMessage<>(command));
		return "redirect:/teams";
	}

	@RequestMapping(value = "teams/new", method = RequestMethod.GET)
	public String newTeam(Model model) {
		final TeamForm form = new TeamForm();
		form.setSites(getSites());

		model.addAttribute("teamForm", form);
		return "teams/newTeam";
	}

	@RequestMapping(value = "/teams/{teamId}/edit", method = RequestMethod.GET)
	public String editTeam(@PathVariable String teamId, Model model) {
		final TeamEntry team = teamRepository.findOne(teamId);

		final TeamForm form = new TeamForm();
		form.setSponsor(team.getSponsor());
		form.setName(team.getName());
		form.setAbbreviation(team.getAbbreviation());
		form.setGender(team.getGender());
		form.setLetter(team.getLetter());
		form.setRegion(team.getRegion());
		if (team.getAffiliation() != null) {
			form.setAffiliation(team.getAffiliation().getId());
		}
		if (team.getHomeSite() != null) {
			form.setHomeSite(team.getHomeSite().getId());
		}
		form.setSites(getSites());

		model.addAttribute("teamForm", form);
		return "teams/editTeam";
	}

	// ---------- Password actions ---------- //

	@RequestMapping(value = "/teams/{teamId}/newPassword", method = RequestMethod.GET)
	public String newPassword(@PathVariable String teamId, Model model) {
		final TeamPasswordForm form = new TeamPasswordForm();
		model.addAttribute("teamId", teamId);
		model.addAttribute("form", form);
		return "teams/newPassword";
	}

	@RequestMapping(value = "/teams/{teamId}/newPassword", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/teams/{teamId}/changePassword", method = RequestMethod.GET)
	public String changePassword(@PathVariable String teamId, Model model) {
		final TeamPasswordForm form = new TeamPasswordForm();
		model.addAttribute("teamId", teamId);
		model.addAttribute("form", form);
		return "/teams/changePassword";
	}

	@RequestMapping(value = "/teams/{teamId}/changePassword", method = RequestMethod.PUT)
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

	private List<SiteEntry> getSites() {
		return (List<SiteEntry>) siteRepository.findAll(new Sort("name"));
	}
}
