package laxstats;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.people.Gender;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.PersonEntry;
import laxstats.web.people.AddressForm;
import laxstats.web.seasons.SeasonForm;
import laxstats.web.sites.SiteForm;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class TestUtils {

   /*---------- Forms ----------*/

   /**
    * Returns a completed {@code AddressForm} for a new address.
    *
    * @return
    */
   public static AddressForm newAddressForm() {
      final AddressForm form = new AddressForm();
      form.setAddress1("340 Tall Pine Drive");
      form.setAddress2("Unit 32");
      form.setCity("Sudbury");
      form.setPersonId("person");
      form.setPostalCode("01776-4616");
      form.setPrimary(true);
      form.setRegion(Region.MA);
      form.setType(AddressType.HOME);
      return form;
   }

   /**
    * Returns a completed {@code SeasonForm} for a new season.
    *
    * @return
    */
   public static SeasonForm newSeasonForm() {
      final SeasonForm form = new SeasonForm();
      form.setDescription("2014-2015 Season");
      form.setEndsOn(LocalDate.parse("2015-06-30"));
      form.setStartsOn(LocalDate.parse("2014-07-01"));
      return form;
   }

   /**
    * Returns a completed {@code SiteForm} for a new site.
    *
    * @return
    */
   public static SiteForm newSiteForm() {
      final SiteForm form = new SiteForm();
      form.setAddress1("390 Lincoln Road");
      form.setCity("Sudbury");
      form.setName("Lincoln Sudbury Regional High School");
      form.setPostalCode("01776");
      form.setRegion(Region.MA);
      form.setStyle(SiteStyle.COMPETITION);
      form.setSurface(Surface.GRASS);
      return form;
   }

   /*---------- Entities ----------*/

   /**
    * Returns a {@code PersonEntry} with a primary home address.
    * 
    * @return
    */
   public static PersonEntry getPersonWithPrimaryAddress() {
      final PersonEntry person = getPerson();
      person.addAddress(getHomeAddress());
      return person;
   }

   /**
    * Returns a new {@code PersonEntry} with no address or contacts.
    *
    * @return
    */
   public static PersonEntry getPerson() {
      final PersonEntry person = new PersonEntry();
      person.setBirthdate(LocalDate.parse("1988-02-15"));
      person.setCollege("Colgate University");
      person.setCreatedAt(LocalDateTime.now());
      person.setFirstName("Stanley");
      person.setGender(Gender.MALE);
      person.setLastName("Caple");
      person.setMiddleName("NMI");
      person.setModifiedAt(LocalDateTime.now());
      person.setNickname("Stan");
      person.setParentReleased(true);
      person.setParentReleaseSentOn(LocalDate.parse("2014-10-05"));
      person.setParentReleaseReceivedOn(LocalDate.parse("2014-10-12"));

      return person;
   }

   /**
    * Returns a new {@code AddressEntry} of type AddressType.HOME.
    *
    * @return
    */
   public static AddressEntry getHomeAddress() {
      final AddressEntry address = new AddressEntry();
      address.setAddress1("340 Tall Pine Drive");
      address.setAddress2("Unit 32");
      address.setAddressType(AddressType.HOME);
      address.setCity("Sudbury");
      address.setCreatedAt(LocalDateTime.now());
      address.setModifiedAt(LocalDateTime.now());
      address.setPostalCode("01776");
      address.setPrimary(true);
      address.setRegion(Region.MA);
      return address;
   }

   protected TestUtils() {
   }
}
