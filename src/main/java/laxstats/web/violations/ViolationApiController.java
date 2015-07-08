package laxstats.web.violations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.violations.CreateViolation;
import laxstats.api.violations.DeleteViolation;
import laxstats.api.violations.UpdateViolation;
import laxstats.api.violations.ViolationDTO;
import laxstats.api.violations.ViolationId;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code ViolationApiController} is a RESTful controller for remote clients accessing violation
 * resources. Security restrictions apply.
 */
@RestController
public class ViolationApiController extends ApplicationController {

   private final ViolationQueryRepository violationRepository;

   @Autowired
   private ViolationValidator violationValidator;

   /**
    * Creates a {@code ViolationApiController} with the given arguments.
    *
    * @param violationRepository
    * @param userRepository
    * @param commandBus
    */
   @Autowired
   public ViolationApiController(ViolationQueryRepository violationRepository,
      UserQueryRepository userRepository, CommandBus commandBus) {
      super(userRepository, commandBus);
      this.violationRepository = violationRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(violationValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of violation resources ordered by name.
    *
    * @return
    */
   @RequestMapping(value = "/api/violations", method = RequestMethod.GET)
   public List<ViolationResourceImpl> index() {
      final Iterable<ViolationEntry> list = violationRepository.findAllByOrderByNameAsc();

      final List<ViolationResourceImpl> body = new ArrayList<ViolationResourceImpl>();
      for (final ViolationEntry each : list) {
         final ViolationResourceImpl e =
            new ViolationResourceImpl(each.getId(), each.getName(), each.getDescription(), each
               .getCategory(), each.getPenaltyLength(), each.isReleasable());
         body.add(e);
      }
      return body;
   }

   /**
    * GET Returns the violation resource matching the given aggregate identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/violations/{id}", method = RequestMethod.GET)
   public ViolationResource show(@PathVariable String id) {
      final ViolationEntry entry = violationRepository.findOne(id);
      if (entry == null) {
         throw new ViolationNotFoundException(id);
      }

      return new ViolationResourceImpl(entry.getId(), entry.getName(), entry.getDescription(), entry
         .getCategory(), entry.getPenaltyLength(), entry.isReleasable());
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST Creates a new violation from information contained in the given resource.
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/violations", method = RequestMethod.POST)
   @ResponseStatus(value = HttpStatus.CREATED)
   public ViolationResource create(@Valid @RequestBody ViolationResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final ViolationId identifier = new ViolationId();

      final ViolationDTO dto =
         new ViolationDTO(identifier.toString(), resource.getName(), resource.getDescription(),
            resource.getCategory(), resource.getPenaltyLength(), resource.isReleasable(), now, user,
            now, user);

      final CreateViolation command = new CreateViolation(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(command));

      resource.setId(identifier.toString());
      return resource;
   }

   /**
    * PUT Updates an existing violation with information contained in the given resource.
    *
    * @param id
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/violations/{id}", method = RequestMethod.PUT)
   @ResponseStatus(value = HttpStatus.OK)
   public ViolationResource update(@PathVariable String id,
      @Valid @RequestBody ViolationResourceImpl resource)
   {
      final boolean exists = violationRepository.exists(id);
      if (!exists) {
         throw new ViolationNotFoundException(id);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final ViolationId identifier = new ViolationId(id);

      final ViolationDTO dto =
         new ViolationDTO(id, resource.getName(), resource.getDescription(), resource.getCategory(),
            resource.getPenaltyLength(), resource.isReleasable(), now, user);

      final UpdateViolation command = new UpdateViolation(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(command));

      return resource;
   }

   /**
    * DELETE Deletes the violation matching the given unique identifier.
    *
    * @param id
    */
   @RequestMapping(value = "/api/violations/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.NO_CONTENT)
   public void delete(@PathVariable String id) {
      final boolean exists = violationRepository.exists(id);
      if (!exists) {
         throw new ViolationNotFoundException(id);
      }

      final DeleteViolation command = new DeleteViolation(new ViolationId(id));
      commandBus.dispatch(new GenericCommandMessage<>(command));
   }
}
