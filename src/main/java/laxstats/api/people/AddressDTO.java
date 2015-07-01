package laxstats.api.people;

import java.io.Serializable;

import laxstats.api.Region;
import laxstats.query.people.PersonEntry;
import laxstats.query.sites.SiteEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code AddressDTO} transfers information about an address between the application and domain
 * layers.
 */
public class AddressDTO implements Serializable {
   private static final long serialVersionUID = -8453839985074107739L;

   private final String id;
   private final PersonEntry person;
   private final SiteEntry site;
   private final AddressType addressType;
   private final String address1;
   private final String address2;
   private final String city;
   private final Region region;
   private final String postalCode;
   private final boolean isPrimary;
   private final boolean doNotUse;
   private final UserEntry createdBy;
   private final LocalDateTime createdAt;
   private final UserEntry modifiedBy;
   private final LocalDateTime modifiedAt;

   /**
    * Creates an {@code AddressDTO} with the given information.
    *
    * @param id
    * @param site
    * @param person
    * @param addressType
    * @param address1
    * @param address2
    * @param city
    * @param region
    * @param postalCode
    * @param isPrimary
    * @param doNotUse
    * @param createdBy
    * @param createdAt
    * @param modifiedBy
    * @param modifiedAt
    */
   public AddressDTO(String id, SiteEntry site, PersonEntry person, AddressType addressType,
      String address1, String address2, String city, Region region, String postalCode,
      boolean isPrimary, boolean doNotUse, UserEntry createdBy, LocalDateTime createdAt,
      UserEntry modifiedBy, LocalDateTime modifiedAt) {
      this.id = id;
      this.site = site;
      this.person = person;
      this.addressType = addressType;
      this.address1 = address1;
      this.address2 = address2;
      this.city = city;
      this.region = region;
      this.postalCode = postalCode;
      this.isPrimary = isPrimary;
      this.doNotUse = doNotUse;
      this.createdBy = createdBy;
      this.createdAt = createdAt;
      this.modifiedBy = modifiedBy;
      this.modifiedAt = modifiedAt;
   }

   /**
    * Creates an {@code AddressDTO} with the given information.
    *
    * @param id
    * @param site
    * @param person
    * @param addressType
    * @param address1
    * @param address2
    * @param city
    * @param region
    * @param postalCode
    * @param isPrimary
    * @param doNotUse
    * @param modifiedBy
    * @param modifiedAt
    */
   public AddressDTO(String id, SiteEntry site, PersonEntry person, AddressType addressType,
      String address1, String address2, String city, Region region, String postalCode,
      boolean isPrimary, boolean doNotUse, UserEntry modifiedBy, LocalDateTime modifiedAt) {
      this(id, site, person, addressType, address1, address2, city, region, postalCode, isPrimary,
         doNotUse, null, null, modifiedBy, modifiedAt);
   }

   /**
    * Returns the unique identifier of the address.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the associated person, or null if the association is with a playing field.
    *
    * @return
    */
   public PersonEntry getPerson() {
      return person;
   }

   /**
    * Returns the associated playing field, or null if the association is with a person.
    *
    * @return
    */
   public SiteEntry getSite() {
      return site;
   }

   /**
    * Returns the address type.
    *
    * @return
    */
   public AddressType getAddressType() {
      return addressType;
   }

   /**
    * Returns the first line of the street address, or null if none.
    *
    * @return
    */
   public String getAddress1() {
      return address1;
   }

   /**
    * Returns the second line of the street address, or null if none.
    *
    * @return
    */
   public String getAddress2() {
      return address2;
   }

   /**
    * Returns the city.
    *
    * @return
    */
   public String getCity() {
      return city;
   }

   /**
    * Returns the region.
    *
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Returns the postal code, or null if none.
    *
    * @return
    */
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * Returns true if this is the association's primary address, false otherwise.
    *
    * @return
    */
   public boolean isPrimary() {
      return isPrimary;
   }

   /**
    * Returns true if this address is obsolete, false otherwise.
    *
    * @return
    */
   public boolean isDoNotUse() {
      return doNotUse;
   }

   /**
    * Returns the user who first persisted this address.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns the date and time this address was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns the user who last modified this address.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Returns the date and time this address was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }
}
