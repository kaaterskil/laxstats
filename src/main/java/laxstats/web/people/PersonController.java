package laxstats.web.people;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import laxstats.api.people.AddressDTO;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.CreatePerson;
import laxstats.api.people.DeleteAddress;
import laxstats.api.people.DeleteContact;
import laxstats.api.people.DeletePerson;
import laxstats.api.people.PersonDTO;
import laxstats.api.people.PersonId;
import laxstats.api.people.RegisterAddress;
import laxstats.api.people.RegisterContact;
import laxstats.api.people.UpdateAddress;
import laxstats.api.people.UpdateContact;
import laxstats.api.people.UpdatePerson;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.people.PersonSpecifications;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonController extends ApplicationController {
   static private Logger logger = LoggerFactory.getLogger(PersonController.class);
   static private String PACKAGE_NAME = PersonController.class.getPackage().getName();

   private final PersonQueryRepository personRepository;

   @Autowired
   private PersonValidator personValidator;
   @Autowired
   private AddressValidator addressValidator;
   @Autowired
   private ContactValidator contactValidator;

   @Autowired
   public PersonController(PersonQueryRepository personRepository,
      UserQueryRepository userRepository, CommandBus commandBus) {
      super(userRepository, commandBus);
      this.personRepository = personRepository;
   }

   @InitBinder("personForm")
   protected void initPersonBinder(WebDataBinder binder) {
      binder.setValidator(personValidator);
   }

   @InitBinder("addressForm")
   protected void initAddressBinder(WebDataBinder binder) {
      binder.setValidator(addressValidator);
   }

   @InitBinder("contactForm")
   protected void initContactBinder(WebDataBinder binder) {
      binder.setValidator(contactValidator);
   }

   /*---------- Person actions ---------*/

   @RequestMapping(value = "/admin/people", method = RequestMethod.GET)
   public String index(Model model) {
      final SearchPeopleForm form = new SearchPeopleForm();
      model.addAttribute("searchForm", form);
      return "people/index";
   }

   @RequestMapping(value = "/admin/people", method = RequestMethod.POST)
   public String createPerson(@Valid PersonForm personForm, BindingResult result,
      RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".createPerson.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "people/newPerson";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId();

      final PersonDTO dto =
         new PersonDTO(identifier, personForm.getPrefix(), personForm.getFirstName(),
            personForm.getMiddleName(), personForm.getLastName(), personForm.getSuffix(),
            personForm.getNickname(), personForm.getFullName(), personForm.getGender(),
            personForm.getDominantHand(), personForm.isReleased(),
            personForm.getParentReleaseSentOn(), personForm.getParentReleaseReceivedOn(),
            personForm.getBirthdate(), personForm.getCollege(), user, now, user, now);
      logger.debug(proc + "30");

      try {
         final CreatePerson command = new CreatePerson(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(command));
      } catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/people";
   }

   @RequestMapping(value = "/admin/people/{personId}", method = RequestMethod.GET)
   public String showPerson(@PathVariable("personId") PersonEntry person, Model model) {
      model.addAttribute("person", person);
      return "people/showPerson";
   }

   @RequestMapping(value = "/admin/people/{personId}", method = RequestMethod.PUT)
   public String updatePerson(@PathVariable String personId, @Valid PersonForm personForm,
      BindingResult result, RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".updatePerson.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "/people/editPerson";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(personId);

      final PersonDTO dto =
         new PersonDTO(identifier, personForm.getPrefix(), personForm.getFirstName(),
            personForm.getMiddleName(), personForm.getLastName(), personForm.getSuffix(),
            personForm.getNickname(), personForm.getFullName(), personForm.getGender(),
            personForm.getDominantHand(), personForm.isReleased(),
            personForm.getParentReleaseSentOn(), personForm.getParentReleaseReceivedOn(),
            personForm.getBirthdate(), personForm.getCollege(), user, now);
      logger.debug(proc + "30");

      try {
         final UpdatePerson payload = new UpdatePerson(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      } catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/people";
   }

   @RequestMapping(value = "/admin/people/{personId}", method = RequestMethod.DELETE)
   public String deletePerson(@PathVariable String personId) {
      final PersonId identifier = new PersonId(personId);
      final DeletePerson payload = new DeletePerson(identifier);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/people";
   }

   @RequestMapping(value = "/admin/people/new", method = RequestMethod.GET)
   public String newPerson(Model model, HttpServletRequest request) {
      final PersonForm form = new PersonForm();
      model.addAttribute("personForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "people/newPerson";
   }

   @RequestMapping(value = "/admin/people/{personId}/edit", method = RequestMethod.GET)
   public String editPerson(@PathVariable("personId") PersonEntry person, Model model,
      HttpServletRequest request) {
      final PersonForm form = new PersonForm();

      form.setPrefix(person.getPrefix());
      form.setFirstName(person.getFirstName());
      form.setMiddleName(person.getMiddleName());
      form.setLastName(person.getLastName());
      form.setSuffix(person.getSuffix());
      form.setNickname(person.getNickname());

      form.setGender(person.getGender());
      form.setDominantHand(person.getDominantHand());
      form.setBirthdate(person.getBirthdate());
      form.setReleased(person.isParentReleased());
      form.setParentReleaseSentOn(person.getParentReleaseSentOn());
      form.setParentReleaseReceivedOn(person.getParentReleaseReceivedOn());
      form.setCollege(person.getCollege());

      model.addAttribute("personForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "people/editPerson";
   }

   /*---------- Address actions ----------*/

   @RequestMapping(value = "/admin/people/{personId}/addresses", method = RequestMethod.POST)
   public String createAddress(@PathVariable("personId") PersonEntry person,
      @Valid AddressForm addressForm, BindingResult result, RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".createAddress.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "people/newAddress";
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(person.getId());
      final String addressId = IdentifierFactory.getInstance().generateIdentifier();

      final AddressDTO dto =
         new AddressDTO(addressId, null, person, addressForm.getType(), addressForm.getAddress1(),
            addressForm.getAddress2(), addressForm.getCity(), addressForm.getRegion(),
            addressForm.getPostalCode(), addressForm.isPrimary(), addressForm.isDoNotUse(), user,
            now, user, now);
      logger.debug(proc + "30");

      try {
         final RegisterAddress payload = new RegisterAddress(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      } catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/people/" + person.getId();
   }

   @RequestMapping(value = "/admin/{personId}/addresses/{addressId}", method = RequestMethod.PUT)
   public String updateAddress(@PathVariable("personId") PersonEntry person,
      @PathVariable String addressId, @Valid AddressForm addressForm, BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".updateAddress.";

      logger.debug("Entering: " + proc + "10");
      if (bindingResult.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "/people/editAddress";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(person.getId());

      final AddressDTO dto =
         new AddressDTO(addressId, null, person, addressForm.getType(), addressForm.getAddress1(),
            addressForm.getAddress2(), addressForm.getCity(), addressForm.getRegion(),
            addressForm.getPostalCode(), addressForm.isPrimary(), addressForm.isDoNotUse(), user,
            now);
      logger.debug(proc + "30");

      try {
         final UpdateAddress payload = new UpdateAddress(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      } catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/people/" + person.getId();
   }

   @RequestMapping(value = "/admin/people/{personId}/addresses/{addressId}",
      method = RequestMethod.DELETE)
   public String deleteAddress(@PathVariable String personId, @PathVariable String addressId) {
      final PersonId identifier = new PersonId(personId);
      final DeleteAddress payload = new DeleteAddress(identifier, addressId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/people/" + personId;
   }

   @RequestMapping(value = "/admin/people/{personId}/addresses/new", method = RequestMethod.GET)
   public String newAddress(@PathVariable("personId") PersonEntry person, Model model,
      HttpServletRequest request) {
      final AddressForm form = new AddressForm();
      model.addAttribute("person", person);
      model.addAttribute("addressForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "people/newAddress";
   }

   @RequestMapping(value = "/admin/people/{personId}/addresses/{addressId}/edit",
      method = RequestMethod.GET)
   public String editAddress(@PathVariable("personId") PersonEntry person,
      @PathVariable String addressId, Model model, HttpServletRequest request) {
      final AddressEntry address = person.getAddress(addressId);

      final AddressForm form = new AddressForm();
      form.setAddress1(address.getAddress1());
      form.setAddress2(address.getAddress2());
      form.setCity(address.getCity());
      form.setDoNotUse(address.isDoNotUse());
      form.setPostalCode(address.getPostalCode());
      form.setType(address.getAddressType());
      form.setPrimary(address.isPrimary());
      form.setRegion(address.getRegion());

      model.addAttribute("person", person);
      model.addAttribute("addressId", addressId);
      model.addAttribute("addressForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "people/editAddress";
   }

   /*---------- Contact actions ----------*/

   @RequestMapping(value = "/admin/people/{personId}/contacts", method = RequestMethod.POST)
   public String createContact(@PathVariable("personId") PersonEntry person,
      @Valid ContactForm contactForm, BindingResult result, RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".createContact.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "people/newContact";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(person.getId());
      final String contactId = IdentifierFactory.getInstance().generateIdentifier();

      final ContactDTO dto =
         new ContactDTO(contactId, person, contactForm.getMethod(), contactForm.getValue(),
            contactForm.isPrimary(), contactForm.isDoNotUse(), now, user, now, user);
      logger.debug(proc + "30");

      try {
         final RegisterContact payload = new RegisterContact(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      } catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/people/" + person.getId();
   }

   @RequestMapping(value = "/admin/people/{personId}/contacts/{contactId}",
      method = RequestMethod.PUT)
   public String updateContact(@PathVariable("personId") PersonEntry person,
      @PathVariable String contactId, @Valid ContactForm contactForm, BindingResult result,
      RedirectAttributes redirectAttributes) {
      final String proc = PACKAGE_NAME + ".updateContact.";

      logger.debug("Entering: " + proc + "10");
      if (result.hasErrors()) {
         logger.debug("Returning errors: " + proc + "20");
         return "/people/editContact";
      }
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(person.getId());

      final ContactDTO dto =
         new ContactDTO(contactId, person, contactForm.getMethod(), contactForm.getValue(),
            contactForm.isPrimary(), contactForm.isDoNotUse(), now, user);
      logger.debug(proc + "30");

      try {
         final UpdateContact payload = new UpdateContact(identifier, dto);
         commandBus.dispatch(new GenericCommandMessage<>(payload));
      } catch (final Exception e) {
         logger.debug(proc + "40");
         redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
      }

      logger.debug("Leaving: " + proc + "50");
      return "redirect:/admin/people/" + person.getId();
   }

   @RequestMapping(value = "/admin/people/{personId}/contacts/{contactId}",
      method = RequestMethod.DELETE)
   public String deleteContact(@PathVariable String personId, @PathVariable String contactId) {
      final PersonId identifier = new PersonId(personId);
      final DeleteContact payload = new DeleteContact(identifier, contactId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
      return "redirect:/admin/people/" + personId;
   }

   @RequestMapping(value = "/admin/people/{personId}/contacts/new", method = RequestMethod.GET)
   public String newContact(@PathVariable("personId") PersonEntry person, Model model,
      HttpServletRequest request) {
      final ContactForm form = new ContactForm();
      form.setPersonId(person.getId());

      model.addAttribute("person", person);
      model.addAttribute("contactForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "people/newContact";
   }

   @RequestMapping(value = "/admin/people/{personId}/contacts/{contactId}/edit",
      method = RequestMethod.GET)
   public String editContact(@PathVariable("personId") PersonEntry person,
      @PathVariable String contactId, Model model, HttpServletRequest request) {
      final ContactEntry contact = person.getContact(contactId);

      final ContactForm form = new ContactForm();
      form.setId(contactId);
      form.setPersonId(person.getId());
      form.setMethod(contact.getMethod());
      form.setValue(contact.getValue());
      form.setDoNotUse(contact.isDoNotUse());
      form.setPrimary(contact.isPrimary());

      model.addAttribute("person", person);
      model.addAttribute("contactId", contactId);
      model.addAttribute("contactForm", form);
      model.addAttribute("back", request.getHeader("Referer"));
      return "people/editContact";
   }

   /*---------- Ajax methods ----------*/

   @RequestMapping(value = "/api/people/search", method = RequestMethod.POST)
   public String searchPeople(@RequestBody SearchPeopleForm form, Model model) {
      final List<SearchResult> results = doSearch(form);
      model.addAttribute("results", results);
      return "people/searchResults :: resultList";
   }

   @RequestMapping(value = "/api/people/searchData", method = RequestMethod.POST)
   public String searchPeopleData(@RequestBody SearchPeopleForm form, Model model) {
      final List<SearchResult> results = doSearch(form);
      model.addAttribute("results", results);
      return "partials/searchPeopleResults :: resultList";
   }

   private List<SearchResult> doSearch(SearchPeopleForm form) {
      final List<PersonEntry> list =
         personRepository.findAll(PersonSpecifications.search(form), searchSort());

      final List<SearchResult> results = new ArrayList<>();
      for (final PersonEntry each : list) {
         final SearchResult item = new SearchResult(each.getId(), each.getFullName());

         if (each.primaryAddress() != null) {
            final AddressEntry address = each.primaryAddress();
            item.setCity(address.getCity());
            item.setRegion(address.getRegion().getAbbreviation());
            item.setPostalCode(address.getPostalCode());
         }
         if (each.primaryContact() != null) {
            final ContactEntry contact = each.primaryContact();
            item.setContact(contact.getValue());
         }
         results.add(item);
      }
      return results;
   }

   private Sort searchSort() {
      final List<Order> orders = new ArrayList<>();
      orders.add(new Order(Sort.Direction.ASC, "lastName"));
      orders.add(new Order(Sort.Direction.ASC, "firstName"));
      return new Sort(orders);
   }
}
