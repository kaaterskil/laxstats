package laxstats.web.players;

import java.util.List;

import javax.validation.Valid;

import laxstats.api.players.PlayerDTO;
import laxstats.api.players.PlayerId;
import laxstats.api.teamSeasons.DropPlayerCommand;
import laxstats.api.teamSeasons.EditPlayerCommand;
import laxstats.api.teamSeasons.RegisterPlayerCommand;
import laxstats.api.teamSeasons.TeamSeasonId;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.players.PlayerEntry;
import laxstats.query.players.PlayerQueryRepository;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teamSeasons.TeamSeasonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayerController extends ApplicationController {
	private final PlayerQueryRepository playerRepository;
	private final PersonQueryRepository personRepository;
	private final TeamSeasonQueryRepository teamSeasonRepository;

	@Autowired
	public PlayerController(UserQueryRepository userRepository,
			CommandBus commandBus, PlayerQueryRepository playerRepository,
			PersonQueryRepository personRepository,
			TeamSeasonQueryRepository teamSeasonRepository) {
		super(userRepository, commandBus);
		this.playerRepository = playerRepository;
		this.personRepository = personRepository;
		this.teamSeasonRepository = teamSeasonRepository;
	}

	@RequestMapping(value = "teamSeasons/{teamSeasonId}/roster", method = RequestMethod.GET)
	public String index(@PathVariable String teamSeasonId, Model model) {
		final TeamSeasonEntry teamSeason = teamSeasonRepository
				.findOne(teamSeasonId);
		final List<PlayerEntry> roster = teamSeason.getRoster();
		model.addAttribute("items", roster);
		return "players/index";
	}

	@RequestMapping(value = "/teamSeasons/{teamSeasonId}/roster", method = RequestMethod.POST)
	public String createPlayer(@PathVariable String teamSeasonId,
			@ModelAttribute("form") @Valid PlayerForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "players/newPlayer";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);
		final TeamSeasonEntry team = teamSeasonRepository.findOne(teamSeasonId);
		final PersonEntry person = personRepository.findOne(form.getPersonId());
		final PlayerId playerId = new PlayerId();

		final PlayerDTO dto = new PlayerDTO(playerId, person, team,
				person.getFullName(), form.getRole(), form.getStatus(),
				form.getJerseyNumber(), form.getPosition(), form.isCaptain(),
				form.getDepth(), form.getHeight(), form.getWeight(), now, user,
				now, user);
		final RegisterPlayerCommand payload = new RegisterPlayerCommand(
				identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/teamSeasons/" + teamSeasonId + "roster";
	}

	@RequestMapping(value = "teamSeasons/{teamSeasonId}/roster/{playerId}", method = RequestMethod.GET)
	public String showPlayer(@PathVariable String teamSeasonId,
			@PathVariable String playerId, Model model) {
		final PlayerEntry player = playerRepository.findOne(playerId);
		model.addAttribute("item", player);
		return "players/showPlayer";
	}

	@RequestMapping(value = "teamSeasons/{teamSeasonId}/roster/{playerId}", method = RequestMethod.PUT)
	public String updatePlayer(@PathVariable String teamSeasonId,
			@PathVariable String playerId,
			@ModelAttribute("form") @Valid PlayerForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "players/editPlayer";
		}
		final LocalDateTime now = LocalDateTime.now();
		final UserEntry user = getCurrentUser();
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);
		final TeamSeasonEntry team = teamSeasonRepository.findOne(teamSeasonId);
		final PersonEntry person = personRepository.findOne(form.getPersonId());
		final PlayerId playerIdentifier = new PlayerId(playerId);

		final PlayerDTO dto = new PlayerDTO(playerIdentifier, person, team,
				person.getFullName(), form.getRole(), form.getStatus(),
				form.getJerseyNumber(), form.getPosition(), form.isCaptain(),
				form.getDepth(), form.getHeight(), form.getWeight(), now, user);
		final EditPlayerCommand payload = new EditPlayerCommand(identifier, dto);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/teamSeasons/" + teamSeasonId + "/roster";
	}

	@RequestMapping(value = "teamSeasons/{teamSeasonId}/roster/{playerId}", method = RequestMethod.DELETE)
	public String deletePlayer(@PathVariable String teamSeasonId,
			@PathVariable String playerId) {
		final TeamSeasonId identifier = new TeamSeasonId(teamSeasonId);
		final PlayerId playerIdentifier = new PlayerId(playerId);
		final DropPlayerCommand payload = new DropPlayerCommand(identifier,
				playerIdentifier);
		commandBus.dispatch(new GenericCommandMessage<>(payload));
		return "redirect:/teamSeasons/" + teamSeasonId + "/roster";
	}

	@RequestMapping(value = "teamSeasons/{teamSeasonId}/roster/new", method = RequestMethod.GET)
	public String newPlayer(@PathVariable String teamSeasonId, Model model) {
		final PlayerForm form = new PlayerForm();
		model.addAttribute("form", form);
		model.addAttribute("teamSeasonId", teamSeasonId);
		return "players/newPlayer";
	}

	@RequestMapping(value = "teamSeasons/{teamSeasonId}/roster/{playerId}/edit", method = RequestMethod.GET)
	public String editPlayer(@PathVariable String teamSeasonId,
			@PathVariable String playerId, Model model) {
		final PlayerEntry player = playerRepository.findOne(playerId);

		final PlayerForm form = new PlayerForm();
		form.setPersonId(player.getPerson().getId());
		form.setJerseyNumber(player.getJerseyNumber());
		form.setPosition(player.getPosition());
		form.setRole(player.getRole());
		form.setStatus(player.getStatus());
		form.setCaptain(player.isCaptain());
		form.setDepth(player.getDepth());
		form.setHeight(player.getHeight());
		form.setWeight(player.getWeight());

		model.addAttribute("form", form);
		model.addAttribute("teamSeasonId", teamSeasonId);
		return "players/editPlayer";
	}
}
