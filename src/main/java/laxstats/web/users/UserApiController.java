package laxstats.web.users;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.users.CreateUser;
import laxstats.api.users.DeleteUser;
import laxstats.api.users.UpdateUser;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;
import laxstats.query.teams.TeamEntry;
import laxstats.query.teams.TeamQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;
import laxstats.web.teams.TeamNotFoundException;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code UserApiController} is a RESTful controller for remote clients accessing user resources.
 * Security restrictions apply.
 */
@RestController
public class UserApiController extends ApplicationController {

   private final TeamQueryRepository teamRepository;

   @Autowired
   private UserValidator userValidator;

   /**
    * Creates a {@code UserApiController} with the given arguments.
    *
    * @param teamRepository
    * @param userRepository
    * @param commandBus
    */
   @Autowired
   public UserApiController(TeamQueryRepository teamRepository, UserQueryRepository userRepository,
      CommandBus commandBus) {
      super(userRepository, commandBus);
      this.teamRepository = teamRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(userValidator);
   }

   /*---------- Admin methods ----------*/

   /**
    * GET Returns a collection of users ordered by last name. If the user is associated with a team,
    * only other associated users will be returned. Otherwise, all users will be returned.
    *
    * @return
    */
   @RequestMapping(value = "/api/users/", method = RequestMethod.GET)
   public List<UserResource> index() {
      final UserEntry currentUser = getCurrentUser();
      final List<UserResource> list = new ArrayList<>();

      Iterable<UserEntry> users = null;
      final TeamEntry team = currentUser.getTeam();
      if (team == null) {
         users = userRepository.findAllByOrderByLastNameAsc();
      }
      else {
         // Only fetch the users associated with the current user's team
         users = userRepository.findByTeamOrderByLastNameAsc(team.getId());
      }

      for (final UserEntry each : users) {
         final UserResource user =
            new UserResourceImpl(each.getId(), each.getTeam()
               .getId(), each.getEmail(), null, each.isEnabled(), each.getFirstName(),
               each.getLastName(), each.getRole());
         list.add(user);
      }

      return list;
   }

   /**
    * GET Returns the user resource matching the given identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
   public UserResource show(@PathVariable String id) {
      final UserEntry user = userRepository.findOne(id);
      if (user == null) {
         throw new UserNotFoundException(id);
      }
      return new UserResourceImpl(user.getId(), user.getTeam()
         .getId(), user.getEmail(), null, user.isEnabled(), user.getFirstName(), user.getLastName(),
         user.getRole());
   }

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/users", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody UserResourceImpl resource,
      HttpServletRequest req)
   {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry currentUser = getCurrentUser();
      final UserId identifier = new UserId();

      final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      final String encodedPassword = encoder.encode(resource.getPassword());

      TeamEntry team = null;
      if (resource.getTeamId() != null) {
         team = teamRepository.findOne(resource.getTeamId());
         if (team == null) {
            throw new TeamNotFoundException(resource.getTeamId());
         }
      }

      final UserDTO dto =
         new UserDTO(identifier.toString(), resource.getEmail(), encodedPassword,
            resource.getFirstName(), resource.getLastName(), team, req.getRemoteAddr(),
            resource.isEnabled(), resource.getRole(), now, currentUser, now, currentUser);

      final CreateUser payload = new CreateUser(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param id
    * @param resource
    * @param req
    * @return
    */
   @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable String id,
      @Valid @RequestBody UserResourceImpl resource, HttpServletRequest req)
   {
      final UserEntry user = userRepository.findOne(id);
      if (user == null) {
         throw new UserNotFoundException(id);
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry currentUser = getCurrentUser();
      final UserId identifier = new UserId(id);

      // Update the user's IP address if the current user is the user represented by the resource.
      final String ipAddress = currentUser.getId()
         .equals(user.getId()) ? req.getRemoteAddr() : user.getIpAddress();

      // Encrypt the new password
      final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      final String encodedPassword = encoder.encode(resource.getPassword());

      TeamEntry team = null;
      if (resource.getTeamId() != null) {
         team = teamRepository.findOne(resource.getTeamId());
         if (team == null) {
            throw new TeamNotFoundException(resource.getTeamId());
         }
      }

      final UserDTO dto =
         new UserDTO(identifier.toString(), resource.getEmail(), encodedPassword,
            resource.getFirstName(), resource.getLastName(), team, ipAddress, resource.isEnabled(),
            resource.getRole(), now, currentUser);

      final UpdateUser payload = new UpdateUser(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param id
    */
   @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   @PreAuthorize(value = "ROLE_SUPERADMIN")
   public void delete(@PathVariable String id) {
      final boolean exists = userRepository.exists(id);
      if (!exists) {
         throw new UserNotFoundException(id);
      }
      checkDelete(id);

      final DeleteUser payload = new DeleteUser(new UserId(id));
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Utility methods ----------*/

   private void checkDelete(String userId) {
      final UserEntry currentUser = getCurrentUser();
      if (currentUser.getId()
         .equals(userId)) {
         throw new DeleteSameUserException();
      }

      final int foundGames = userRepository.countGamesCreatedOrModified(userId);
      final int foundPlays = userRepository.countPlaysCreatedOrModified(userId);
      if (foundGames > 0 || foundPlays > 0) {
         throw new DeleteUserWithHistoryException();
      }
   }
}
