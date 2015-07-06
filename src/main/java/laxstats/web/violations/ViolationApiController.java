package laxstats.web.violations;

import java.util.ArrayList;
import java.util.List;

import laxstats.query.users.UserQueryRepository;
import laxstats.query.violations.ViolationEntry;
import laxstats.query.violations.ViolationQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code ViolationApiController} is a RESTful controller for remote clients accessing violation
 * resources. Security restrictions apply.
 */
@RestController
@RequestMapping(value = "/api/violations")
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

   /**
    * GET Returns a collection of violation resource objects ordered by name.
    *
    * @return
    */
   @RequestMapping(method = RequestMethod.GET)
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
    * GET Returns
    * 
    * @param id
    * @return
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   public ViolationResourceImpl show(@PathVariable String id) {
      final ViolationEntry entry = violationRepository.findOne(id);

      return new ViolationResourceImpl(entry.getId(), entry.getName(), entry.getDescription(), entry
         .getCategory(), entry.getPenaltyLength(), entry.isReleasable());
   }

   @RequestMapping(value = "/new", method = RequestMethod.GET)
   public ViolationResourceImpl showNew() {
      return new ViolationResourceImpl();
   }

}
