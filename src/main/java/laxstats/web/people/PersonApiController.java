package laxstats.web.people;

import java.util.ArrayList;
import java.util.List;

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
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;
import laxstats.web.ApplicationController;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code PersonApiController} is a RESTful controller providing endpoints for remote clients
 * accessing person resources. Security restrictions apply.
 */
@RestController
public class PersonApiController extends ApplicationController {

   /**
    * Returns a {@code Sort} object capable of ordering people by last name, first name, in
    * ascending order.
    *
    * @return
    */
   private static Sort personSortInstance() {
      final List<Sort.Order> orders = new ArrayList<>();
      orders.add(new Sort.Order(Sort.Direction.ASC, "lastName"));
      orders.add(new Sort.Order(Sort.Direction.ASC, "firstName"));
      return new Sort(orders);
   }

   private final PersonQueryRepository personRepository;

   @Autowired
   private PersonValidator personValidator;
   @Autowired
   private AddressValidator addressValidator;
   @Autowired
   private ContactValidator contactValidator;

   /**
    * Creates a {@code PersonApiController} with the given arguments.
    *
    * @param userRepository
    * @param commandBus
    * @param personRepository
    */
   @Autowired
   public PersonApiController(UserQueryRepository userRepository, CommandBus commandBus,
      PersonQueryRepository personRepository) {
      super(userRepository, commandBus);
      this.personRepository = personRepository;
   }

   @InitBinder(value = "personResource")
   protected void personInitBinder(WebDataBinder binder) {
      binder.setValidator(personValidator);
   }

   @InitBinder(value = "addressResource")
   protected void addressInitBinder(WebDataBinder binder) {
      binder.setValidator(addressValidator);
   }

   @InitBinder(value = "contactResource")
   protected void contactInitBinder(WebDataBinder binder) {
      binder.setValidator(contactValidator);
   }

   /*---------- Public action methods ----------*/

   /**
    * GET Returns a collection of people ordered by last name, first name.
    *
    * @return
    */
   @RequestMapping(value = "/api/people", method = RequestMethod.GET)
   public List<PersonResource> index() {
      final Iterable<PersonEntry> people = personRepository.findAll(personSortInstance());

      final List<PersonResource> list = new ArrayList<>();
      for (final PersonEntry each : people) {
         final PersonResource resource =
            new PersonResourceImpl(each.getId(), each.getPrefix(), each.getFirstName(),
               each.getMiddleName(), each.getLastName(), each.getSuffix(), each.getNickname(),
               each.getFullName(), each.getGender(), each.getDominantHand(), each.getBirthdate()
                  .toString(), each.getCollege());
         list.add(resource);
      }
      return list;
   }

   /**
    * GET Returns the person resource matching the given identifier.
    *
    * @param id
    * @return
    */
   @RequestMapping(value = "/api/people/{id}", method = RequestMethod.GET)
   public PersonResource show(@PathVariable("id") String id) {
      final PersonEntry entity = personRepository.findOne(id);
      if (entity == null) {
         throw new PersonNotFoundException(id);
      }

      return new PersonResourceImpl(entity.getId(), entity.getPrefix(), entity.getFirstName(),
         entity.getMiddleName(), entity.getLastName(), entity.getSuffix(), entity.getNickname(),
         entity.getFullName(), entity.getGender(), entity.getDominantHand(), entity.getBirthdate()
            .toString(), entity.getCollege());
   }

   /*---------- Admin action methods ----------*/

   /**
    * POST
    *
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/people", method = RequestMethod.POST)
   public ResponseEntity<?> create(@Valid @RequestBody PersonResourceImpl resource) {
      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId();

      final PersonDTO dto =
         new PersonDTO(identifier, resource.getPrefix(), resource.getFirstName(),
            resource.getMiddleName(), resource.getLastName(), resource.getSuffix(),
            resource.getNickname(), resource.getFullName(), resource.getGender(),
            resource.getDominantHand(), resource.getBirthdateAsLocalDate(), resource.getCollege(),
            user, now, user, now);

      final CreatePerson payload = new CreatePerson(identifier, dto);
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
   @RequestMapping(value = "/api/people/{id}", method = RequestMethod.PUT)
   public ResponseEntity<?> update(@PathVariable("id") String id,
      @Valid @RequestBody PersonResourceImpl resource)
   {
      final boolean exists = personRepository.exists(id);
      if (!exists) {
         throw new PersonNotFoundException(id);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(id);

      final PersonDTO dto =
         new PersonDTO(identifier, resource.getPrefix(), resource.getFirstName(),
            resource.getMiddleName(), resource.getLastName(), resource.getSuffix(),
            resource.getNickname(), resource.getFullName(), resource.getGender(),
            resource.getDominantHand(), resource.getBirthdateAsLocalDate(), resource.getCollege(),
            user, now);

      final UpdatePerson payload = new UpdatePerson(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param id
    */
   @RequestMapping(value = "/api/people/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void delete(@PathVariable("id") String id) {
      final PersonEntry entity = personRepository.findOne(id);
      if (entity == null) {
         throw new PersonNotFoundException(id);
      }

      checkDelete(entity);

      final DeletePerson payload = new DeletePerson(new PersonId(id));
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /**
    * POST
    *
    * @param personId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/people/{personId}/addresses", method = RequestMethod.POST)
   public ResponseEntity<?> createAddress(@PathVariable("personId") String personId,
      @Valid @RequestBody AddressResourceImpl resource)
   {
      final PersonEntry person = personRepository.findOne(personId);
      if (person == null) {
         throw new PersonNotFoundException(personId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(personId);
      final String addressId = IdentifierFactory.getInstance()
         .generateIdentifier();

      final AddressDTO dto =
         new AddressDTO(addressId, null, person, resource.getType(), resource.getAddress1(),
            resource.getAddress2(), resource.getCity(), resource.getRegion(),
            resource.getPostalCode(), resource.isPrimary(), resource.isDoNotUse(), user, now, user,
            now);

      final RegisterAddress payload = new RegisterAddress(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param personId
    * @param addressId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/people/{personId}/addresses/{addressId}",
            method = RequestMethod.PUT)
   public ResponseEntity<?> updateAddress(@PathVariable("personId") String personId,
      @PathVariable("addressId") String addressId, @Valid @RequestBody AddressResourceImpl resource)
   {
      final PersonEntry person = personRepository.findOne(personId);
      if (person == null) {
         throw new PersonNotFoundException(personId);
      }
      final boolean exists = personRepository.checkAddressExists(addressId);
      if (!exists) {
         throw new AddressNotFoundException(addressId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(personId);

      final AddressDTO dto =
         new AddressDTO(addressId, null, person, resource.getType(), resource.getAddress1(),
            resource.getAddress2(), resource.getCity(), resource.getRegion(),
            resource.getPostalCode(), resource.isPrimary(), resource.isDoNotUse(), user, now);

      final UpdateAddress payload = new UpdateAddress(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param personId
    * @param addressId
    */
   @RequestMapping(value = "/api/people/{personId}/addresses/{addressId}",
            method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void deleteAddress(@PathVariable("personId") String personId,
      @PathVariable("addressId") String addressId)
   {
      final boolean personExists = personRepository.exists(personId);
      if (!personExists) {
         throw new PersonNotFoundException(personId);
      }
      final boolean addressExists = personRepository.checkAddressExists(addressId);
      if (!addressExists) {
         throw new AddressNotFoundException(addressId);
      }

      final PersonId identifier = new PersonId(personId);
      final DeleteAddress payload = new DeleteAddress(identifier, addressId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /**
    * POST
    *
    * @param personId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/people/{personId}/contacts", method = RequestMethod.POST)
   public ResponseEntity<?> createContact(@PathVariable("personId") String personId,
      @Valid @RequestBody ContactResourceImpl resource)
   {
      final PersonEntry person = personRepository.findOne(personId);
      if (person == null) {
         throw new PersonNotFoundException(personId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(personId);
      final String contactId = IdentifierFactory.getInstance()
         .generateIdentifier();

      final ContactDTO dto =
         new ContactDTO(contactId, person, resource.getMethod(), resource.getValue(),
            resource.isPrimary(), resource.isDoNotUse(), now, user, now, user);

      final RegisterContact payload = new RegisterContact(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.CREATED);
   }

   /**
    * PUT
    *
    * @param personId
    * @param contactId
    * @param resource
    * @return
    */
   @RequestMapping(value = "/api/people/{personId}/contacts/{contactId}", method = RequestMethod.PUT)
   public
      ResponseEntity<?> updateContact(@PathVariable("personId") String personId,
         @PathVariable("contactId") String contactId,
         @Valid @RequestBody ContactResourceImpl resource)
   {
      final PersonEntry person = personRepository.findOne(personId);
      if (person == null) {
         throw new PersonNotFoundException(personId);
      }
      final boolean exists = personRepository.checkContactExists(contactId);
      if (!exists) {
         throw new ContactNotFoundException(contactId);
      }

      final LocalDateTime now = LocalDateTime.now();
      final UserEntry user = getCurrentUser();
      final PersonId identifier = new PersonId(personId);

      final ContactDTO dto =
         new ContactDTO(contactId, person, resource.getMethod(), resource.getValue(),
            resource.isPrimary(), resource.isDoNotUse(), now, user);

      final UpdateContact payload = new UpdateContact(identifier, dto);
      commandBus.dispatch(new GenericCommandMessage<>(payload));

      return new ResponseEntity<>(resource, HttpStatus.OK);
   }

   /**
    * DELETE
    *
    * @param personId
    * @param contactId
    */
   @RequestMapping(value = "/api/people/{personId}/contacts/{contactId}",
            method = RequestMethod.DELETE)
   @ResponseStatus(value = HttpStatus.OK)
   public void deleteContact(@PathVariable("personId") String personId,
      @PathVariable("contactId") String contactId)
   {
      final boolean personExists = personRepository.exists(personId);
      if (!personExists) {
         throw new PersonNotFoundException(personId);
      }
      final boolean contactExists = personRepository.checkContactExists(contactId);
      if (!contactExists) {
         throw new ContactNotFoundException(contactId);
      }

      final PersonId identifier = new PersonId(personId);
      final DeleteContact payload = new DeleteContact(identifier, contactId);
      commandBus.dispatch(new GenericCommandMessage<>(payload));
   }

   /*---------- Utilities ----------*/

   private void checkDelete(PersonEntry person) {
      // Test if the person associated entries in a team roster
      if (person.hasPlayedSeasons()) {
         throw new DeletePersonWithTeamHistoryException();
      }
   }
}
