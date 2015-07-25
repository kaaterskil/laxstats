package laxstats.web.people;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import laxstats.TestUtils;
import laxstats.api.people.ContactMethod;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.people.PersonId;
import laxstats.query.people.PersonEntry;
import laxstats.query.people.PersonQueryRepository;
import laxstats.web.AbstractIntegrationTest;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonApiControllerIT extends AbstractIntegrationTest {
   private static String BASE_REQUEST_URL = AbstractIntegrationTest.BASE_URL + "/api/people/";

   @Autowired
   private PersonQueryRepository repository;

   private final List<PersonEntry> people = new ArrayList<>();

   @Before
   public void setup() {
      createSecureContext();

      repository.deleteAll();

      PersonEntry entity = new PersonEntry();
      entity.setBirthdate(LocalDate.parse("1992-11-04"));
      entity.setCollege("Russell Sage Collee");
      entity.setCreatedAt(LocalDateTime.now());
      entity.setFirstName("Elyse");
      entity.setGender(Gender.FEMALE);
      entity.setId(new PersonId().toString());
      entity.setLastName("Feldman");
      entity.setModifiedAt(LocalDateTime.now());
      entity.setNickname("Leesie");

      entity.setFullName(PersonEntry.computeFullName(entity));

      entity = repository.save(entity);
      people.add(entity);
   }

   /*---------- Public method tests ----------*/

   @Test
   public void personNotFound() throws Exception {
      final String id = new PersonId().toString();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isNotFound());
   }

   @Test
   public void readPerson() throws Exception {
      final PersonEntry person = people.get(0);
      final String id = person.getId();
      final String getUrl = BASE_REQUEST_URL + id;

      mockMvc.perform(get(getUrl))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(id)))
         .andExpect(jsonPath("$.firstName", is(person.getFirstName())));
   }

   @Test
   public void readPeople() throws Exception {
      final PersonEntry person = people.get(0);

      mockMvc.perform(get(BASE_REQUEST_URL))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].id", is(person.getId())))
         .andExpect(jsonPath("$[0].firstName", is(person.getFirstName())));
   }

   /*---------- Admin method tests ----------*/

   @Test
   public void createPerson() throws Exception {
      final PersonResource resource = TestUtils.newPersonResource();

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.firstName", is(resource.getFirstName())));
   }

   @Test
   public void createPersonWithValidationError() throws Exception {
      final PersonResource resource = TestUtils.newPersonResource();
      resource.setLastName(null);

      mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void updatePerson() throws Exception {
      final PersonEntry entity = people.get(0);
      final String putUrl = BASE_REQUEST_URL + entity.getId();

      final PersonResource resource =
         new PersonResourceImpl(entity.getId(), entity.getPrefix(), entity.getFirstName(),
            entity.getMiddleName(), entity.getLastName(), entity.getSuffix(), entity.getNickname(),
            entity.getFullName(), entity.getGender(), entity.getDominantHand(),
            entity.getBirthdate()
               .toString(), entity.getCollege());
      resource.setMiddleName("Sondra");
      resource.setDominantHand(DominantHand.RIGHT);

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.id", is(resource.getId())))
         .andExpect(jsonPath("$.middleName", is("Sondra")));
   }

   @Test
   public void updatePersonWithValidationError() throws Exception {
      final PersonEntry entity = people.get(0);
      final String putUrl = BASE_REQUEST_URL + entity.getId();

      final PersonResource resource =
         new PersonResourceImpl(entity.getId(), entity.getPrefix(), entity.getFirstName(),
            entity.getMiddleName(), entity.getLastName(), entity.getSuffix(), entity.getNickname(),
            entity.getFullName(), entity.getGender(), entity.getDominantHand(),
            entity.getBirthdate()
               .toString(), entity.getCollege());
      resource.setLastName(null);

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deletePerson() throws Exception {
      final PersonEntry entity = people.get(0);
      final String deleteUrl = BASE_REQUEST_URL + entity.getId();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser()))
         .andExpect(status().isOk());
   }

   /*--------- Address method tests ----------*/

   @Test
   public void createAddress() throws Exception {
      final PersonResource person = newPerson();

      final AddressResource resource = TestUtils.newAddressResource();
      final String personId = person.getId();
      final String postUrl = BASE_REQUEST_URL + personId + "/addresses";

      mockMvc.perform(post(postUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.address1", is(resource.getAddress1())));
   }

   @Test
   public void updateAddress() throws Exception {
      final AddressResource resource = newAddress();
      resource.setAddress1("123 Main Street");

      final String putUrl =
         BASE_REQUEST_URL + resource.getPersonId() + "/addresses/" + resource.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.id", is(resource.getId())))
         .andExpect(jsonPath("$.address1", is(resource.getAddress1())));
   }

   @Test
   public void updateAddressWithInvalidValue() throws Exception {
      final AddressResource resource = newAddress();
      resource.setPostalCode("0000");

      final String putUrl =
         BASE_REQUEST_URL + resource.getPersonId() + "/addresses/" + resource.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteAddress() throws Exception {
      final AddressResource resource = newAddress();
      final String deleteUrl =
         BASE_REQUEST_URL + resource.getPersonId() + "/addresses/" + resource.getId();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser()))
         .andExpect(status().isOk());
   }

   /*---------- Contact method tests ----------*/

   @Test
   public void createContact() throws Exception {
      final PersonResource person = newPerson();
      final String personId = person.getId();

      final ContactResource resource = TestUtils.newContactResource();
      resource.setPersonId(personId);
      final String postUrl = BASE_REQUEST_URL + personId + "/contacts";

      mockMvc.perform(post(postUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isCreated())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.value", is(resource.getValue())));
   }

   @Test
   public void updateContact() throws Exception {
      final ContactResource resource = newContact();
      resource.setMethod(ContactMethod.EMAIL);
      resource.setValue("john@example.com");

      final String putUrl =
         BASE_REQUEST_URL + resource.getPersonId() + "/contacts/" + resource.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.id", is(resource.getId())))
         .andExpect(jsonPath("$.value", is(resource.getValue())));
   }

   @Test
   public void updateContactWithInvalidValue() throws Exception {
      final ContactResource resource = newContact();
      resource.setValue(null);

      final String putUrl =
         BASE_REQUEST_URL + resource.getPersonId() + "/contacts/" + resource.getId();

      mockMvc.perform(put(putUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andExpect(status().isBadRequest())
         .andExpect(content().contentType(contentType))
         .andExpect(jsonPath("$.fieldErrors", hasSize(1)));
   }

   @Test
   public void deleteContact() throws Exception {
      final ContactResource resource = newContact();
      final String deleteUrl =
         BASE_REQUEST_URL + resource.getPersonId() + "/contacts/" + resource.getId();

      mockMvc.perform(delete(deleteUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser()))
         .andExpect(status().isOk());
   }

   /*---------- Utilities ----------*/

   private PersonResource newPerson() throws Exception {
      final PersonResource person = TestUtils.newPersonResource();
      final byte[] result = mockMvc.perform(post(BASE_REQUEST_URL).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(person)))
         .andReturn()
         .getResponse()
         .getContentAsByteArray();
      return (PersonResource)TestUtils.convertJsonToObject(result, PersonResourceImpl.class);

   }

   private AddressResource newAddress() throws Exception {
      final PersonResource person = newPerson();

      final AddressResource resource = TestUtils.newAddressResource();
      final String personId = person.getId();
      final String postUrl = BASE_REQUEST_URL + personId + "/addresses";

      final byte[] result = mockMvc.perform(post(postUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andReturn()
         .getResponse()
         .getContentAsByteArray();
      return (AddressResource)TestUtils.convertJsonToObject(result, AddressResourceImpl.class);
   }

   private ContactResource newContact() throws Exception {
      final PersonResource person = newPerson();
      final String personId = person.getId();

      final ContactResource resource = TestUtils.newContactResource();
      resource.setPersonId(personId);
      final String postUrl = BASE_REQUEST_URL + personId + "/contacts";

      final byte[] result = mockMvc.perform(post(postUrl).with(superadmin)
         .header(AUTH_HEADER_NAME, createTokenForUser())
         .contentType(contentType)
         .content(TestUtils.convertObjectToJson(resource)))
         .andReturn()
         .getResponse()
         .getContentAsByteArray();
      return (ContactResource)TestUtils.convertJsonToObject(result, ContactResourceImpl.class);
   }

}
