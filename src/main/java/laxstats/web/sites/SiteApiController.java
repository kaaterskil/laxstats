package laxstats.web.sites;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressType;
import laxstats.api.sites.CreateSiteCommand;
import laxstats.api.sites.DeleteSiteCommand;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.UpdateSiteCommand;
import laxstats.query.sites.SiteEntry;
import laxstats.query.sites.SiteQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

@RestController
@RequestMapping(value = "/admin/api/sites")
public class SiteApiController extends ApplicationController {

   private final SiteQueryRepository siteRepository;
   private SiteFormValidator siteValidator;

   @Autowired
   public SiteApiController(SiteQueryRepository siteRepository, UserQueryRepository userRepository,
      CommandBus commandBus) {
      super(userRepository, commandBus);
      this.siteRepository = siteRepository;
   }

   @Autowired
   public void setSiteValidator(SiteFormValidator siteValidator) {
      this.siteValidator = siteValidator;
   }

   @InitBinder(value = "SiteInfo")
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(siteValidator);
   }

   /*---------- Action methods ----------*/

   /**
    * GET
    *
    * @return
    */
   @RequestMapping(method = RequestMethod.GET)
   public List<SiteInfo> siteIndex() {
      final Iterable<SiteEntry> sites = siteRepository.findAll();

      final List<SiteInfo> list = new ArrayList<>();
      for (final SiteEntry each : sites) {
         final SiteInfo resource = new SiteInfo(each.getId(), each.getName(), each.getStyle(),
            each.getSurface(), each.getDirections());
         list.add(resource);
      }
      return list;
   }

   /**
    * GET
    *
    * @param site
    * @return
    */
   @RequestMapping(value = "/{siteId}", method = RequestMethod.GET)
   public SiteInfo showSite(@PathVariable("siteId") SiteEntry site) {
      if (site == null) {
         throw new IllegalArgumentException("Site not found");
      }
      return new SiteInfo(site.getId(), site.getName(), site.getStyle(), site.getSurface(),
         site.getDirections());
   }

   /**
    * GET
    *
    * @return
    */
   @RequestMapping(value = "/new", method = RequestMethod.GET)
   public SiteInfo newSite() {
      return new SiteInfo();
   }

   /**
    * POST
    *
    * @param resource
    * @param bindingResult
    * @return
    */
   @RequestMapping(method = RequestMethod.POST)
   public SiteInfo createSite(@Valid @RequestBody SiteInfo resource, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return resource;
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SiteId identifier = new SiteId();
      final String addressId = IdentifierFactory.getInstance().generateIdentifier();

      final AddressDTO address = new AddressDTO(addressId, null, null, AddressType.SITE,
         resource.getAddress1(), resource.getAddress2(), resource.getCity(), resource.getRegion(),
         resource.getPostalCode(), true, false, user, now, user, now);

      final SiteDTO dto = new SiteDTO(identifier, resource.getName(), resource.getStyle(),
         resource.getSurface(), address, resource.getDirections(), now, user, now, user);

      final CreateSiteCommand payload = new CreateSiteCommand(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      resource.setId(identifier.toString());
      return resource;
   }

   /**
    * PUT
    *
    * @param site
    * @param resource
    * @param bindingResult
    * @return
    */
   @RequestMapping(value = "/{siteId}", method = RequestMethod.PUT)
   public SiteInfo updateSite(@PathVariable("siteId") SiteEntry site,
      @Valid @RequestBody SiteInfo resource, BindingResult bindingResult)
   {
      if (site == null) {
         throw new IllegalArgumentException("Site not found");
      }
      if (bindingResult.hasErrors()) {
         return resource;
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SiteId identifier = new SiteId(site.getId());

      final AddressDTO address = new AddressDTO(site.getAddress().getId(), site, null,
         AddressType.SITE, resource.getAddress1(), resource.getAddress2(), resource.getCity(),
         resource.getRegion(), resource.getPostalCode(), true, false, user, now);

      final SiteDTO dto = new SiteDTO(identifier, resource.getName(), resource.getStyle(),
         resource.getSurface(), address, resource.getDirections(), now, user);

      final UpdateSiteCommand payload = new UpdateSiteCommand(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return resource;
   }

   /**
    * DELETE
    *
    * @param siteId
    */
   @RequestMapping(value = "/{siteId}", method = RequestMethod.DELETE)
   public void deleteSite(@PathVariable("siteId") String siteId) {
      checkDelete(siteId);

      final SiteId identifier = new SiteId(siteId);
      final DeleteSiteCommand payload = new DeleteSiteCommand(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Methods ----------*/

   private void checkDelete(String siteId) {
      final int foundTeams = siteRepository.countTeams(siteId);
      if (foundTeams > 0) {
         throw new IllegalArgumentException("Cannot delete site with associated teams.");
      }

      final int foundGames = siteRepository.countGames(siteId);
      if (foundGames > 0) {
         throw new IllegalArgumentException("Cannot delete site with associated games.");
      }
   }
}
