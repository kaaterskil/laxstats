package laxstats;

import java.io.IOException;

import laxstats.api.Region;
import laxstats.api.games.Schedule;
import laxstats.api.games.Status;
import laxstats.api.people.AddressType;
import laxstats.api.people.ContactMethod;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.players.PlayerStatus;
import laxstats.api.players.Position;
import laxstats.api.players.Role;
import laxstats.api.sites.SiteAlignment;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.api.teamSeasons.TeamStatus;
import laxstats.api.teams.Letter;
import laxstats.api.teams.TeamGender;
import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.query.games.GameEntry;
import laxstats.query.people.AddressEntry;
import laxstats.query.people.ContactEntry;
import laxstats.query.people.PersonEntry;
import laxstats.query.players.PlayerEntry;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teamSeasons.TeamSeasonEntry;
import laxstats.query.teams.TeamEntry;
import laxstats.query.violations.ViolationEntry;
import laxstats.web.games.GameForm;
import laxstats.web.games.GameResourceImpl;
import laxstats.web.people.AddressForm;
import laxstats.web.people.AddressResourceImpl;
import laxstats.web.people.ContactForm;
import laxstats.web.people.ContactResourceImpl;
import laxstats.web.people.PersonForm;
import laxstats.web.people.PersonResourceImpl;
import laxstats.web.players.PlayerForm;
import laxstats.web.players.PlayerResourceImpl;
import laxstats.web.seasons.SeasonForm;
import laxstats.web.seasons.SeasonResourceImpl;
import laxstats.web.sites.SiteForm;
import laxstats.web.sites.SiteResourceImpl;
import laxstats.web.teamSeasons.TeamSeasonForm;
import laxstats.web.teamSeasons.TeamSeasonResourceImpl;
import laxstats.web.teams.TeamForm;
import laxstats.web.teams.TeamResourceImpl;
import laxstats.web.users.UserForm;
import laxstats.web.users.UserResource;
import laxstats.web.users.UserResourceImpl;
import laxstats.web.violations.ViolationForm;
import laxstats.web.violations.ViolationResource;
import laxstats.web.violations.ViolationResourceImpl;

import org.axonframework.domain.IdentifierFactory;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

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

   /**
    * Converts the given object into a JSON document and returns the content as a byte array.
    *
    * @param obj
    * @return
    * @throws IOException
    */
   public static byte[] convertObjectToJson(Object obj) throws IOException {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JodaModule());
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      return mapper.writeValueAsBytes(obj);
   }

   public static Object convertJsonToObject(byte[] src, Class<?> valueType)
      throws JsonParseException, JsonMappingException, IOException
   {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JodaModule());
      return mapper.readValue(src, valueType);
   }

   /*---------- Resources ----------*/

   /**
    * Returns a completed {@code AddressResource} for a new address.
    *
    * @return
    */
   public static AddressResourceImpl newAddressResource() {
      final AddressResourceImpl resource = new AddressResourceImpl();
      resource.setAddress1("340 Tall Pine Drive");
      resource.setAddress2("Unit 32");
      resource.setCity("Sudbury");
      resource.setPersonId("person");
      resource.setPostalCode("01776-4616");
      resource.setPrimary(true);
      resource.setRegion(Region.MA);
      resource.setType(AddressType.HOME);
      return resource;
   }

   /**
    * Returns a completed {@code ContactResource} for a new contact with ContactMethod.TELEPHONE.
    *
    * @return
    */
   public static ContactResourceImpl newContactResource() {
      final ContactResourceImpl resource = new ContactResourceImpl();
      resource.setMethod(ContactMethod.TELEPHONE);
      resource.setValue("888-555-1212");
      resource.setPrimary(true);
      resource.setDoNotUse(false);
      return resource;
   }

   /**
    * Returns a complete {@code GameResource} for a new game. NOTE: the two teams and the site must
    * be assigned.
    *
    * @return
    */
   public static GameResourceImpl newGameResource() {
      final GameResourceImpl resource = new GameResourceImpl();
      resource.setAlignment(SiteAlignment.HOME);
      resource.setDescription("This is a description");
      resource.setSchedule(Schedule.REGULAR);
      resource.setStartsAt("2014-04-01T16:00:00");
      resource.setStatus(Status.SCHEDULED);
      return resource;
   }

   /**
    * Returns a complete {@code PersonResource} for a new person.
    *
    * @return
    */
   public static PersonResourceImpl newPersonResource() {
      final PersonResourceImpl resource = new PersonResourceImpl();
      resource.setBirthdate("1988-09-30");
      resource.setCollege("Colgate University");
      resource.setDominantHand(DominantHand.RIGHT);
      resource.setFirstName("Stanley");
      resource.setGender(Gender.MALE);
      resource.setLastName("Caple");
      resource.setMiddleName("NMI");
      resource.setNickname("Stan");
      resource.setFullName("Stanley Caple");
      return resource;
   }

   /**
    * Returns a complete {@code PlayerResource} for a new player. NOTE: The person and the team
    * season must be applied.
    *
    * @return
    */
   public static PlayerResourceImpl newPlayerResource() {
      final PlayerResourceImpl resource = new PlayerResourceImpl();
      resource.setCaptain(false);
      resource.setJerseyNumber("18");
      resource.setPosition(Position.ATTACK);
      resource.setParentReleaseReceivedOn("2014-10-12");
      resource.setParentReleaseSentOn("2014-10-05");
      resource.setReleased(true);
      return resource;
   }

   /**
    * Returns a completed {@code SeasonResource} for a new season.
    *
    * @return
    */
   public static SeasonResourceImpl newSeasonResource() {
      final SeasonResourceImpl resource = new SeasonResourceImpl();
      resource.setDescription("2014-2015 Season");
      resource.setStartsOn("2014-07-01");
      resource.setEndsOn("2015-06-30");
      return resource;
   }

   /**
    * Returns a completed {@code SiteResource} for a new site.
    *
    * @return
    */
   public static SiteResourceImpl newSiteResource() {
      final SiteResourceImpl resource = new SiteResourceImpl();
      resource.setAddress1("40 Rice Street");
      resource.setCity("Wellesley");
      resource.setName("Wellesley High School");
      resource.setPostalCode("02482");
      resource.setRegion(Region.MA);
      resource.setStyle(SiteStyle.COMPETITION);
      resource.setSurface(Surface.GRASS);
      return resource;
   }

   /**
    * Returns a completed {@code TeamResource} for a new team. NOTE: The home site is null.
    *
    * @return
    */
   public static TeamResourceImpl newTeamResource() {
      final TeamResourceImpl resource = new TeamResourceImpl();
      resource.setAbbreviation("WHS");
      resource.setGender(TeamGender.BOYS);
      resource.setLetter(Letter.VARSITY);
      resource.setName("Wellesley Raiders");
      resource.setRegion(Region.MA);
      resource.setSponsor("Wellesley High School");
      return resource;
   }

   /**
    * Returns a completed {@code TeamSeasonResurce} for a new team season.
    *
    * @return
    */
   public static TeamSeasonResourceImpl newTeamSeasonResource() {
      final TeamEntry team = getExistingTeam();
      final SeasonEntry season = getExistingSeason();

      final TeamSeasonResourceImpl resource = new TeamSeasonResourceImpl();
      resource.setEndsOn("2014-06-30");
      resource.setName("Wellesley Raiders");
      resource.setSeason(season.getId());
      resource.setStartsOn("2013-07-01");
      resource.setStatus(TeamStatus.ACTIVE);
      resource.setTeam(team.getId());
      return resource;
   }

   /**
    * Returns a completed {@code UserResource} for a new user.
    *
    * @return
    */
   public static UserResource newUserResource() {
      final UserResourceImpl resource = new UserResourceImpl();
      resource.setEmail("john@example.com");
      resource.setEnabled(true);
      resource.setFirstName("John");
      resource.setLastName("Doe");
      resource.setPassword("user");
      return resource;
   }

   /**
    * Returns a completed {@code ViolationResource} for a new violation.
    *
    * @return
    */
   public static ViolationResource newViolationResource() {
      final ViolationResourceImpl resource = new ViolationResourceImpl();
      resource.setCategory(PenaltyCategory.PERSONAL_FOUL);
      resource.setDescription("This is a slash");
      resource.setName("Slash");
      resource.setPenaltyLength(PenaltyLength.ONE_MINUTE);
      resource.setReleasable(true);
      return resource;
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
    * Returns a completed {@code ContactForm} for a new contact with ContactMethod.TELEPHONE.
    *
    * @return
    */
   public static ContactForm newContactForm() {
      final ContactForm form = new ContactForm();
      form.setMethod(ContactMethod.TELEPHONE);
      form.setValue("888-555-1212");
      form.setPrimary(true);
      form.setDoNotUse(false);
      return form;
   }

   /**
    * Returns a complete {@code GameForm} for a new game. NOTE: the two teams and the site must be
    * assigned.
    *
    * @return
    */
   public static GameForm newGameForm() {
      final GameForm form = new GameForm();
      form.setAlignment(SiteAlignment.HOME);
      form.setDescription("This is a description");
      form.setSchedule(Schedule.REGULAR);
      form.setStartsAtAsDateTime(LocalDateTime.parse("2014-04-01T16:00:00"));
      form.setStatus(Status.SCHEDULED);
      return form;
   }

   /**
    * Returns a complete {@code PersonForm} for a new person.
    *
    * @return
    */
   public static PersonForm newPersonForm() {
      final PersonForm form = new PersonForm();
      form.setBirthdate("1988-09-30");
      form.setCollege("Colgate University");
      form.setDominantHand(DominantHand.RIGHT);
      form.setFirstName("Stanley");
      form.setGender(Gender.MALE);
      form.setLastName("Caple");
      form.setMiddleName("NMI");
      form.setNickname("Stan");
      return form;
   }

   /**
    * Returns a complete {@code PlayerForm} for a new player. NOTE: The person and the team season
    * must be applied.
    *
    * @return
    */
   public static PlayerForm newPlayerForm() {
      final PlayerForm form = new PlayerForm();
      form.setCaptain(false);
      form.setJerseyNumber("18");
      form.setPosition(Position.ATTACK);
      form.setParentReleaseReceivedOn("2014-10-12");
      form.setParentReleaseSentOn("2014-10-05");
      form.setReleased(true);
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
      form.setStartsOn("2014-07-01");
      form.setEndsOn("2015-06-30");
      return form;
   }

   /**
    * Returns a completed {@code SiteForm} for a new site.
    *
    * @return
    */
   public static SiteForm newSiteForm() {
      final SiteForm form = new SiteForm();
      form.setAddress1("40 Rice Street");
      form.setCity("Wellesley");
      form.setName("Wellesley High School");
      form.setPostalCode("02482");
      form.setRegion(Region.MA);
      form.setStyle(SiteStyle.COMPETITION);
      form.setSurface(Surface.GRASS);
      return form;
   }

   /**
    * Returns a completed {@code TeamForm} for a new team. NOTE: The home site is null.
    *
    * @return
    */
   public static TeamForm newTeamForm() {
      final TeamForm form = new TeamForm();
      form.setAbbreviation("WHS");
      form.setGender(TeamGender.BOYS);
      form.setLetter(Letter.VARSITY);
      form.setName("Wellesley Raiders");
      form.setRegion(Region.MA);
      form.setSponsor("Wellesley High School");
      return form;
   }

   /**
    * Returns a completed {@code TeamSeasonForm} for a new team season.
    *
    * @return
    */
   public static TeamSeasonForm newTeamSeasonForm() {
      final TeamEntry team = getExistingTeam();
      final SeasonEntry season = getExistingSeason();

      final TeamSeasonForm form = new TeamSeasonForm();
      form.setEndsOnFromLocalDate(LocalDate.parse("2014-06-30"));
      form.setName("Wellesley Raiders");
      form.setSeason(season.getId());
      form.setStartsOnFromLocalDate(LocalDate.parse("2013-07-01"));
      form.setStatus(TeamStatus.ACTIVE);
      form.setTeam(team.getId());
      return form;
   }

   /**
    * Returns a completed {@code UserForm} for a new user.
    *
    * @return
    */
   public static UserResource newUserForm() {
      final UserForm form = new UserForm();
      form.setEmail("john@example.com");
      form.setEnabled(true);
      form.setFirstName("John");
      form.setLastName("Doe");
      form.setPassword("user");
      return form;
   }

   /**
    * Returns a completed {@code ViolationForm} for a new violation.
    *
    * @return
    */
   public static ViolationResource newViolationForm() {
      final ViolationForm form = new ViolationForm();
      form.setCategory(PenaltyCategory.PERSONAL_FOUL);
      form.setDescription("This is a slash");
      form.setName("Slash");
      form.setPenaltyLength(PenaltyLength.ONE_MINUTE);
      form.setReleasable(true);
      return form;
   }

   /*---------- Entities ----------*/

   /**
    * Returns a {@code GameEntry} with a primary key, but no assigned teams or site.
    *
    * @return
    */
   public static GameEntry getUnassignedGame() {
      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();

      final GameEntry game = new GameEntry();
      game.setCreatedAt(LocalDateTime.now());
      game.setId(id);
      game.setModifiedAt(LocalDateTime.now());
      game.setSchedule(Schedule.REGULAR);
      game.setStartsAt(LocalDateTime.parse("2014-04-01T16:00:00"));
      game.setStatus(Status.SCHEDULED);
      return game;
   }

   /**
    * Returns a {@code PersonEntry} with a primary key and primary home address.
    *
    * @return
    */
   public static PersonEntry getPersonWithPrimaryAddress() {
      final String personId = IdentifierFactory.getInstance()
         .generateIdentifier();
      final String addressId = IdentifierFactory.getInstance()
         .generateIdentifier();

      final PersonEntry person = getPerson();
      person.setId(personId);

      final AddressEntry address = getHomeAddress();
      address.setId(addressId);

      person.addAddress(address);
      return person;
   }

   /**
    * Returns a {@code PersonEntry} with a primary key and primary telephone contact.
    *
    * @return
    */
   public static PersonEntry getPersonWithPrimaryContact() {
      final String personId = IdentifierFactory.getInstance()
         .generateIdentifier();
      final String contactId = IdentifierFactory.getInstance()
         .generateIdentifier();

      final PersonEntry person = getPerson();
      person.setId(personId);

      final ContactEntry contact = getTelephoneContact();
      contact.setId(contactId);
      contact.setPrimary(true);

      person.addContact(contact);
      return person;
   }

   /**
    * Returns a new {@code PersonEntry} with no address or contacts.
    *
    * @return
    */
   public static PersonEntry getPerson() {
      final PersonEntry person = new PersonEntry();
      person.setBirthdate(LocalDate.parse("1988-09-30"));
      person.setCollege("Colgate University");
      person.setCreatedAt(LocalDateTime.now());
      person.setFirstName("Stanley");
      person.setGender(Gender.MALE);
      person.setLastName("Caple");
      person.setModifiedAt(LocalDateTime.now());
      person.setNickname("Stan");

      return person;
   }

   public static ContactEntry getTelephoneContact() {
      final ContactEntry contact = new ContactEntry();
      contact.setCreatedAt(LocalDateTime.now());
      contact.setDoNotUse(false);
      contact.setMethod(ContactMethod.TELEPHONE);
      contact.setModifiedAt(LocalDateTime.now());
      contact.setPrimary(false);
      contact.setValue("888-555-1212");
      return contact;
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
      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
      final AddressEntry address = new AddressEntry();
      address.setAddress1("40 Rice Street");
      address.setAddressType(AddressType.SITE);
      address.setCity("Wellesley");
      address.setCreatedAt(LocalDateTime.now());
      address.setId(id);
      address.setModifiedAt(LocalDateTime.now());
      address.setPostalCode("02482");
      address.setPrimary(true);
      address.setRegion(Region.MA);
      return address;
   }

   /**
    * Returns a new {@code PlayerEntry} of type Role.ATHLETE
    *
    * @return
    */
   public static PlayerEntry getExistingPlayer() {
      final PersonEntry person = getPersonWithPrimaryContact();
      final TeamSeasonEntry teamSeason = getExistingTeamSeason();

      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
      final PlayerEntry player = new PlayerEntry();
      player.setCaptain(false);
      player.setCreatedAt(LocalDateTime.now());
      player.setFullName(person.getFullName());
      player.setId(id);
      player.setJerseyNumber("18");
      player.setModifiedAt(LocalDateTime.now());
      player.setPerson(person);
      player.setPosition(Position.ATTACK);
      player.setRole(Role.ATHLETE);
      player.setStatus(PlayerStatus.ACTIVE);
      player.setParentReleased(true);
      player.setParentReleaseSentOn(LocalDate.parse("2014-10-05"));
      player.setParentReleaseReceivedOn(LocalDate.parse("2014-10-12"));

      teamSeason.addPlayerToRoster(player);

      return player;
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
      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
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
      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
      final SiteEntry site = new SiteEntry();
      site.setName("Wellesley High School");
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

   /**
    * Returns a {@code TeamEntry} with a primary key and a home site.
    *
    * @return
    */
   public static TeamEntry getExistingTeam() {
      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
      final TeamEntry team = new TeamEntry();
      team.setAbbreviation("WHS");
      team.setCreatedAt(LocalDateTime.now());
      team.setGender(TeamGender.BOYS);
      team.setId(id);
      team.setLetter(Letter.VARSITY);
      team.setModifiedAt(LocalDateTime.now());
      team.setName("Wellesley Raiders");
      team.setRegion(Region.MA);
      team.setSponsor("Wellesley High School");

      final SiteEntry homeSite = getExistingSite();
      team.setHomeSite(homeSite);

      return team;
   }

   public static TeamSeasonEntry getExistingTeamSeason() {
      final SeasonEntry season = getExistingSeason();
      final TeamEntry team = getExistingTeam();

      final String id = IdentifierFactory.getInstance()
         .generateIdentifier();
      final TeamSeasonEntry teamSeason = new TeamSeasonEntry();
      teamSeason.setCreatedAt(LocalDateTime.now());
      teamSeason.setEndsOn(season.getEndsOn());
      teamSeason.setId(id);
      teamSeason.setModifiedAt(LocalDateTime.now());
      teamSeason.setName("Raiders");
      teamSeason.setSeason(season);
      teamSeason.setStartsOn(season.getStartsOn());
      teamSeason.setStatus(TeamStatus.ACTIVE);

      team.addSeason(teamSeason);

      return teamSeason;
   }

   /**
    * Returns a {@code ViolationEntry}
    *
    * @return
    */
   public static ViolationEntry getViolation() {
      final ViolationEntry violation = new ViolationEntry();
      violation.setCategory(PenaltyCategory.PERSONAL_FOUL);
      violation.setCreatedAt(LocalDateTime.now());
      violation.setDescription("This is a long description of a slash");
      violation.setModifiedAt(LocalDateTime.now());
      violation.setName("Slash");
      violation.setPenaltyLength(PenaltyLength.ONE_MINUTE);
      violation.setReleasable(true);
      return violation;
   }

   protected TestUtils() {
   }
}
