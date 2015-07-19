package laxstats.web.sites;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressType;
import laxstats.api.sites.CreateSite;
import laxstats.api.sites.DeleteSite;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.UpdateSite;
import laxstats.query.people.AddressEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code SiteApiController} is a RESTful controller providing endpoints for remote clients to
 * access playing field resources. Security restrictions apply.
 */
@RestController
public class SiteApiController extends ApplicationController {

   private final SiteQueryRepository siteRepository;

   @Autowired
   private SiteValidator siteValidator;

   /**
    * Creates a {@code SiteApiController} with the given arguments.
    *
    * @param siteRepository
    * @param userRepository
    * @param commandBus
    */
   @Autowired
   public SiteApiController(SiteQueryRepository siteRepository, UserQueryRepository userRepository,
      CommandBus commandBus) {
      super(userRepository, commandBus);
      this.siteRepository = siteRepository;
   }

   @InitBinder
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(siteValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of playing field resources sorted by region and name.
    *
    * @return
    */
   @RequestMapping(value = "/api/sites", method = RequestMethod.GET)
   public List<SiteResource> index() {
      final List<SiteEntry> sites = (List<SiteEntry>)siteRepository.findAll();

      final List<SiteResource> list = new ArrayList<>();
      if (sites.size() > 0) {
         for (final SiteEntry each : sites) {
            final SiteResource resource = transform(each);
            list.add(resource);
         }
         list.sort(new SiteResourceComparator());
      }
      return list;
   }

   /**
    * GET Returns the playing field resource matching the given aggregate identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/sites/{id}", method = RequestMethod.GET)
   public SiteResource show(@PathVariable String id) {
      final SiteEntry entity = siteRepository.findOne(id);
      if (entity == null) {
         throw new SiteNotFoundException(id);
      }

      return transform(entity);
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/sites", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody SiteResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SiteId identifier = new SiteId();
      final String addressId = IdentifierFactory.getInstance()
         .generateIdentifier();

      final AddressDTO address =
         new AddressDTO(addressId, null, null, AddressType.SITE, resource.getAddress1(),
            resource.getAddress2(), resource.getCity(), resource.getRegion(),
            resource.getPostalCode(), true, false, user, now, user, now);

      final SiteDTO dto =
         new SiteDTO(identifier, resource.getName(), resource.getStyle(), resource.getSurface(),
            address, resource.getDirections(), now, user, now, user);

      final CreateSite payload = new CreateSite(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(identifier.toString());
      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param id
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/sites/{id}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable String id,
      @Valid @RequestBody SiteResourceImpl resource)
   {
      final SiteEntry aggregate = siteRepository.findOne(id);
      if (aggregate == null) {
         throw new SiteNotFoundException(id);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SiteId identifier = new SiteId(id);

      final AddressDTO addressDTO =
         new AddressDTO(id, aggregate, null, AddressType.SITE, resource.getAddress1(),
            resource.getAddress2(), resource.getCity(), resource.getRegion(),
            resource.getPostalCode(), true, false, user, now);

      final SiteDTO dto =
         new SiteDTO(identifier, resource.getName(), resource.getStyle(), resource.getSurface(),
            addressDTO, resource.getDirections(), now, user);

      final UpdateSite payload = new UpdateSite(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param id
    */
   @RequestMapping(value = "/api/sites/{id}", method = RequestMethod.DELETE)
   public void deleteSite(@PathVariable("id") String id) {
      final boolean exists = siteRepository.exists(id);
      if (!exists) {
         throw new SiteNotFoundException(id);
      }

      checkDelete(id);

      final DeleteSite payload = new DeleteSite(new SiteId(id));
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(String siteId) {
      // Validates that the playing field has no associated team.
      final int foundTeams = siteRepository.countTeams(siteId);
      if (foundTeams > 0) {
         throw new DeleteSiteWithTeamsException();
      }

      // Validates that the playing field has no associated games or plays.
      final int foundGames = siteRepository.countGames(siteId);
      if (foundGames > 0) {
         throw new DeleteSiteWithHistoryException();
      }
   }

   private SiteResource transform(SiteEntry entity) {
      final SiteResource resource =
         new SiteResourceImpl(entity.getId(), entity.getName(), entity.getStyle(),
            entity.getSurface(), entity.getDirections());

      final AddressEntry address = entity.getAddress();
      if (address != null) {
         resource.setAddress1(address.getAddress1());
         resource.setAddress2(address.getAddress2());
         resource.setCity(address.getCity());
         resource.setRegion(address.getRegion());
         resource.setPostalCode(address.getPostalCode());
      }

      return resource;
   }

   private static class SiteResourceComparator implements Comparator<SiteResource> {

      @Override
      public int compare(SiteResource o1, SiteResource o2) {
         final String r1 = o1.getRegion()
            .getLabel();
         final String r2 = o2.getRegion()
            .getLabel();
         final String n1 = o1.getName();
         final String n2 = o2.getName();

         return r1.compareToIgnoreCase(r2) < 0 ? -1 : r1.compareToIgnoreCase(r2) > 0 ? 1
            : n1.compareToIgnoreCase(n2);
      }

   }
}
