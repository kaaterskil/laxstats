package laxstats.web.site;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.people.AddressDTO;
import laxstats.api.people.AddressType;
import laxstats.api.sites.CreateSiteCommand;
import laxstats.api.sites.DeleteSiteCommand;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.api.sites.UpdateSiteCommand;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SiteController extends ApplicationController {
   private final SiteQueryRepository siteRepository;
   private SiteFormValidator siteValidator;

   @InitBinder("SiteForm")
   protected void initBinder(WebDataBinder binder) {
      binder.setValidator(siteValidator);
   }

   @Autowired
   public SiteController(SiteQueryRepository siteRepository, UserQueryRepository userRepository,
      CommandBus commandBus) {
      super(userRepository, commandBus);
      this.siteRepository = siteRepository;
   }

   @Autowired
   public void setSiteValidator(SiteFormValidator siteValidator) {
      this.siteValidator = siteValidator;
   }

   /*---------- Action methods ----------*/

   @RequestMapping(value = "/admin/sites", method = RequestMethod.GET)
   public String index(Model model) {
      model.addAttribute("items", siteRepository.findAll());
      return "sites/index";
   }

   @RequestMapping(value = "/admin/sites", method = RequestMethod.POST)
   public String createSite(@Valid SiteForm form, BindingResult result) {
      if (result.hasErrors()) {
         return "sites/newSite";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SiteId identifier = new SiteId();
      final String addressId = IdentifierFactory.getInstance().generateIdentifier();

      final AddressDTO address =
               new AddressDTO(addressId, null, null, AddressType.SITE, form.getAddress1(),
                  form.getAddress2(), form.getCity(), form.getRegion(), form.getPostalCode(), true, false,
                  user, now, user, now);

      final SiteDTO dto =
               new SiteDTO(identifier, form.getName(), form.getStyle(), form.getSurface(), address,
                  form.getDirections(), now, user, now, user);

      final CreateSiteCommand payload = new CreateSiteCommand(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/sites";
   }

   @RequestMapping(value = "/admin/sites/{siteId}", method = RequestMethod.GET)
   public String show(@PathVariable("siteId") SiteEntry site, Model model) {
      model.addAttribute("site", site);
      return "sites/show";
   }

   @RequestMapping(value = "/admin/sites/{siteId}", method = RequestMethod.PUT)
   public String updateSite(@PathVariable("siteId") SiteEntry site, @Valid SiteForm form,
      BindingResult result)
   {
      if (result.hasErrors()) {
         return "sites/editSite";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final SiteId identifier = new SiteId(site.getId());

      final AddressDTO address =
               new AddressDTO(site.getAddress().getId(), site, null, AddressType.SITE, form.getAddress1(),
                  form.getAddress2(), form.getCity(), form.getRegion(), form.getPostalCode(), true, false,
                  user, now);

      final SiteDTO dto =
               new SiteDTO(identifier, form.getName(), form.getStyle(), form.getSurface(), address,
                  form.getDirections(), now, user);

      final UpdateSiteCommand payload = new UpdateSiteCommand(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/sites";
   }

   @RequestMapping(value = "/admin/sites/{siteId}", method = RequestMethod.DELETE)
   public String deleteSite(@PathVariable String siteId) {
      final SiteId identifier = new SiteId(siteId);
      final DeleteSiteCommand payload = new DeleteSiteCommand(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/sites";
   }

   @RequestMapping(value = "/admin/sites/new", method = RequestMethod.GET)
   public String newSite(Model model, HttpServletRequest request) {
      final SiteForm form = new SiteForm();
      form.setSurface(Surface.UNKNOWN);
      form.setStyle(SiteStyle.UNKNOWN);

      model.addAttribute("siteForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "sites/newSite";
   }

   @RequestMapping(value = "/admin/sites/{siteId}/edit", method = RequestMethod.GET)
   public String editSite(@PathVariable("siteId") SiteEntry site, Model model,
      HttpServletRequest request)
   {
      final SiteForm form = new SiteForm();

      form.setName(site.getName());
      form.setStyle(site.getStyle());
      form.setSurface(site.getSurface());
      form.setDirections(site.getDirections());

      final AddressEntry address = site.getAddress();
      form.setAddress1(address.getAddress1());
      form.setAddress2(address.getAddress2());
      form.setCity(address.getCity());
      form.setRegion(address.getRegion());
      form.setPostalCode(address.getPostalCode());

      model.addAttribute("siteForm", form);
      model.addAttribute("siteId", site.getId());
      model.addAttribute("back", request.getHeader("Referer"));
      return "sites/editSite";
   }
}
