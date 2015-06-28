package laxstats.web.players;

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
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.people.SearchPeopleForm;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PlayerController extends ApplicationController {
   private final PersonQueryRepository personRepository;

   @Autowired
   public PlayerController(UserQueryRepository userRepository, CommandBus commandBus,
      PersonQueryRepository personRepository) {
      super(userRepository, commandBus);
      this.personRepository = personRepository;
   }

   @RequestMapping(value = "/admin/teamSeasons/{teamSeasonId}/roster", method = RequestMethod.POST)
   public String createPlayer(@PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @Valid PlayerForm form, BindingResult result)
   {
      if (result.hasErrors()) {
         return "players/newPlayer";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final TeamSeasonId identifier = new TeamSeasonId(teamSeason.getId());
      final PersonEntry person = personRepository.findOne(form.getPerson());
      final PlayerId playerId = new PlayerId();

      final PlayerDTO dto =
               new PlayerDTO(playerId, person, teamSeason, person.getFullName(), form.getRole(),
                  form.getStatus(), form.getJerseyNumber(), form.getPosition(), form.isCaptain(),
                  form.getDepth(), form.getHeight(), form.getWeight(), now, user, now, user);

      final RegisterPlayerCommand payload = new RegisterPlayerCommand(identifier, dto);
      try {
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         result.reject(e.getMessage());
         return "players/newPlayer";
      }
      return "redirect:/admin/teams/" + teamSeason.getTeam().getId() + "/seasons/" +
      teamSeason.getId();
   }

   @RequestMapping(value = "/admin/teamSeasons/{teamSeasonId}/roster/{playerId}",
            method = RequestMethod.PUT)
   public String updatePlayer(@PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @PathVariable String playerId, @Valid PlayerForm form, BindingResult result)
   {
      if (result.hasErrors()) {
         return "players/editPlayer";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final TeamSeasonId identifier = new TeamSeasonId(teamSeason.getId());
      final PersonEntry person = personRepository.findOne(form.getPerson());
      final PlayerId playerIdentifier = new PlayerId(playerId);

      final PlayerDTO dto =
               new PlayerDTO(playerIdentifier, person, teamSeason, person.getFullName(), form.getRole(),
                  form.getStatus(), form.getJerseyNumber(), form.getPosition(), form.isCaptain(),
                  form.getDepth(), form.getHeight(), form.getWeight(), now, user);

      final EditPlayerCommand payload = new EditPlayerCommand(identifier, dto);
      try {
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      }
      catch (final Exception e) {
         result.reject(e.getMessage());
         return "players/editPlayer";
      }
      return "redirect:/admin/teams/" + teamSeason.getTeam().getId() + "/seasons/" +
      teamSeason.getId();
   }

   @RequestMapping(value = "/admin/teamSeasons/{teamSeasonId}/roster/{playerId}",
            method = RequestMethod.DELETE)
   public String deletePlayer(@PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @PathVariable String playerId)
   {
      final TeamSeasonId identifier = new TeamSeasonId(teamSeason.getId());
      final PlayerId playerIdentifier = new PlayerId(playerId);
      final DropPlayerCommand payload = new DropPlayerCommand(identifier, playerIdentifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/teams/" + teamSeason.getTeam().getId() + "/seasons/" +
      teamSeason.getId();
   }

   @RequestMapping(value = "/admin/teamSeasons/{teamSeasonId}/roster/new",
            method = RequestMethod.GET)
   public String newPlayer(@PathVariable("teamSeasonId") TeamSeasonEntry teamSeason, Model model) {
      final SearchPeopleForm searchForm = new SearchPeopleForm();
      final PlayerForm form = new PlayerForm();

      model.addAttribute("searchForm", searchForm);
      model.addAttribute("playerForm", form);
      model.addAttribute("teamSeason", teamSeason);
      return "players/newPlayer";
   }

   @RequestMapping(value = "/admin/teamSeasons/{teamSeasonId}/roster/{playerId}/edit",
            method = RequestMethod.GET)
   public String editPlayer(@PathVariable("teamSeasonId") TeamSeasonEntry teamSeason,
      @PathVariable("playerId") PlayerEntry player, Model model)
   {

      final PlayerForm form = new PlayerForm();
      form.setPerson(player.getPerson().getId());
      form.setJerseyNumber(player.getJerseyNumber());
      form.setPosition(player.getPosition());
      form.setRole(player.getRole());
      form.setStatus(player.getStatus());
      form.setCaptain(player.isCaptain());
      form.setDepth(player.getDepth());
      form.setHeight(player.getHeight());
      form.setWeight(player.getWeight());

      model.addAttribute("playerForm", form);
      model.addAttribute("teamSeason", teamSeason);
      model.addAttribute("player", player);
      return "players/editPlayer";
   }
}
