package laxstats;

import laxstats.api.Region;
import laxstats.api.people.AddressType;
import laxstats.api.people.Gender;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.web.people.AddressForm;
import laxstats.web.seasons.SeasonForm;
import laxstats.web.sites.SiteForm;

import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class TestUtils {

   /**
    * Returns true if the given string is null, empty or contains only whitespace characters, false
    * otherwise.
    *
    * @param str
    * @return
    */
   public static boolean isEmptyOrWhitespace(String str) {
      if (str == null || str.length() == 0) {
         return true;
      }

      boolean hasOnlyWhitespace = true;
      final int len = str.length();
      for (int i = 0; i < len; i++) {
         if (!Character.isWhitespace(str.charAt(i))) {
            hasOnlyWhitespace = false;
            break;
         }
      }
      return hasOnlyWhitespace;
   }

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
      form.setStartsOn(LocalDate.parse("2014-07-01"));
      form.setEndsOn(LocalDate.parse("2015-06-30"));
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

   /**
    * Returns a new {@code AddressEntry} of type AddressType.SITE.
    *
    * @return
    */
   public static AddressEntry getSiteAddress() {
      final AddressEntry address = new AddressEntry();
      address.setAddress1("390 Lincoln Road");
      address.setAddress2("Unit 32");
      address.setAddressType(AddressType.SITE);
      address.setCity("Sudbury");
      address.setCreatedAt(LocalDateTime.now());
      address.setModifiedAt(LocalDateTime.now());
      address.setPostalCode("01776");
      address.setPrimary(true);
      address.setRegion(Region.MA);
      return address;
   }

   /**
    * Returns a new {@code SeasonEntry}
    *
    * @return
    */
   public static SeasonEntry getSeason() {
      final SeasonEntry season = new SeasonEntry();
      season.setDescription("2014-2015 Season");
      season.setStartsOn(LocalDate.parse("2014-07-01"));
      season.setEndsOn(LocalDate.parse("2015-06-30"));
      return season;
   }

   /**
    * Returns a {@code SeasonEntry} with a primary key, and set to the prior year.
    *
    * @return
    */
   public static SeasonEntry getExistingSeason() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      final SeasonEntry season = new SeasonEntry();
      season.setDescription("2013-2014 Season");
      season.setStartsOn(LocalDate.parse("2013-07-01"));
      season.setEndsOn(LocalDate.parse("2014-06-30"));
      season.setId(id);
      return season;
   }

   /**
    * Returns a {@code SiteEntry} with a primary key and address.
    *
    * @return
    */
   public static SiteEntry getExistingSite() {
      final String id = IdentifierFactory.getInstance().generateIdentifier();
      final SiteEntry site = new SiteEntry();
      site.setName("Lincoln Sudbury Regional High School");
      site.setId(id);
      site.setStyle(SiteStyle.COMPETITION);
      site.setSurface(Surface.GRASS);
      site.setCreatedAt(LocalDateTime.now());
      site.setModifiedAt(LocalDateTime.now());

      final AddressEntry address = getSiteAddress();
      site.setAddress(address);
      address.setSite(site);

      return site;
   }

   protected TestUtils() {
   }
}
